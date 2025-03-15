package com.wagwan.faketimes.global.exception.handler

import com.wagwan.faketimes.domain.auth.client.api.AuthController
import com.wagwan.faketimes.domain.kkangtong.controller.KkangtongController
import com.wagwan.faketimes.global.exception.BusinessException
import com.wagwan.faketimes.global.exception.CustomException
import org.springframework.dao.DataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.sql.SQLTimeoutException
import java.sql.SQLTransientConnectionException

//@RestControllerAdvice(annotations = [RestController::class], basePackageClasses = [AuthController::class, KkangtongController::class])
class ExceptionAdvice{

//    val logger = LoggerFactory.getLogger(ExceptionAdvice::class.java)

    data class ErrorResponse(
        val status: Int,
        val message: String
    )

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(ex: BusinessException): ResponseEntity<ErrorResponse> {
//        logger.warn("Handling BusinessException: ${ex.message}")
        val response = ErrorResponse(
            status = ex.error.status.value(),
            message = ex.error.message
        )
        return ResponseEntity(response, ex.error.status)
    }

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(customException: CustomException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            status = customException.errorProperty.status.value(),
            message = customException.errorProperty.message
        )
        return ResponseEntity(response, customException.errorProperty.status)
    }

    @ExceptionHandler(SQLTimeoutException::class, SQLTransientConnectionException::class)
    fun handleDatabaseTimeoutException(ex: Exception): ResponseEntity<ErrorResponse> {
//        logger.error("Database Connection Timeout: ${ex.message}")
        val response = ErrorResponse(
            status = HttpStatus.SERVICE_UNAVAILABLE.value(),
            message = "DB Connection Timeout ${ex.message}"
        )
        return ResponseEntity(response, HttpStatus.SERVICE_UNAVAILABLE)
    }

    @ExceptionHandler(DataAccessException::class)
    fun handleDataAccessException(ex: DataAccessException): ResponseEntity<ErrorResponse> {
//        logger.error("Data Access Error: ${ex.message}")
        val response = ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "데이터 처리 중 에러"
        )
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ErrorResponse> {
//        logger.error("Unexpected error: ${ex.message}", ex)
        val response = ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "Unexpected error: ${ex.message}"
        )
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(BindException::class)
    fun handleValidationException(ex: BindException): ResponseEntity<ErrorResponse> {
        val errorMessage = ex.bindingResult.allErrors.joinToString(", ") { it.defaultMessage ?: "Validation error" }
//        logger.warn("Validation error: $errorMessage")
        return ResponseEntity(
            ErrorResponse(status = HttpStatus.BAD_REQUEST.value(), message = errorMessage),
            HttpStatus.BAD_REQUEST
        )
    }
}