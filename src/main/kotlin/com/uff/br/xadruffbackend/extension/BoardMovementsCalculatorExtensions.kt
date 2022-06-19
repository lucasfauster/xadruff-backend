package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.extension.BoardCastleExtensions.handleCastleMovements
import com.uff.br.xadruffbackend.extension.BoardPromotionExtensions.handlePromotionInRange
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.LegalMovements
import com.uff.br.xadruffbackend.model.Position
import com.uff.br.xadruffbackend.model.direction.Direction
import com.uff.br.xadruffbackend.model.piece.King
import com.uff.br.xadruffbackend.model.piece.Pawn
import com.uff.br.xadruffbackend.model.piece.Piece
import com.uff.br.xadruffbackend.util.parallelMap
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import kotlin.math.absoluteValue

object BoardMovementsCalculatorExtensions {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun Board.calculatePseudoLegalMoves(withCastle: Boolean = true): LegalMovements {
        return runBlocking {
            positions.parallelMap { row ->
                row.filter {
                    it.piece?.color == turnColor
                }.parallelMap { position ->
                    calculateLegalMovementsInPosition(position, withCastle)
                }
            }.flattenToLegalMovements().also {
                logger.debug("Calculated all legal movements for color $turnColor = ${it.movements}.")
            }
        }
    }

    suspend fun Board.calculateLegalMovementsInPosition(
        position: Position,
        withCastle: Boolean = true
    ): LegalMovements {
        logger.debug(
            "Calculating legal moves for piece ${position.piece} at " +
                "row ${position.row} - column ${position.column}"
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
                if (withCastle) {
                    handleCastleMovements(position, it)
                }
            }
            .also {
                logger.debug(
                    "LegalMovements calculated $it for piece ${position.piece} at " +
                        "row ${position.row} - column ${position.column}"
                )
            }
    }

    fun Board.isKingInCheck(): Boolean {
        changeTurn()
        filterOnlyCaptureDirections()
        val legalMovements = this.calculatePseudoLegalMoves(withCastle = false)
        changeTurn()
        logger.debug("Legal movements for check checking: $legalMovements")
        return legalMovements.movements.any {
            isKingCapture(it)
        }
    }

    private fun Board.isKingCapture(movement: String): Boolean {
        val capturedPiece = position(movement.futureStringPosition()).piece
        logger.debug("Captured piece ${capturedPiece?.value}")
        return capturedPiece is King
    }

    private fun Board.filterAvailableDirections(availableDirections: List<Direction>, position: Position, index: Int) =
        availableDirections.filter {
            getFuturePositionOrNull(it, position, index)
                ?.isEmpty() ?: false
        }

    private suspend fun Board.getLegalPositionsInRange(
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
        }.let {
            handlePromotionInRange(it, position)
        }
        return legalMovementList.toLegalMovements()
    }

    private fun Position.handlePawnCapture(futurePosition: Position) =
        piece !is Pawn || futurePosition.column == column || (futurePosition.row - row).absoluteValue == 1

    private fun Position.buildMovementOrNull(
        futurePosition: Position,
        hasMovement: Boolean,
        hasCapture: Boolean
    ): String? {
        return if (handlePawnCapture(futurePosition) && piece!!.canMove(futurePosition, hasMovement, hasCapture)) {
            createMovement(
                originPosition = this,
                futurePosition = futurePosition,
                action = buildCaptureAction(futurePosition, piece),
            )
        } else null
    }

    private fun Board.getFuturePositionOrNull(direction: Direction, position: Position, range: Int): Position? {
        val futureRow = direction.getFutureRow(position.row, range)
        val futureColumn = direction.getFutureColumn(position.column, range)
        return positions.getOrNull(futureRow)?.getOrNull(futureColumn)
    }

    fun buildCaptureAction(position: Position, originPiece: Piece?): String {
        return if (position.hasEnemyPiece(originPiece)) {
            "C"
        } else {
            ""
        }
    }
}
