package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.LegalMovements
import com.uff.br.xadruffbackend.model.Position
import com.uff.br.xadruffbackend.model.direction.Direction
import com.uff.br.xadruffbackend.model.piece.Piece
import org.slf4j.LoggerFactory

object BoardMovementsCalculatorExtensions {
    private val logger = LoggerFactory.getLogger(this::class.java)

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
        logger.debug(
            "Calculating legal moves for piece ${position.piece} at " +
                "line ${position.line} - column ${position.column}"
        )
        var availableDirections = position.piece!!.directions
        val legalMovementsList = (1..position.getMovementRange()).map { range ->
            getLegalPositionsInRange(range, availableDirections, position)
                .also {
                    logger.debug("Legal positions $it calculated in range $range")
                    availableDirections = filterAvailableDirections(availableDirections, position, range)
                    logger.debug("Available directions $availableDirections in range $range")
                }
        }
        return legalMovementsList.flattenToLegalMovements()
            .also {
                logger.debug(
                    "LegalMovements calculated $it for piece ${position.piece} at " +
                        "line ${position.line} - column ${position.column}"
                )
            }
    }

    private fun Board.filterAvailableDirections(availableDirections: List<Direction>, position: Position, index: Int) =
        availableDirections.filter {
            getFuturePositionOrNull(it, position, index)
                ?.isEmpty() ?: false
        }

    private fun Board.getLegalPositionsInRange(
        range: Int,
        availableDirections: List<Direction>,
        position: Position
    ): LegalMovements {
        val legalMovementList = availableDirections.mapNotNull { direction ->
            getFuturePositionOrNull(direction, position, range)
                ?.let { futurePosition ->
                    position.buildMovementOrNull(futurePosition, direction.hasMovement, direction.hasCapture)
                }.also {
                    logger.debug("Position {} calculated for direction {} in range {}", it, direction, range)
                }
        }
        return legalMovementList.toLegalMovements()
    }

    private fun Position.buildMovementOrNull(
        futurePosition: Position,
        hasMovement: Boolean,
        hasCapture: Boolean
    ): String? {
        return if (piece!!.canMove(futurePosition, hasMovement, hasCapture)) {
            createMovement(
                originPosition = this,
                futurePosition = futurePosition,
                action = buildAction(futurePosition, piece),
            )
        } else null
    }

    private fun Board.getFuturePositionOrNull(direction: Direction, position: Position, range: Int): Position? {
        val futureLine = direction.getFutureLine(position.line, range)
        val futureColumn = direction.getFutureColumn(position.column, range)
        return positions.getOrNull(futureLine)?.getOrNull(futureColumn)
    }

    fun buildAction(position: Position, originPiece: Piece?): String {
        return if (position.hasEnemyPiece(originPiece)) {
            "C"
        } else {
            ""
        }
    }
}
