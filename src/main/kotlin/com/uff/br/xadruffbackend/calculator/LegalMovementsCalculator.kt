package com.uff.br.xadruffbackend.calculator

import com.uff.br.xadruffbackend.calculator.piece.BishopMovementsCalculator
import com.uff.br.xadruffbackend.calculator.piece.HorseMovementsCalculator
import com.uff.br.xadruffbackend.calculator.piece.KingMovementsCalculator
import com.uff.br.xadruffbackend.calculator.piece.PawnMovementsCalculator
import com.uff.br.xadruffbackend.calculator.piece.QueenMovementsCalculator
import com.uff.br.xadruffbackend.enum.Color

fun String.getColor(): Color {
    forEach {
        if(!it.isUpperCase())
            return Color.BLACK
    }
    return Color.WHITE
}

class LegalMovementsCalculator(
    private val boardPositions: List<List<String>>,
    colorTurn: Color) {

    private val pawnMovementsCalculator:PawnMovementsCalculator = PawnMovementsCalculator(colorTurn, boardPositions)
    private val horseMovementsCalculator:HorseMovementsCalculator = HorseMovementsCalculator(colorTurn, boardPositions)
    private val kingMovementsCalculator:KingMovementsCalculator = KingMovementsCalculator(colorTurn, boardPositions)
    private val rookMovementsCalculator:BishopMovementsCalculator = BishopMovementsCalculator(colorTurn, boardPositions)
    private val queenMovementsCalculator:QueenMovementsCalculator = QueenMovementsCalculator(colorTurn, boardPositions)
    private val bishopMovementsCalculator:BishopMovementsCalculator = BishopMovementsCalculator(colorTurn, boardPositions)

    fun calculateLegalMoves(colorTurn: Color): MutableList<String> {
        //TODO: Acrescentar indicação de promoção, rook e captura

        val newLegalMoves = mutableListOf<String>()
        for (line in 0..7){
            for (col in 0..7) {
                if(boardPositions[line][col].getColor() == colorTurn)
                    when (boardPositions[line][col].uppercase()) {
                        "P" -> {
                            pawnMovementsCalculator.calculatePawnMoves(newLegalMoves, line, col)
                        }
                        "N" -> {
                            horseMovementsCalculator.calculateHorseMoves(newLegalMoves, line, col)
                        }
                        "K" -> {
                            kingMovementsCalculator.calculateKingMoves(newLegalMoves, line, col)
                        }
                        "R" -> {
                            rookMovementsCalculator.calculateBishopMoves(newLegalMoves, line, col)
                        }
                    }
            }
        }
        return newLegalMoves
    }
}

fun indexToString(line: Int, col: Int): String =
    'a' + col + (8 - line).toString()



