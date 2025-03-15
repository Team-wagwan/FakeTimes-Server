package com.wagwan.faketimes.global.security.jwt.exception

import com.wagwan.faketimes.global.exception.BusinessException
import com.wagwan.faketimes.global.security.jwt.exception.error.JwtTokenError

object TokenUnKnownException: BusinessException(JwtTokenError.JWT_UNKNOWN_EXCEPTION) {
    private fun readResolve(): Any = TokenUnKnownException
    val EXCEPTION: TokenUnKnownException = TokenUnKnownException
}