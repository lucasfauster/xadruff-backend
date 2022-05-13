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

        val columnsAndLinesFree = DiagonalsFree()
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
        columnsAndLinesFree: DiagonalsFree) {

        if(columnsAndLinesFree.upLeftDiagonalFree && hasEnemy(line+index, col)) {
            addNewMove(line, col, line + index, col, "C")
            columnsAndLinesFree.upLeftDiagonalFree = false
        }
        if(columnsAndLinesFree.downLeftDiagonalFree && hasEnemy(line-index, col)) {
            addNewMove(line, col, line - index, col, "C")
            columnsAndLinesFree.downLeftDiagonalFree = false
        }
        if(columnsAndLinesFree.upRithDiagonalFree && hasEnemy(line, col-index)) {
            addNewMove(line, col, line, col - index, "C")
            columnsAndLinesFree.upRithDiagonalFree = false
        }
        if(columnsAndLinesFree.downRightDiagonalFree && hasEnemy(line, col+index)) {
            addNewMove(line, col, line, col + index, "C")
            columnsAndLinesFree.downRightDiagonalFree = false
        }
    }

    fun MutableList<String>.calculateNormalMovements(
        line: Int,
        col: Int,
        index: Int,
        columnsAndLinesFree: DiagonalsFree) {

        if(columnsAndLinesFree.upLeftDiagonalFree){
            addNewMove(line, col, line+index, col)
        }
        if(columnsAndLinesFree.downLeftDiagonalFree){
            addNewMove(line, col, line-index, col)
        }
        if(columnsAndLinesFree.upRithDiagonalFree) {
            addNewMove(line, col, line, col-index)
        }
        if(columnsAndLinesFree.downRightDiagonalFree) {
            addNewMove(line, col, line, col+index)
        }
    }

    fun DiagonalsFree.verifyIfHasAlliesInAllDirections(line: Int, col: Int, index: Int){
        if(hasAlly(line+index, col)){
            upLeftDiagonalFree = false
        }
        if(hasAlly(line-index, col)){
            downLeftDiagonalFree = false
        }
        if(hasAlly(line, col-index)){
            upRithDiagonalFree = false
        }
        if(hasAlly(line, col+index)){
            downRightDiagonalFree = false
        }
    }
}





