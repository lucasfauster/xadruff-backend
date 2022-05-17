package com.uff.br.xadruffbackend.calculator

import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.Position
import com.uff.br.xadruffbackend.model.direction.Direction


abstract class AbstractLegalMovementsCalculator {

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

    fun canMove(line: Int, col: Int, board: Board) = isEmpty(line, col, board) || hasEnemy(line, col, board)

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

    abstract fun calculateLegalMovements(line: Int, col: Int, board: Board): MutableList<String>

    fun calculate(directions: List<Direction>, indexRange: Int = 7, board: Board): MutableList<String> {
        var availableDirections = directions
        val legalMovements: MutableList<String> = mutableListOf()

        for(index in 1..indexRange){
            val futurePositions = getLegalFuturePositions(index, availableDirections, board)
            futurePositions.forEach {
                legalMovements.addNewMove(
                    originLine = availableDirections.first().line,
                    originCol = availableDirections.first().column,
                    futureLine = it.line,
                    futureCol = it.column,
                    action = it.action
                )
            }
            availableDirections = directions.filter{
                isEmpty(it.getFutureLine(index), it.getFutureColumn(index), board)
            }
        }
        return legalMovements
    }

    fun getFuturePositionFromMove(index: String, legalMoves: List<String>): List<String> =
        legalMoves.filter { it.slice(0..1) == index }
            .map { it.slice(2..3) }

    fun List<String>.legalMovementsToMap(): MutableMap<String, List<String>> {
        val movesMap: MutableMap<String, List<String>> = mutableMapOf()
        for (move in this) {
            val index = move.slice(0..1)
            if (!movesMap.containsKey(index)) {
                movesMap[index] = getFuturePositionFromMove(index, this)
            }
        }
        return movesMap
    }

    fun MutableList<String>.addNewMove(
        originLine: Int, originCol: Int,
        futureLine: Int, futureCol: Int,
        action: String = ""
    ) {
        add(indexToString(originLine, originCol) + indexToString(futureLine, futureCol) + action)
    }

    fun indexToString(line: Int, col: Int): String =
        'a' + col + (8 - line).toString()
}