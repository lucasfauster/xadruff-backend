package com.uff.br.xadruffbackend.dto.piece

import com.uff.br.xadruffbackend.dto.direction.Direction
import com.uff.br.xadruffbackend.dto.enum.Color
import com.uff.br.xadruffbackend.util.buildBlackPawnDirections
import com.uff.br.xadruffbackend.util.buildWhitePawnDirections

class Pawn(color: Color) : Piece(VALUE, color) {

    companion object {
        const val INITIAL_WHITE_PAWN_POSITION_ROW = 6
        const val INITIAL_BLACK_PAWN_POSITION_ROW = 1
        const val VALUE = 'p'
    }

    override val directions: List<Direction> = if (color == Color.BLACK) {
        buildBlackPawnDirections()
    } else {
        buildWhitePawnDirections()
    }

    override val movementRange: Int = MovementRange.ONE

    fun isBlackFirstMovement(positionRow: Int) = color == Color.BLACK && positionRow == INITIAL_BLACK_PAWN_POSITION_ROW

    fun isWhiteFirstMovement(positionRow: Int) = color == Color.WHITE && positionRow == INITIAL_WHITE_PAWN_POSITION_ROW
}
