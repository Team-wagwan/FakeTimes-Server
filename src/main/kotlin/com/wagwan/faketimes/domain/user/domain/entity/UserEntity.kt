package com.wagwan.faketimes.domain.user.domain.entity

import com.wagwan.faketimes.domain.user.domain.enums.UserRole
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "tb_user")
class UserEntity(
    @Id
    var email: String,
    var name: String,
    var password: String,
    var role: UserRole,
) {

}