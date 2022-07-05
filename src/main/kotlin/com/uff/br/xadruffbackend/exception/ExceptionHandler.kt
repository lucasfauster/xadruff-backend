package com.uff.br.xadruffbackend.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.security.InvalidParameterException
import javax.validation.ConstraintViolationException

@ControllerAdvice
class ExceptionHandler {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler(InvalidMovementException::class)
    fun handleInvalidMovementException(
        exception: InvalidMovementException,
    ): ResponseEntity<ErrorResponse> {
        return buildResponse(
            HttpStatus.BAD_REQUEST,
            exception,
            ErrorResponse(exception.message)
        )
    }

    @ExceptionHandler(InvalidParameterException::class)
    fun handleInvalidParameterException(
        exception: InvalidParameterException
    ): ResponseEntity<ErrorResponse> {
        return buildResponse(
            HttpStatus.BAD_REQUEST,
            exception,
            ErrorResponse(exception.message)
        )
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(
        exception: MethodArgumentTypeMismatchException
    ): ResponseEntity<ErrorResponse> {
        return buildResponse(
            HttpStatus.BAD_REQUEST,
            exception,
            ErrorResponse(exception.message)
        )
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingServletRequestParameterException(
        exception: MissingServletRequestParameterException
    ): ResponseEntity<ErrorResponse> {
        return buildResponse(
            HttpStatus.BAD_REQUEST,
            exception,
            ErrorResponse(exception.message)
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(
        exception: HttpMessageNotReadableException
    ): ResponseEntity<ErrorResponse> {
        return buildResponse(
            HttpStatus.BAD_REQUEST,
            exception,
            ErrorResponse(exception.message)
        )
    }

    @ExceptionHandler(GameNotFoundException::class)
    fun handleGameNotFoundException(
        exception: GameNotFoundException
    ): ResponseEntity<ErrorResponse> {
        return buildResponse(
            HttpStatus.BAD_REQUEST,
            exception,
            ErrorResponse(exception.message)
        )
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(
        exception: ConstraintViolationException
    ): ResponseEntity<ErrorResponse> {
        return buildResponse(
            HttpStatus.BAD_REQUEST,
            exception,
            ErrorResponse(exception.message)
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(
        exception: Exception
    ): ResponseEntity<ErrorResponse> {
        return buildResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            exception,
            ErrorResponse(exception.message)
        )
    }

    private fun buildResponse(
        status: HttpStatus,
        exception: Exception? = null,
        payload: ErrorResponse? = null
    ) = ResponseEntity.status(status).body(payload).also {

        logger.error(
            "Erro: ${exception?.let {
                "exception.class: ${exception.javaClass.name} exception.message ${exception.message}"
            }} payload: $payload"
        )
    }
}
