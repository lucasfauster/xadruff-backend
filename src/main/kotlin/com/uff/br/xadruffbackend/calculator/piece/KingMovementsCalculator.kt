package com.uff.br.xadruffbackend.calculator.piece

import com.uff.br.xadruffbackend.calculator.AbstractLegalMovementsCalculator
import com.uff.br.xadruffbackend.calculator.addNewMove
import com.uff.br.xadruffbackend.enum.Color


class KingMovementsCalculator(colorTurn: Color,
                              boardPositions: List<List<String>>):
    AbstractLegalMovementsCalculator(colorTurn, boardPositions) {

    fun calculateKingMoves(legalMovements: MutableList<String>, line: Int, col: Int){
        //TODO: implementar o rook
        for(index in (-1..1)){
            legalMovements.calculateNormalMovements(line, col, index)
            legalMovements.calculateCaptureMovements(line, col, index)
        }
    }

    fun MutableList<String>.calculateCaptureMovements(line: Int, col: Int, index: Int){
        if(hasEnemy(line+index,col)){
            addNewMove(line, col, line+index, col, "C")
        }
        if(hasEnemy(line+index,col-1)){
            addNewMove(line, col, line+index, col-1, "C")
        }
        if(hasEnemy(line+index,col+1)){
            addNewMove(line, col, line+index, col+1, "C")
        }
    }

    fun MutableList<String>.calculateNormalMovements(line: Int, col: Int, index: Int) {
        if(isEmpty(line+index,col)){
            addNewMove(line, col, line+index, col)
        }
        if(isEmpty(line+index,col-1)){
            addNewMove(line, col, line+index, col-1)
        }
        if(isEmpty(line+index,col+1)){
            addNewMove(line, col, line+index, col+1)
        }
    }
}





