package com.example.shopping_api.exception

import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    data class ErrorResponse(
        val status: Int,
        val error: String,
        val message: String,
        val path: String? = null
    )

    private fun buildResponse(ex: BaseException, request: String?): ResponseEntity<ErrorResponse> =
        ResponseEntity.status(ex.status).body(
            ErrorResponse(
                status = ex.status,
                error = HttpStatus.valueOf(ex.status).reasonPhrase,
                message = ex.message,
                path = request
            )
        )

    @ExceptionHandler(BaseException::class)
    fun handleBaseException(ex: BaseException, request: jakarta.servlet.http.HttpServletRequest): ResponseEntity<ErrorResponse> {
        return buildResponse(ex, request.requestURI)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, request: jakarta.servlet.http.HttpServletRequest)
            : ResponseEntity<ErrorResponse> {

        val message = ex.bindingResult.fieldErrors.joinToString(", ") { "${it.field}: ${it.defaultMessage}" }

        return ResponseEntity.status(400).body(
            ErrorResponse(
                status = 400,
                error = "Bad Request",
                message = message,
                path = request.requestURI
            )
        )
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(ex: ConstraintViolationException, request: jakarta.servlet.http.HttpServletRequest)
            : ResponseEntity<ErrorResponse> {

        return ResponseEntity.status(400).body(
            ErrorResponse(
                status = 400,
                error = "Bad Request",
                message = ex.message ?: "Invalid request",
                path = request.requestURI
            )
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleAll(ex: Exception, request: jakarta.servlet.http.HttpServletRequest)
            : ResponseEntity<ErrorResponse> {

        ex.printStackTrace()

        return ResponseEntity.status(500).body(
            ErrorResponse(
                status = 500,
                error = "Internal Server Error",
                message = ex.message ?: "Unexpected error",
                path = request.requestURI
            )
        )
    }
}
