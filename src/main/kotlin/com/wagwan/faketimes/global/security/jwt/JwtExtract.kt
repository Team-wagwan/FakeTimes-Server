package com.wagwan.faketimes.global.security.jwt

import com.wagwan.faketimes.domain.user.domain.repository.UserJpaRepository
import com.wagwan.faketimes.domain.user.dto.User
import com.wagwan.faketimes.domain.user.exception.UserNotFoundException
import com.wagwan.faketimes.global.security.auth.CustomUserDetails
import com.wagwan.faketimes.global.security.jwt.config.JwtProperties
import com.wagwan.faketimes.global.security.jwt.exception.error.JwtErrorType
import io.jsonwebtoken.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Component
class JwtExtract(
    private val jwtProperties: JwtProperties,
    private val userJpaRepository: UserJpaRepository
) {

    private val secretKey: SecretKey = SecretKeySpec(
        this.jwtProperties.secretKey.toByteArray(StandardCharsets.UTF_8),
        Jwts.SIG.HS256.key().build().algorithm
    )

    fun checkTokenInfo(token: String): JwtErrorType {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token)
            return JwtErrorType.OK
        } catch (e: ExpiredJwtException) {
            return JwtErrorType.ExpiredJwtException
        } catch (e: java.security.SignatureException) {
            return JwtErrorType.SignatureException
        } catch (e: MalformedJwtException) {
            return JwtErrorType.MalformedJwtException
        } catch (e: UnsupportedJwtException) {
            return JwtErrorType.UnsupportedJwtException
        } catch (e: IllegalArgumentException) {
            return JwtErrorType.IllegalArgumentException
        } catch (e: Exception) {
            return JwtErrorType.UNKNOWN_EXCEPTION
        }
    }

    fun getAuthentication(token: String): Authentication {
        val t = getToken(token)
        val user = findUserByEmail(getEmail(t))

        val details = CustomUserDetails(user)

        return UsernamePasswordAuthenticationToken(details, null, details.getAuthorities())
    }

    fun getToken(token: String): String {
        return token.removePrefix("Bearer ")
    }

    fun getEmail(token: String): String {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload.get(
            "email",
            String::class.java
        )
    }

    fun findUserByEmail(email: String): User {
        return userJpaRepository.findByEmail(email)
            .map { userEntity -> User.toUser(userEntity) }
            .orElseThrow{ UserNotFoundException }
    }

}