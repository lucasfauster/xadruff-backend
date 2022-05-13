package com.uff.br.xadruffbackend.calculator.piece

import com.uff.br.xadruffbackend.calculator.AbstractLegalMovementsCalculator
import com.uff.br.xadruffbackend.enum.Color
import com.uff.br.xadruffbackend.util.addNewMove


data class DiagonalsFree(
    var downLeftDiagonalFree: Boolean = true,
    var upLeftDiagonalFree:Boolean = true,
    var downRightDiagonalFree: Boolean = true,
    var upRithDiagonalFree: Boolean = true
) {
    fun isAllDirectionsOccupiedByAllies(): Boolean {
        return !(downLeftDiagonalFree || upLeftDiagonalFree || downRightDiagonalFree || upRithDiagonalFree)
    }
}

class BishopMovementsCalculator(colorTurn: Color,
                                boardPositions: List<List<String>>):
    AbstractLegalMovementsCalculator(colorTurn, boardPositions) {

    fun calculateBishopMoves(legalMovements: MutableList<String>, line: Int, col: Int){

        val diagonalsFree = DiagonalsFree()
        for(index in (1..7)){
            if(diagonalsFree.isAllDirectionsOccupiedByAllies()){
                break
            }
            diagonalsFree.verifyIfHasAlliesInAllDirections(line, col, index)
            legalMovements.calculateCaptureMovements(line, col, index, diagonalsFree)
            legalMovements.calculateNormalMovements(line, col, index, diagonalsFree)
        }
    }

    private fun MutableList<String>.calculateCaptureMovements(
        line: Int,
        col: Int,
        index: Int,
        diagonalsFree: DiagonalsFree) {

        if(diagonalsFree.upLeftDiagonalFree && hasEnemy(line+index, col)) {
            addNewMove(line, col, line - index, col - index, "C")
            diagonalsFree.upLeftDiagonalFree = false
        }
        if(diagonalsFree.downLeftDiagonalFree && hasEnemy(line-index, col)) {
            addNewMove(line, col, line + index, col - index, "C")
            diagonalsFree.downLeftDiagonalFree = false
        }
        if(diagonalsFree.upRithDiagonalFree && hasEnemy(line, col-index)) {
            addNewMove(line, col, line - index, col + index, "C")
            diagonalsFree.upRithDiagonalFree = false
        }
        if(diagonalsFree.downRightDiagonalFree && hasEnemy(line, col+index)) {
            addNewMove(line, col, line + index, col + index, "C")
            diagonalsFree.downRightDiagonalFree = false
        }
    }

    private fun MutableList<String>.calculateNormalMovements(
        line: Int,
        col: Int,
        index: Int,
        diagonalsFree: DiagonalsFree) {

        if(diagonalsFree.upLeftDiagonalFree){
            addNewMove(line, col, line - index, col - index)
        }
        if(diagonalsFree.downLeftDiagonalFree){
            addNewMove(line, col, line + index, col - index)
        }
        if(diagonalsFree.upRithDiagonalFree) {
            addNewMove(line, col, line - index, col + index)
        }
        if(diagonalsFree.downRightDiagonalFree) {
            addNewMove(line, col, line + index, col + index)
        }
    }

    private fun DiagonalsFree.verifyIfHasAlliesInAllDirections(line: Int, col: Int, index: Int){
        if(hasAlly(line-index, col-index)){
            upLeftDiagonalFree = false
        }
        if(hasAlly(line+index, col-index)){
            downLeftDiagonalFree = false
        }
        if(hasAlly(line-index, col+index)){
            upRithDiagonalFree = false
        }
        if(hasAlly(line+index, col+index)){
            downRightDiagonalFree = false
        }
    }
}
