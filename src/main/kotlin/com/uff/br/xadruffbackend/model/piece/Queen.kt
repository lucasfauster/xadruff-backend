package com.uff.br.xadruffbackend.model.piece

import com.uff.br.xadruffbackend.model.direction.Direction
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.util.buildAllDirections

class Queen(color: Color) : Piece(VALUE, color) {
    companion object {
        const val VALUE = 'q'
    }
    override val directions: List<Direction> = buildAllDirections()
    override val movementRange: Int = MovementRange.ALL_BOARD
}
