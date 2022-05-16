package com.uff.br.xadruffbackend.model.direction

interface Direction {
    val line: Int
    val column: Int
    fun getFutureLine(index: Int): Int
    fun getFutureColumn(index: Int): Int
}