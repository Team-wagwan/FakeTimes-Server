package com.wagwan.faketimes.domain.user.domain.repository

import com.wagwan.faketimes.domain.user.domain.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserJpaRepository: JpaRepository<UserEntity, String> {
    fun findByEmail(email:String): Optional<UserEntity>
}