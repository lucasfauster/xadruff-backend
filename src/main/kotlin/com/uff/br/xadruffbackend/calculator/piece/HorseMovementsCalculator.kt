package com.uff.br.xadruffbackend.calculator.piece

import com.uff.br.xadruffbackend.calculator.AbstractLegalMovementsCalculator
import com.uff.br.xadruffbackend.enum.Color
import com.uff.br.xadruffbackend.util.addNewMove


class HorseMovementsCalculator(colorTurn: Color,
                               boardPositions: List<List<String>>):
    AbstractLegalMovementsCalculator(colorTurn, boardPositions) {


    fun calculateHorseMoves(legalMovements: MutableList<String>, line: Int, col: Int){
        for(index in 0..1){

            legalMovements.calculateCaptureMovements(line, col, index)
            legalMovements.calculateNormalMovements(line, col, index)
        }
    }

    private fun MutableList<String>.calculateCaptureMovements(line: Int, col: Int, index: Int){

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

    private fun MutableList<String>.calculateNormalMovements(line: Int, col: Int, index: Int) {
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





