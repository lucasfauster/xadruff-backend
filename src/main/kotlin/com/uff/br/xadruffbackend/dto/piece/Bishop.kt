package com.uff.br.xadruffbackend.dto.piece

import com.uff.br.xadruffbackend.dto.direction.Direction
import com.uff.br.xadruffbackend.dto.enum.Color
import com.uff.br.xadruffbackend.util.buildDiagonalDirections

class Bishop(color: Color) : Piece(VALUE, color) {
    companion object {
        const val VALUE = 'b'
    }
    override val directions: List<Direction> = buildDiagonalDirections()
    override val movementRange: Int = MovementRange.ALL_BOARD
}
