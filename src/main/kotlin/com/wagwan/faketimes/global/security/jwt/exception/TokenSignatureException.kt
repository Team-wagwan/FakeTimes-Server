package com.wagwan.faketimes.global.security.jwt.exception

import com.wagwan.faketimes.global.exception.BusinessException
import com.wagwan.faketimes.global.security.jwt.exception.error.JwtTokenError

object TokenSignatureException : BusinessException(JwtTokenError.JWT_TOKEN_SIGNATURE_ERROR) {
    private fun readResolve(): Any  = TokenSignatureException
    val EXCEPTION : TokenSignatureException = TokenSignatureException
}