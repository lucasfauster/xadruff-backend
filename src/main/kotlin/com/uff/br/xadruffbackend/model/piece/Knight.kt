package com.uff.br.xadruffbackend.model.piece

import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.LegalMovements
import com.uff.br.xadruffbackend.model.direction.buildLDirections

class Knight(value: Char): Piece(value)  {
    override fun calculateLegalMovements(line: Int, col: Int, board: Board, legalMovements: LegalMovements) =
        legalMovements.calculate(directions = buildLDirections(line, col), indexRange = 1, board = board)

}