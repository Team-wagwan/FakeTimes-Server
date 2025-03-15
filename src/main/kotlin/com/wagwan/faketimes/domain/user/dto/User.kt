package com.wagwan.faketimes.domain.user.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.wagwan.faketimes.domain.user.domain.entity.UserEntity
import com.wagwan.faketimes.domain.user.domain.enums.UserRole
import org.springframework.stereotype.Component

data class User(
    var email: String,
    var name: String,
    @JsonIgnore
    var password: String,
    @JsonIgnore
    var role: UserRole
){
    @Component
    companion object {
        fun toUser(userEntity: UserEntity): User {
            return User(
                email = userEntity.email,
                name = userEntity.name,
                role = userEntity.role,
                password = userEntity.password,
            )
        }
    }
}