package com.uff.br.xadruffbackend.model.piece

import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.direction.Direction
import com.uff.br.xadruffbackend.model.direction.DownColumnStraight
import com.uff.br.xadruffbackend.model.direction.DownLeftDiagonal
import com.uff.br.xadruffbackend.model.direction.DownRightDiagonal
import com.uff.br.xadruffbackend.model.direction.UpColumnStraight
import com.uff.br.xadruffbackend.model.direction.UpLeftDiagonal
import com.uff.br.xadruffbackend.model.direction.UpRightDiagonal

class Pawn(value: Char): Piece(value) {

    override fun calculateLegalMovements(line: Int, col: Int, board: Board): MutableList<String> {
        var initialLine = 6
        var moveDirection = listOf<Direction>(UpColumnStraight(line, col))
        var captureDirections = listOf(UpRightDiagonal(line, col), UpLeftDiagonal(line, col))

        if(board.colorTurn == Color.BLACK) {
            moveDirection = listOf<Direction>(DownColumnStraight(line, col))
            initialLine = 1
            captureDirections = listOf(DownRightDiagonal(line, col), DownLeftDiagonal(line, col))
        }

        val legalMovements: MutableList<String> = calculate(
            directions = moveDirection,
            indexRange = getIndexRange(line, initialLine),
            board = board
        )

        legalMovements.addAll(calculateCaptureMovement(captureDirections, board))

        return legalMovements
    }

    private fun getIndexRange(line: Int, initialLine: Int): Int {
        return if (line == initialLine) {
            2
        } else {
            1
        }
    }

    fun calculateCaptureMovement(captureDirections: List<Direction>, board: Board): MutableList<String>{
        val pseudoLegalFuturePositions = getLegalFuturePositions(1, captureDirections, board)
        val legalCaptureMovements: MutableList<String> = mutableListOf()

        pseudoLegalFuturePositions.filter {
            it.action == "C"
        }.forEach {
            legalCaptureMovements.addNewMove(
                originLine = captureDirections.first().line,
                originCol = captureDirections.first().column,
                futureLine = it.line,
                futureCol = it.column,
                action = it.action
            )
        }

        return legalCaptureMovements
    }
}