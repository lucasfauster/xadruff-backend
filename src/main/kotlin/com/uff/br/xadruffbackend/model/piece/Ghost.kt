package com.uff.br.xadruffbackend.model.piece

import com.uff.br.xadruffbackend.model.direction.Direction
import com.uff.br.xadruffbackend.model.enum.Color

class Ghost(color: Color) : Piece('g', color) {
    override val directions: List<Direction> = emptyList()

    override val movementRange = 0

    override fun toString(): String {
        return ""
    }
}
