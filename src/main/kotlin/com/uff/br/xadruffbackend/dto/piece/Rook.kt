package com.uff.br.xadruffbackend.dto.piece

import com.uff.br.xadruffbackend.dto.direction.Direction
import com.uff.br.xadruffbackend.dto.enum.Color
import com.uff.br.xadruffbackend.util.buildStraightDirections

class Rook(color: Color) : Piece(VALUE, color) {
    companion object {
        const val VALUE = 'r'
    }

    override val directions: List<Direction> = buildStraightDirections()
    override val movementRange: Int = MovementRange.ALL_BOARD
    var hasMoved = false
}
