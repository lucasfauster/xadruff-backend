package com.uff.br.xadruffbackend.calculator

import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.LegalMovements
import org.springframework.stereotype.Service

@Service
class LegalMovementsCalculator {

    fun calculatePseudoLegalMoves(board: Board): LegalMovements {
        //TODO: Acrescentar indicação de promoção e rook

        val newLegalMoves = LegalMovements(mutableListOf<String>())

        board.positions.forEach { boardLine ->
            boardLine.filter {
                it.piece?.getColor() == board.colorTurn
            }.forEach {
                it.piece!!.calculateLegalMovements(it.line, it.column, board, newLegalMoves)
            }
        }
        return newLegalMoves
    }
}



