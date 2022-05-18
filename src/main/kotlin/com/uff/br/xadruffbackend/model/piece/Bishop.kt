package com.uff.br.xadruffbackend.model.piece

import com.uff.br.xadruffbackend.model.direction.Direction
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.util.buildDiagonalDirections

class Bishop(color: Color): Piece('b', color){
    override val directions: List<Direction> = buildDiagonalDirections()
    override val movementRange: Int = 7
}