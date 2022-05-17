package com.uff.br.xadruffbackend.calculator

import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.LegalMovements
import com.uff.br.xadruffbackend.model.Position
import com.uff.br.xadruffbackend.model.direction.Direction


abstract class AbstractLegalMovementsCalculator {

    abstract fun calculateLegalMovements(line: Int, col: Int, board: Board, legalMovements: LegalMovements)

    fun hasAlly(line: Int, col: Int, board: Board) =
        board.positions.getOrNull(line)?.getOrNull(col)?.piece?.getColor() == board.colorTurn

    fun isEmpty(line: Int, col: Int, board: Board) =
        board.positions.getOrNull(line)?.getOrNull(col)?.let{
            it.piece == null
        } ?: false

    fun hasEnemy(line: Int, col: Int, board: Board) =
        board.positions.getOrNull(line)?.getOrNull(col)?.piece?.let{
            it.getColor() != board.colorTurn
        } ?: false

    fun canMove(line: Int, col: Int, board: Board) =
        isEmpty(line, col, board) || hasEnemy(line, col, board)

    fun buildAction(futureLine: Int, futureColumn: Int, board: Board): String {
        return if (hasEnemy(futureLine, futureColumn, board)) {
            "C"
        } else {
            ""
        }
    }

    fun getLegalFuturePositions(index: Int, directions: List<Direction>, board: Board): List<Position> {
        return directions.mapNotNull {
            val futureLine = it.getFutureLine(index)
            val futureColumn = it.getFutureColumn(index)
            if (canMove(futureLine, futureColumn, board)) {
                Position(
                    line = futureLine,
                    column = futureColumn,
                    action = buildAction(futureLine, futureColumn, board),
                    piece = board.positions[futureLine][futureColumn].piece
                )
            } else null
        }
    }

    fun LegalMovements.calculate(directions: List<Direction>,
                                 indexRange: Int = 7,
                                 board: Board) {
        var availableDirections = directions

        for(index in 1..indexRange){
            val futurePositions = getLegalFuturePositions(index, availableDirections, board)
            futurePositions.forEach {
                addNewMove(
                    originPosition = Position(directions.first().line, directions.first().column),
                    futurePosition = it,
                    action = it.action
                )
            }
            availableDirections = availableDirections.filter{
                isEmpty(it.getFutureLine(index), it.getFutureColumn(index), board)
            }
        }
    }
}