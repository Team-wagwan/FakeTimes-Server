package com.wagwan.faketimes.global.exception.error

import org.springframework.http.HttpStatus

enum class ErrorCode(
    override val status: HttpStatus,
    override val message: String
) : ErrorProperty {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    CAN_NOT_GET_KAKAO_ID_ERROR(HttpStatus.NOT_FOUND, "카카오 아이디를 가져올 수 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다.");

}