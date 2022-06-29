package com.uff.br.xadruffbackend.dto.piece

import com.uff.br.xadruffbackend.dto.direction.Direction
import com.uff.br.xadruffbackend.dto.enum.Color

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
