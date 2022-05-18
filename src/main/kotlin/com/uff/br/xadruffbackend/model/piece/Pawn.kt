package com.uff.br.xadruffbackend.model.piece

import com.uff.br.xadruffbackend.model.direction.Direction
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.util.buildBlackPawnDirections
import com.uff.br.xadruffbackend.util.buildWhitePawnDirections

class Pawn(color: Color) : Piece('p', color) {

    override val directions: List<Direction> = if (color == Color.BLACK) {
        buildBlackPawnDirections()
    } else {
        buildWhitePawnDirections()
    }

    override val movementRange: Int = 1

    fun isBlackFirstMovement(positionLine: Int) = color == Color.BLACK && positionLine == 1

    fun isWhiteFirstMovement(positionLine: Int) = color == Color.WHITE && positionLine == 6
}

