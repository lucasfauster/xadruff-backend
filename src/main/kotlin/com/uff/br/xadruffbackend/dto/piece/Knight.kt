package com.uff.br.xadruffbackend.dto.piece

import com.uff.br.xadruffbackend.dto.direction.Direction
import com.uff.br.xadruffbackend.dto.enum.Color
import com.uff.br.xadruffbackend.util.buildLDirections

class Knight(color: Color) : Piece(VALUE, color) {
    companion object {
        const val VALUE = 'n'
    }
    override val directions: List<Direction> = buildLDirections()
    override val movementRange: Int = MovementRange.ONE
}
