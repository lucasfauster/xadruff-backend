package com.uff.br.xadruffbackend.calculator

import com.uff.br.xadruffbackend.calculator.piece.BishopMovementsCalculator
import com.uff.br.xadruffbackend.calculator.piece.HorseMovementsCalculator
import com.uff.br.xadruffbackend.calculator.piece.KingMovementsCalculator
import com.uff.br.xadruffbackend.calculator.piece.PawnMovementsCalculator
import com.uff.br.xadruffbackend.calculator.piece.QueenMovementsCalculator
import com.uff.br.xadruffbackend.calculator.piece.RookMovementsCalculator
import com.uff.br.xadruffbackend.enum.Color
import com.uff.br.xadruffbackend.model.Piece

class LegalMovementsCalculator(
    private val boardPositions: List<MutableList<Piece?>>,
    colorTurn: Color
) {
    private val pawnMovementsCalculator: PawnMovementsCalculator = PawnMovementsCalculator(colorTurn, boardPositions)
    private val horseMovementsCalculator: HorseMovementsCalculator = HorseMovementsCalculator(colorTurn, boardPositions)
    private val kingMovementsCalculator: KingMovementsCalculator = KingMovementsCalculator(colorTurn, boardPositions)
    private val rookMovementsCalculator: RookMovementsCalculator = RookMovementsCalculator(colorTurn, boardPositions)
    private val queenMovementsCalculator: QueenMovementsCalculator = QueenMovementsCalculator(colorTurn, boardPositions)
    private val bishopMovementsCalculator: BishopMovementsCalculator = BishopMovementsCalculator(colorTurn, boardPositions)

    fun calculatePseudoLegalMoves(colorTurn: Color): MutableList<String> {
        //TODO: Acrescentar indicação de promoção e rook

        val newLegalMoves = mutableListOf<String>()

        boardPositions.forEach { boardLine ->
            boardLine.filterNotNull().filter {
                it.getColor() == colorTurn
            }.forEach {
                val line = it.position.line
                val col = it.position.column
                when(it.value) {
                    'P', 'p' -> {
                        pawnMovementsCalculator.calculate(newLegalMoves, line, col)
                    }
                    'N', 'n' -> {
                        horseMovementsCalculator.calculate(newLegalMoves, line, col)
                    }
                    'K', 'k' -> {
                        kingMovementsCalculator.calculate(newLegalMoves, line, col)
                    }
                    'R', 'r' -> {
                        rookMovementsCalculator.calculate(newLegalMoves, line, col)
                    }
                    'B', 'b' -> {
                        bishopMovementsCalculator.calculate(newLegalMoves, line, col)
                    }
                    'Q', 'q' -> {
                        queenMovementsCalculator.calculate(newLegalMoves, line, col)
                    }
                }
            }
        }

        return newLegalMoves
    }
}



