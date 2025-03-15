package com.wagwan.faketimes.global.security.jwt.exception

import com.wagwan.faketimes.global.exception.BusinessException
import com.wagwan.faketimes.global.security.jwt.exception.error.JwtTokenError

object TokenTypeException : BusinessException(JwtTokenError.JWT_TOKEN_ERROR) {

    private fun readResolve(): Any = TokenTypeException

    val EXCEPTION: TokenTypeException = TokenTypeException

}