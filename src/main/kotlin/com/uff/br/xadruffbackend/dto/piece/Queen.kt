package com.uff.br.xadruffbackend.dto.piece

import com.uff.br.xadruffbackend.dto.direction.Direction
import com.uff.br.xadruffbackend.dto.enum.Color
import com.uff.br.xadruffbackend.util.buildAllDirections

class Queen(color: Color) : Piece(VALUE, color) {
    companion object {
        const val VALUE = 'q'
    }
    override val directions: List<Direction> = buildAllDirections()
    override val movementRange: Int = MovementRange.ALL_BOARD
}
