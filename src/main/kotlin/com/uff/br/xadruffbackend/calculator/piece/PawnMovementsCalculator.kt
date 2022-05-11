package com.uff.br.xadruffbackend.calculator.piece

import com.uff.br.xadruffbackend.calculator.AbstractLegalMovementsCalculator
import com.uff.br.xadruffbackend.calculator.addNewMove
import com.uff.br.xadruffbackend.enum.Color


class PawnMovementsCalculator(colorTurn: Color,
                              boardPositions: List<List<String>>):
    AbstractLegalMovementsCalculator(colorTurn, boardPositions) {

    fun calculatePawnMoves(legalMovements: MutableList<String>, line: Int, col: Int){
        //TODO: implementar en passant
        val direction = if (colorTurn == Color.WHITE) {
            -1 // se for branco ele move apenas para linhas de index menores
        } else {
            1
        }
        legalMovements.calculateCaptureMovements(line, col, direction)
        legalMovements.calculateNormalMovements(line, col, direction)
    }

    fun MutableList<String>.calculateCaptureMovements(line: Int, col: Int, direction: Int){
        if(hasEnemy(line+(1*direction), col-1)) {
            addNewMove(line, col,line+(1*direction), col-1, "C")
        }
        if(hasEnemy(line+(1*direction), col+1)) {
            addNewMove(line, col,line+(1*direction), col+1, "C")
        }
    }

    fun MutableList<String>.calculateNormalMovements(line: Int, col: Int, direction: Int) {
        if (isEmpty(line + (1 * direction), col)) {
            addNewMove(line, col, line + (1 * direction), col)
        }
        if (isEmpty(line + (2 * direction), col) && isEmpty(line - 1, col) && line == 7) {
            addNewMove(line, col, line + (2 * direction), col)
        }
    }
}





