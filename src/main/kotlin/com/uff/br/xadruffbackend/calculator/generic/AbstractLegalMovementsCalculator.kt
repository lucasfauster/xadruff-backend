package com.uff.br.xadruffbackend.calculator.generic

import com.uff.br.xadruffbackend.enum.Color
import com.uff.br.xadruffbackend.model.Piece
import com.uff.br.xadruffbackend.model.Position
import com.uff.br.xadruffbackend.model.direction.Direction


abstract class AbstractLegalMovementsCalculator(protected val colorTurn: Color,
                                                protected val boardPositions: List<List<Piece?>>) {

    fun hasAlly(line: Int, col: Int) =
        boardPositions.getOrNull(line)?.getOrNull(col)?.getColor() == colorTurn

    fun isEmpty(line: Int, col: Int) =
        boardPositions.getOrNull(line)?.getOrNull(col) == null

    fun hasEnemy(line: Int, col: Int) =
        boardPositions.getOrNull(line)?.getOrNull(col)?.getColor() != colorTurn

    fun canMove(line: Int, col: Int) = isEmpty(line, col) || hasEnemy(line, col)

    fun buildAction(futureLine: Int, futureColumn: Int): String {
        return if (hasEnemy(futureLine, futureColumn)) {
            "C"
        } else {
            ""
        }
    }

    fun getLegalFuturePositions(index: Int, directions: List<Direction>): List<Position> {
        return directions.mapNotNull {
            val futureLine = it.getFutureLine(index)
            val futureColumn = it.getFutureColumn(index)
            if (canMove(futureLine, futureColumn)) {
                Position(
                    line = futureLine,
                    column = futureColumn,
                    action = buildAction(futureLine, futureColumn)
                )
            } else null
        }
    }

    abstract fun calculate(legalMovements: MutableList<String>, line: Int, col: Int)

    fun MutableList<String>.calculate(directions: List<Direction>, indexRange: Int = 7) {
        var availableDirections = directions

        for(index in 1..indexRange){
            val futurePositions = getLegalFuturePositions(index, availableDirections)
            futurePositions.forEach {
                addNewMove(
                    originLine = availableDirections.first().line,
                    originCol = availableDirections.first().column,
                    futureLine = it.line,
                    futureCol = it.column,
                    action = it.action
                )
            }

            availableDirections = directions.filter{
                isEmpty(it.getFutureLine(index), it.getFutureColumn(index))
            }

        }
    }

    private fun getFuturePositionFromMove(index: String, legalMoves: List<String>): List<String> =
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