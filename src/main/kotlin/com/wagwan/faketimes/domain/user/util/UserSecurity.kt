package com.wagwan.faketimes.domain.user.util

import com.wagwan.faketimes.domain.user.dto.User

interface UserSecurity {

    fun getUser(): User

}