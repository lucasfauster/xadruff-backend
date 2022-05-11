package com.uff.br.xadruffbackend.calculator.piece

import com.uff.br.xadruffbackend.calculator.AbstractLegalMovementsCalculator
import com.uff.br.xadruffbackend.enum.Color


class BishopMovementsCalculator(colorTurn: Color,
                                boardPositions: List<List<String>>):
    AbstractLegalMovementsCalculator(colorTurn, boardPositions) {

    fun calculateBishopMoves(legalMovements: MutableList<String>, line: Int, col: Int){
    }

    fun MutableList<String>.calculateCaptureMovements(line: Int, col: Int){
    }

    fun MutableList<String>.calculateNoCaptureMovements(line: Int, col: Int, direction: Int) {
    }
}





