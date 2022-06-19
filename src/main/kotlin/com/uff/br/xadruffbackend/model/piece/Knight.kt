package com.uff.br.xadruffbackend.model.piece

import com.uff.br.xadruffbackend.model.direction.Direction
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.util.buildLDirections

class Knight(color: Color) : Piece(VALUE, color) {
    companion object {
        const val VALUE = 'n'
    }
    override val directions: List<Direction> = buildLDirections()
    override val movementRange: Int = MovementRange.ONE
}
