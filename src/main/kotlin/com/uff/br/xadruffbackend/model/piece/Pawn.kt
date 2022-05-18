package com.uff.br.xadruffbackend.model.piece

import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.LegalMovements
import com.uff.br.xadruffbackend.model.direction.Direction
import com.uff.br.xadruffbackend.model.direction.DownLeftDiagonal
import com.uff.br.xadruffbackend.model.direction.DownRightDiagonal
import com.uff.br.xadruffbackend.model.direction.DownStraight
import com.uff.br.xadruffbackend.model.direction.UpLeftDiagonal
import com.uff.br.xadruffbackend.model.direction.UpRightDiagonal
import com.uff.br.xadruffbackend.model.direction.UpStraight
import com.uff.br.xadruffbackend.model.enum.Color

class Pawn(value: Char) : Piece(value) {

    override fun calculateLegalMovements(line: Int, col: Int, board: Board, legalMovements: LegalMovements) {
        var initialLine = 6
        var moveDirection = listOf<Direction>(UpStraight(line, col))
        var captureDirections = listOf(UpRightDiagonal(line, col), UpLeftDiagonal(line, col))
        if(board.colorTurn == Color.BLACK) {
            moveDirection = listOf<Direction>(DownStraight(line, col))
            initialLine = 1
            captureDirections = listOf(DownRightDiagonal(line, col), DownLeftDiagonal(line, col))
        }

        legalMovements.movements.addAll(
            calculateMoves(
            directions = moveDirection,
            indexRange = getIndexRange(line, initialLine),
            board = board,
            captures = false)
        )

        legalMovements.movements.addAll(
            calculateMoves(
            directions = captureDirections,
            indexRange = 1,
            board = board,
            captures = true)
        )
    }

    fun calculateMoves(directions: List<Direction>, indexRange: Int, board: Board, captures: Boolean): List<String> {
        val auxLegalMoves = LegalMovements(mutableListOf())
        auxLegalMoves.calculate(
            directions = directions,
            indexRange = indexRange,
            board = board
        )

        return if (captures) auxLegalMoves.movements.filter { it.contains("C")}
                else auxLegalMoves.movements.filter { !it.contains("C")}
    }

    private fun getIndexRange(line: Int, initialLine: Int): Int {
        return if (line == initialLine) {
            2
        } else {
            1
        }
    }
}

