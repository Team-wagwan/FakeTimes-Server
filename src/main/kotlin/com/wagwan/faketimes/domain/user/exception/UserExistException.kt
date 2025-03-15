package com.wagwan.faketimes.domain.user.exception

import com.wagwan.faketimes.domain.user.exception.error.UserError
import com.wagwan.faketimes.global.exception.BusinessException

object UserExistException : BusinessException(UserError.USER_EXIST) {

    private fun readResolve(): Any = UserExistException

    val EXCEPTION: UserExistException = UserExistException

}