package com.appjam29th.global.security.jwt.exception

import com.wagwan.faketimes.global.exception.BusinessException
import com.wagwan.faketimes.global.security.jwt.exception.error.JwtTokenError

object TokenEmptyException: BusinessException(JwtTokenError.JWT_EMPTY_EXCEPTION) {
    private fun readResolve():Any = TokenEmptyException
    val EXCEPTION: TokenEmptyException = TokenEmptyException
}