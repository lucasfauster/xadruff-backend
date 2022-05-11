package com.uff.br.xadruffbackend.calculator.piece

import com.uff.br.xadruffbackend.calculator.AbstractLegalMovementsCalculator
import com.uff.br.xadruffbackend.enum.Color
import com.uff.br.xadruffbackend.util.addNewMove


data class ColumnsAndLinesFree(
    var upColumnFree: Boolean = true,
    var downColumnFree:Boolean = true,
    var rightLineFree: Boolean = true,
    var leftLineFree: Boolean = true
) {
    fun isAllDirectionsOccupiedByAllies(): Boolean {
        return !(upColumnFree || downColumnFree || rightLineFree || leftLineFree)
    }
}

class RookMovementsCalculator(colorTurn: Color,
                              boardPositions: List<List<String>>):
    AbstractLegalMovementsCalculator(colorTurn, boardPositions) {

    fun calculateRookMoves(legalMovements: MutableList<String>, line: Int, col: Int){

        val columnsAndLinesFree = ColumnsAndLinesFree()
        for(index in (1..7)){
            if(columnsAndLinesFree.isAllDirectionsOccupiedByAllies()){
                break
            }
            columnsAndLinesFree.verifyIfHasAlliesInAllDirections(line, col, index)
            legalMovements.calculateCaptureMovements(line, col, index, columnsAndLinesFree)
            legalMovements.calculateNormalMovements(line, col, index, columnsAndLinesFree)
        }
    }

    fun MutableList<String>.calculateCaptureMovements(
        line: Int,
        col: Int,
        index: Int,
        columnsAndLinesFree: ColumnsAndLinesFree) {

        if(columnsAndLinesFree.downColumnFree && hasEnemy(line+index, col)) {
            addNewMove(line, col, line + index, col, "C")
            columnsAndLinesFree.downColumnFree = false
        }
        if(columnsAndLinesFree.upColumnFree && hasEnemy(line-index, col)) {
            addNewMove(line, col, line - index, col, "C")
            columnsAndLinesFree.upColumnFree = false
        }
        if(columnsAndLinesFree.leftLineFree && hasEnemy(line, col-index)) {
            addNewMove(line, col, line, col - index, "C")
            columnsAndLinesFree.leftLineFree = false
        }
        if(columnsAndLinesFree.rightLineFree && hasEnemy(line, col+index)) {
            addNewMove(line, col, line, col + index, "C")
            columnsAndLinesFree.rightLineFree = false
        }
    }

    fun MutableList<String>.calculateNormalMovements(
        line: Int,
        col: Int,
        index: Int,
        columnsAndLinesFree: ColumnsAndLinesFree) {

        if(columnsAndLinesFree.downColumnFree){
            addNewMove(line, col, line+index, col)
        }
        if(columnsAndLinesFree.upColumnFree){
            addNewMove(line, col, line-index, col)
        }
        if(columnsAndLinesFree.leftLineFree) {
            addNewMove(line, col, line, col-index)
        }
        if(columnsAndLinesFree.rightLineFree) {
            addNewMove(line, col, line, col+index)
        }
    }

    fun ColumnsAndLinesFree.verifyIfHasAlliesInAllDirections(line: Int, col: Int, index: Int){
        if(hasAlly(line+index, col)){
            downColumnFree = false
        }
        if(hasAlly(line-index, col)){
            upColumnFree = false
        }
        if(hasAlly(line, col-index)){
            leftLineFree = false
        }
        if(hasAlly(line, col+index)){
            rightLineFree = false
        }
    }
}





