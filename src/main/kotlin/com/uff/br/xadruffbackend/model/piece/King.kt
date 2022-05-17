package com.uff.br.xadruffbackend.model.piece

import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.direction.buildDiagonalDirections
import com.uff.br.xadruffbackend.model.direction.buildStraightDirections

class King(value: Char): Piece(value) {

    override fun calculateLegalMovements(line: Int, col: Int, board: Board): MutableList<String>{
        val legalMovements: MutableList<String> = calculate(
            directions = buildDiagonalDirections(line, col),
            indexRange = 1,
            board = board
        )
        legalMovements.addAll(
            calculate(
                directions = buildStraightDirections(line, col),
                indexRange = 1,
                board = board
            )
        )
        return legalMovements
    }
}