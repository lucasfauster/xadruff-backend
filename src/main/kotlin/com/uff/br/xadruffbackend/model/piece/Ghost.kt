package com.uff.br.xadruffbackend.model.piece

import com.uff.br.xadruffbackend.model.direction.Direction
import com.uff.br.xadruffbackend.model.enum.Color

class Ghost(color: Color) : Piece(VALUE, color) {
    companion object {
        const val VALUE = 'g'
    }

    override val directions: List<Direction> = emptyList()
    override val movementRange = MovementRange.ZERO

    override fun toString(): String {
        return ""
    }
}
