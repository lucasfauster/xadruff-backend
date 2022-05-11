package com.uff.br.xadruffbackend.calculator.piece

import com.uff.br.xadruffbackend.calculator.AbstractLegalMovementsCalculator
import com.uff.br.xadruffbackend.calculator.addNewMove
import com.uff.br.xadruffbackend.enum.Color


class HorseMovementsCalculator(colorTurn: Color,
                               boardPositions: List<List<String>>):
    AbstractLegalMovementsCalculator(colorTurn, boardPositions) {


    fun calculateHorseMoves(legalMovements: MutableList<String>, line: Int, col: Int){
        for(index in 0..1){

            legalMovements.calculateCaptureMovements(line, col, index)
            legalMovements.calculateNoCaptureMovements(line, col, index)
        }
    }

    fun MutableList<String>.calculateCaptureMovements(line: Int, col: Int, index: Int){

        if(hasEnemy(line+2-index, col+1+index)) {
            addNewMove(line, col,line+2-index, col+1+index, "C")
        }
        if(hasEnemy(line+2-index, col-1+index)) {
            addNewMove(line, col,line+2-index, col-1+index, "C")
        }
        if(hasEnemy(line-2-index, col+1+index)) {
            addNewMove(line, col,line-2-index, col+1+index, "C")
        }
        if(hasEnemy(line-2-index, col-1+index)) {
            addNewMove(line, col,line-2-index, col-1+index, "C")
        }
    }

    fun MutableList<String>.calculateNoCaptureMovements(line: Int, col: Int, index: Int) {
        if(isEmpty(line+2-index, col+1+index)){
            addNewMove(line, col,line+2-index, col+1+index)
        }
        if(isEmpty(line+2-index, col-1+index)){
            addNewMove(line, col,line+2-index, col-1+index)
        }
        if(isEmpty(line-2-index, col+1+index)){
            addNewMove(line, col,line-2-index, col+1+index)
        }
        if(isEmpty(line-2-index, col-1+index)){
            addNewMove(line, col,line-2-index, col-1+index)
        }
    }
}





