package com.uff.br.xadruffbackend.calculator.piece

import com.uff.br.xadruffbackend.calculator.generic.AbstractLegalMovementsCalculator
import com.uff.br.xadruffbackend.enum.Color
import com.uff.br.xadruffbackend.model.Piece
import com.uff.br.xadruffbackend.model.direction.Direction
import com.uff.br.xadruffbackend.model.direction.DownColumnStraight
import com.uff.br.xadruffbackend.model.direction.DownLeftDiagonal
import com.uff.br.xadruffbackend.model.direction.DownRightDiagonal
import com.uff.br.xadruffbackend.model.direction.UpColumnStraight
import com.uff.br.xadruffbackend.model.direction.UpLeftDiagonal
import com.uff.br.xadruffbackend.model.direction.UpRightDiagonal


class PawnMovementsCalculator(colorTurn: Color, boardPositions: List<List<Piece?>>):
    AbstractLegalMovementsCalculator(colorTurn, boardPositions) {

    override fun calculate(legalMovements: MutableList<String>, line: Int, col: Int) {
        var initialLine = 1
        var moveDirection = listOf<Direction>(UpColumnStraight(line, col))
        var captureDirections = listOf(UpRightDiagonal(line, col), UpLeftDiagonal(line, col))

        if(colorTurn == Color.BLACK) {
            moveDirection = listOf<Direction>(DownColumnStraight(line, col))
            initialLine = 6
            captureDirections = listOf(DownRightDiagonal(line, col), DownLeftDiagonal(line, col))
        }

        val indexRange = getIndexRange(line, initialLine)
        legalMovements.calculate(moveDirection, indexRange)
        calculateCaptureMovement(legalMovements, captureDirections)
    }

    private fun getIndexRange(line: Int, initialLine: Int): Int {
        return if (line == initialLine) {
            2
        } else {
            1
        }
    }

    fun calculateCaptureMovement(legalMovements: MutableList<String>, captureDirections: List<Direction>){
        val pseudoLegalFuturePositions = getLegalFuturePositions(1, captureDirections)
        pseudoLegalFuturePositions.filter {
            it.action == "C"
        }.forEach {
            legalMovements.addNewMove(
                originLine = captureDirections.first().line,
                originCol = captureDirections.first().column,
                futureLine = it.line,
                futureCol = it.column,
                action = it.action
            )
        }
    }
}





