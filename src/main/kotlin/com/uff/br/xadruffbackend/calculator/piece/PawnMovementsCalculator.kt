package com.uff.br.xadruffbackend.calculator.piece

import com.uff.br.xadruffbackend.calculator.AbstractLegalMovementsCalculator
import com.uff.br.xadruffbackend.calculator.getColor
import com.uff.br.xadruffbackend.enum.Color
import com.uff.br.xadruffbackend.util.addNewMove


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

    private fun MutableList<String>.calculateCaptureMovements(line: Int, col: Int, direction: Int){
        if(hasEnemy(line+(1*direction), col-1)) {
            addNewMove(line, col,line+(1*direction), col-1, "C")
        }
        if(hasEnemy(line+(1*direction), col+1)) {
            addNewMove(line, col,line+(1*direction), col+1, "C")
        }
    }

    private fun MutableList<String>.calculateNormalMovements(line: Int, col: Int, direction: Int) {
        if (isEmpty(line + (1 * direction), col)) {
            addNewMove(line, col, line + (1 * direction), col)
        }
        if (isEmpty(line + (2 * direction), col) && isEmpty(line - 1, col)
            && pawnIsOnInitialPosition(line, col)) {
            addNewMove(line, col, line + (2 * direction), col)
        }
    }

    private fun pawnIsOnInitialPosition(line: Int, col: Int) =
        if (boardPositions.getOrNull(line)?.getOrNull(col)?.getColor() == Color.WHITE) {
            line == 7
        } else {
            line == 1
        }
}





