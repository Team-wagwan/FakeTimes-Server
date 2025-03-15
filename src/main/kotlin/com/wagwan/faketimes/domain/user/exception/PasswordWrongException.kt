package com.wagwan.faketimes.domain.user.exception

import com.wagwan.faketimes.domain.user.exception.error.UserError
import com.wagwan.faketimes.global.exception.BusinessException

object PasswordWrongException : BusinessException(UserError.PASSWORD_WRONG) {

    private fun readResolve(): Any = PasswordWrongException

    val EXCEPTION: PasswordWrongException = PasswordWrongException

}