package com.wagwan.faketimes.global.security.jwt.exception

import com.wagwan.faketimes.global.exception.BusinessException
import com.wagwan.faketimes.global.security.jwt.exception.error.JwtTokenError

object TokenNotSupportException : BusinessException(JwtTokenError.JWT_NOT_SUPPORT) {

    private fun readResolve(): Any = TokenNotSupportException

    val EXCEPTION: TokenNotSupportException = TokenNotSupportException

}