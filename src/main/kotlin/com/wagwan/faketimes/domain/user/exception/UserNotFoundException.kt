package com.wagwan.faketimes.domain.user.exception

import com.wagwan.faketimes.domain.user.exception.error.UserError
import com.wagwan.faketimes.global.exception.BusinessException

object UserNotFoundException : BusinessException(UserError.USER_NOT_FOUND) {

    private fun readResolve(): Any = UserNotFoundException

    val EXCEPTION: UserNotFoundException = UserNotFoundException

}