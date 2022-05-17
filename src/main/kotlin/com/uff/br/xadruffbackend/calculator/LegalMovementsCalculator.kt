package com.uff.br.xadruffbackend.calculator

import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.Board

class LegalMovementsCalculator(
    private val board: Board
) {

    fun calculatePseudoLegalMoves(): MutableList<String> {
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



