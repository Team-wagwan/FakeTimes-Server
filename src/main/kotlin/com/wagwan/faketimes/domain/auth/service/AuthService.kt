package com.wagwan.faketimes.domain.auth.service

import com.appjam29th.global.security.jwt.exception.TokenExpiredException
import com.wagwan.faketimes.domain.auth.client.request.RefreshTokenRequest
import com.wagwan.faketimes.domain.auth.client.request.SignInRequest
import com.wagwan.faketimes.domain.auth.client.request.SignUpRequest
import com.wagwan.faketimes.domain.auth.service.response.JsonWebTokenResponse
import com.wagwan.faketimes.domain.auth.service.response.RefreshTokenResponse
import com.wagwan.faketimes.domain.user.domain.entity.UserEntity
import com.wagwan.faketimes.domain.user.domain.enums.UserRole
import com.wagwan.faketimes.domain.user.domain.repository.UserJpaRepository
import com.wagwan.faketimes.domain.user.dto.User
import com.wagwan.faketimes.domain.user.exception.PasswordWrongException
import com.wagwan.faketimes.domain.user.exception.UserExistException
import com.wagwan.faketimes.domain.user.exception.UserNotFoundException
import com.wagwan.faketimes.global.security.jwt.JwtExtract
import com.wagwan.faketimes.global.security.jwt.JwtProvider
import com.wagwan.faketimes.global.security.jwt.exception.error.JwtErrorType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService (
    private val userJpaRepository: UserJpaRepository,
    private val encoder: PasswordEncoder,
    private val jwtProvider: JwtProvider,
    private val jwtExtract: JwtExtract,
) {

    fun signUp(request: SignUpRequest) {
        if(userJpaRepository.existsById(request.email)) {
            throw UserExistException.EXCEPTION
        }
        save(request)
    }

    fun signIn(request: SignInRequest): JsonWebTokenResponse {
        val user: User = userJpaRepository
            .findByEmail(request.email)
            .map { userEntity -> User.toUser(userEntity) }
            .orElseThrow { UserNotFoundException.EXCEPTION }
        val password: String = user.password
        if (!encoder.matches(request.password, password))
            throw PasswordWrongException.EXCEPTION
        return JsonWebTokenResponse(
            accessToken = jwtProvider.generateAccessToken(request.email, user.role),
            refreshToken = jwtProvider.generateRefreshToken(request.email, user.role),
            )
    }

    fun refresh(request: RefreshTokenRequest): RefreshTokenResponse {
        val got = jwtExtract.getToken(request.refreshToken)
        val user = jwtExtract.findUserByEmail(got)
        if (jwtExtract.checkTokenInfo(got) == JwtErrorType.ExpiredJwtException) {
            throw TokenExpiredException
        }
        return RefreshTokenResponse(
                jwtProvider.generateAccessToken(user.email, user.role),
            )
    }

    fun save(request: SignUpRequest){
        userJpaRepository.save(
            UserEntity(
            email = request.email,
            name = request.name,
            password = encoder.encode(request.password),
            role = UserRole.USER,
        ))
    }

}