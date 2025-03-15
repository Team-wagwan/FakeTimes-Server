package com.wagwan.faketimes.global.exception

import com.wagwan.faketimes.global.exception.error.ErrorProperty

open class BusinessException(val error: ErrorProperty) : RuntimeException() {

}