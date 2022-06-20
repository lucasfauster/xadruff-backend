package com.uff.br.xadruffbackend.model.piece

import com.uff.br.xadruffbackend.model.direction.Direction
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.util.buildDiagonalDirections

class Bishop(color: Color) : Piece(VALUE, color) {
    companion object {
        const val VALUE = 'b'
    }
    override val directions: List<Direction> = buildDiagonalDirections()
    override val movementRange: Int = MovementRange.ALL_BOARD
}
