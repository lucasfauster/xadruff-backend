package com.uff.br.xadruffbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class XadruffBackendApplication

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<XadruffBackendApplication>(*args)
}
