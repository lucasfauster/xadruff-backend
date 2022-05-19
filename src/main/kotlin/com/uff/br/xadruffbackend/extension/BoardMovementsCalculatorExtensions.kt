package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.LegalMovements
import com.uff.br.xadruffbackend.model.Position
import com.uff.br.xadruffbackend.model.direction.Direction
import com.uff.br.xadruffbackend.model.enum.Color


object BoardMovementsCalculatorExtensions {

    fun Board.calculatePseudoLegalMoves(): LegalMovements {
        return positions.map { line ->
            line.filter {
                it.piece?.color == turnColor
            }.map { position ->
                calculateLegalMovementsInPosition(position)
            }
        }.flattenToLegalMovements()
    }

    fun Board.calculateLegalMovementsInPosition(position: Position): LegalMovements {
        var availableDirections = position.piece!!.directions
        val legalMovementsList = (1..position.getMovementRange()).map{ range ->
            getLegalPositionsInRange(range, availableDirections, position)
                .also {
                    availableDirections = filterAvailableDirections(availableDirections, position, range)
                }
        }
        return legalMovementsList.flattenToLegalMovements()
    }

    private fun Board.filterAvailableDirections(availableDirections: List<Direction>, position: Position, index: Int)
    = availableDirections.filter { direction ->
        getFuturePositionOrNull(direction, position, index)?.isEmpty()
        ?: false
    }

    private fun Board.getLegalPositionsInRange(range: Int, availableDirections: List<Direction>, position: Position)
    : LegalMovements {
        return getLegalFuturePositions(range, availableDirections, position)
            .map {
                createMovement(
                    originPosition = position,
                    futurePosition = it,
                    action = it.action
                )
            }.toLegalMovements()
    }

    private fun Board.getFuturePositionOrNull(
        direction: Direction,
        position: Position,
        index: Int,
    ): Position? {
        val futureLine = direction.getFutureLine(position.line, index)
        val futureColumn = direction.getFutureColumn(position.column, index)
        return positions.getOrNull(futureLine)?.getOrNull(futureColumn)
    }

    private fun Board.getLegalFuturePositions(index: Int, directions: List<Direction>, position: Position)
    : List<Position> {
        return directions.mapNotNull { direction ->
            getFuturePositionOrNull(direction, position, index)
                ?.let{
                    if (position.piece!!.canMove(it, direction.hasMovement, direction.hasCapture)) {
                        Position(
                            line = it.line,
                            column = it.column,
                            action = buildAction(it, position.piece!!.color),
                            piece = it.piece
                        )
                    } else null
                }
            }
    }

    fun buildAction(position: Position, pieceColor: Color): String {
        return if (position.hasEnemyPiece(pieceColor)) {
            "C"
        } else {
            ""
        }
    }
}