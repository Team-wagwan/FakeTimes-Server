package com.appjam29th.global.security.jwt.exception

import com.wagwan.faketimes.global.exception.BusinessException
import com.wagwan.faketimes.global.security.jwt.exception.error.JwtTokenError

object TokenExpiredException : BusinessException(JwtTokenError.JWT_EXPIRED) {

    private fun readResolve(): Any = TokenExpiredException

    val EXCEPTION: TokenExpiredException = TokenExpiredException

}