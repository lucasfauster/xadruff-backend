package com.uff.br.xadruffbackend.exception

class InvalidMovementException(
    message: String? = null,
    throwable: Throwable? = null
) : RuntimeException(message, throwable)
