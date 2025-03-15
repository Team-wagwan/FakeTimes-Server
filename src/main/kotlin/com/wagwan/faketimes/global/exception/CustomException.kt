package com.wagwan.faketimes.global.exception

import com.wagwan.faketimes.global.exception.error.ErrorProperty

class CustomException(
    val errorProperty: ErrorProperty
): RuntimeException()