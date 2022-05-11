package com.uff.br.xadruffbackend.calculator.piece

import com.uff.br.xadruffbackend.calculator.AbstractLegalMovementsCalculator
import com.uff.br.xadruffbackend.enum.Color


class QueenMovementsCalculator(colorTurn: Color,
                               boardPositions: List<List<String>>):
    AbstractLegalMovementsCalculator(colorTurn, boardPositions) {

    fun calculateQueenMoves(legalMovements: MutableList<String>, line: Int, col: Int){
    }

    fun MutableList<String>.calculateCaptureMovements(line: Int, col: Int, direction: Int){
    }

    fun MutableList<String>.calculateNormalMovements(line: Int, col: Int, direction: Int) {
    }
}





