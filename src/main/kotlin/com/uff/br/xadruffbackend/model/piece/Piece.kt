package com.uff.br.xadruffbackend.model.piece

import com.uff.br.xadruffbackend.model.direction.Direction
import com.uff.br.xadruffbackend.model.enum.Color

abstract class Piece(
    private val _value: Char,
    val color: Color
) {
    abstract val directions: List<Direction>
    abstract val movementRange: Int

    val value: Char
        get() = if (color == Color.BLACK) {
            _value
        } else {
            _value.uppercaseChar()
        }

    override fun toString(): String {
        return value.toString()
    }
}
