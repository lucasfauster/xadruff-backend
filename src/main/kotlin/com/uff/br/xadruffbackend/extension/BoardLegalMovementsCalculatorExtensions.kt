package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.LegalMovements
import com.uff.br.xadruffbackend.model.Position
import com.uff.br.xadruffbackend.model.direction.Direction
import com.uff.br.xadruffbackend.model.piece.Pawn


object BoardLegalMovementsCalculatorExtensions {

    fun Board.calculatePseudoLegalMoves(): LegalMovements {
        val legalMovements = LegalMovements()
        positions.forEach { boardLine ->
            boardLine.filter {
                it.piece?.color == turnColor
            }.forEach { position ->
                legalMovements.addAll(calculateLegalMovementsInPosition(position))
            }
        }
        return legalMovements
    }

    fun Board.calculateLegalMovementsInPosition(position: Position): LegalMovements{
        val legalMovements = LegalMovements()
        var availableDirections = position.piece!!.directions
        var indexRange = position.piece!!.movementRange

        if(position.piece is Pawn){
            indexRange = position.handlePawnFirstMovementRange()
        }

        for(index in 1..indexRange){
            getLegalPositionsInRange(index, availableDirections, position, legalMovements)

            availableDirections = availableDirections.filter{ direction ->
                val futurePosition = getFuturePositionOrNull(direction, position, index)
                futurePosition?.isEmpty() ?: false
            }
        }
        return legalMovements
    }

    private fun Board.getLegalPositionsInRange(
        range: Int,
        availableDirections: List<Direction>,
        position: Position,
        legalMovements: LegalMovements,
    ) {
        val futurePositions = getLegalFuturePositions(range, availableDirections, position)
        futurePositions.forEach {
            legalMovements.addNewMove(
                originPosition = position,
                futurePosition = it,
                action = it.action
            )
        }
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

    fun Board.canMove(position: Position, hasMovement: Boolean = true, hasCapture: Boolean = true) =
        (hasMovement && position.isEmpty()) || (hasCapture && position.hasEnemy(turnColor))

    fun Board.buildAction(position: Position): String {
        return if (position.hasEnemy(turnColor)) {
            "C"
        } else {
            ""
        }
    }

    fun Board.getLegalFuturePositions(index: Int, directions: List<Direction>, position: Position):
            List<Position> {
        return directions.mapNotNull { direction ->
            val futurePosition = getFuturePositionOrNull(direction, position, index)
            futurePosition?.let{
                if (canMove(it, direction.hasMovement, direction.hasCapture)) {
                    Position(
                        line = it.line,
                        column = it.column,
                        action = buildAction(it),
                        piece = it.piece
                    )
                } else null
            }
        }
    }
}