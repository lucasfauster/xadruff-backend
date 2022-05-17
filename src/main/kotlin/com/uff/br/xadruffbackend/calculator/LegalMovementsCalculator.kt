package com.uff.br.xadruffbackend.calculator

import com.uff.br.xadruffbackend.model.Board
import org.springframework.stereotype.Service

@Service
class LegalMovementsCalculator {

    fun calculatePseudoLegalMoves(board: Board): MutableList<String> {
        //TODO: Acrescentar indicação de promoção e rook

        val newLegalMoves = mutableListOf<String>()

        board.positions.forEach { boardLine ->
            boardLine.filter {
                it.piece?.getColor() == board.colorTurn
            }.forEach {
                newLegalMoves.addAll(
                    it.piece!!.calculateLegalMovements(it.line, it.column, board)
                )
            }
        }
        return newLegalMoves
    }
}



