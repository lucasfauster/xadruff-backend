package com.uff.br.xadruffbackend.dto.piece

import com.uff.br.xadruffbackend.dto.direction.Direction
import com.uff.br.xadruffbackend.dto.enum.Color

object MovementRange {
    const val ALL_BOARD = 7
    const val ONE = 1
    const val ZERO = 0
}

abstract class Piece(
    private val internValue: Char,
    val color: Color
) {
    abstract val directions: List<Direction>
    abstract val movementRange: Int

    val value: Char
        get() = if (color == Color.BLACK) {
            internValue
        } else {
            internValue.uppercaseChar()
        }

    override fun toString(): String {
        return value.toString()
    }
}
