package com.uff.br.xadruffbackend.dto.direction

abstract class Direction(
    val hasCapture: Boolean = true,
    var hasMovement: Boolean = true
) {
    abstract fun getFutureRow(row: Int, index: Int): Int
    abstract fun getFutureColumn(column: Int, index: Int): Int

    override fun toString(): String {
        return this.javaClass.simpleName
    }
}
