package com.uff.br.xadruffbackend.model.piece

import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.LegalMovements
import com.uff.br.xadruffbackend.model.direction.buildDiagonalDirections

class Bishop(value: Char): Piece(value){
    override fun calculateLegalMovements(line: Int, col: Int, board: Board, legalMovements: LegalMovements) =
        legalMovements.calculate(directions = buildDiagonalDirections(line, col), board = board)
}