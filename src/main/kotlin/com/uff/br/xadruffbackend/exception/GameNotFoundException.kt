package com.uff.br.xadruffbackend.exception

class GameNotFoundException(
    message: String? = null,
    throwable: Throwable? = null
) : RuntimeException(message, throwable)
