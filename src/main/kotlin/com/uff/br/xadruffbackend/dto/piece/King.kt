package com.uff.br.xadruffbackend.dto.piece

import com.uff.br.xadruffbackend.dto.enum.Color
import com.uff.br.xadruffbackend.util.buildAllDirections

class King(color: Color) : Piece(VALUE, color) {
    companion object {
        const val VALUE = 'k'
    }
    override val directions = buildAllDirections()
    override val movementRange = MovementRange.ONE
    var hasMoved = false
}
