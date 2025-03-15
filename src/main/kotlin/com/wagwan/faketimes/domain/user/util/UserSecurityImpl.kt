package com.wagwan.faketimes.domain.user.util

import com.wagwan.faketimes.domain.user.dto.User
import com.wagwan.faketimes.global.security.auth.CustomUserDetails
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Repository

@Repository
class UserSecurityImpl: UserSecurity {
    override fun getUser(): User {
        val user: User = (SecurityContextHolder
            .getContext()
            .authentication
            .principal as CustomUserDetails).getUser()
        return user
    }
}