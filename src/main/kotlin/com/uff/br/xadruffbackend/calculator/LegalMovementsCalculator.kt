package com.uff.br.xadruffbackend.calculator

import com.uff.br.xadruffbackend.enum.Color
import com.uff.br.xadruffbackend.model.Board

class LegalMovementsCalculator(
    private val board: Board
) {

    fun calculatePseudoLegalMoves(colorTurn: Color): MutableList<String> {
        //TODO: Acrescentar indicação de promoção e rook

        val newLegalMoves = mutableListOf<String>()

        board.positions.forEach { boardLine ->
            boardLine.filter {
                it.piece?.getColor() == colorTurn
            }.forEach {
                newLegalMoves.addAll(
                    it.piece!!.calculateLegalMovements(it.line, it.column, board)
                )
            }
        }
        return newLegalMoves
    }
}



