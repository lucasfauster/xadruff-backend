package com.uff.br.xadruffbackend.model.piece

import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.direction.buildStraightDirections

class Rook(value: Char): Piece(value) {

    override fun calculateLegalMovements(line: Int, col: Int, board: Board): MutableList<String> =
        calculate(
            directions = buildStraightDirections(line, col),
            board = board
        )
}