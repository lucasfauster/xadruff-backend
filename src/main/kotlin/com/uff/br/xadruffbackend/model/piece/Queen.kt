package com.uff.br.xadruffbackend.model.piece

import com.uff.br.xadruffbackend.model.direction.Direction
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.util.buildAllDirections

class Queen(color: Color) : Piece('q', color) {
    override val directions: List<Direction> = buildAllDirections()
    override val movementRange: Int = 7
}
