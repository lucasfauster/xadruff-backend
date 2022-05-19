package com.uff.br.xadruffbackend.model.direction

abstract class Direction(
    val hasCapture: Boolean = true,
    val hasMovement: Boolean = true) {
    abstract fun getFutureLine(line: Int, index: Int): Int
    abstract fun getFutureColumn(column: Int, index: Int): Int

    override fun toString(): String {
        return this.javaClass.simpleName
    }
}