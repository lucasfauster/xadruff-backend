package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.dto.Board
import com.uff.br.xadruffbackend.dto.Position
import com.uff.br.xadruffbackend.dto.enum.Color
import com.uff.br.xadruffbackend.dto.piece.Bishop
import com.uff.br.xadruffbackend.dto.piece.Knight
import com.uff.br.xadruffbackend.dto.piece.MovementRange
import com.uff.br.xadruffbackend.dto.piece.Pawn
import com.uff.br.xadruffbackend.dto.piece.Queen
import com.uff.br.xadruffbackend.dto.piece.Rook
import com.uff.br.xadruffbackend.extension.BoardMovementsCalculatorExtensions.buildCaptureAction
import com.uff.br.xadruffbackend.util.parallelMap
import org.slf4j.LoggerFactory

object BoardPromotionExtensions {

    private val logger = LoggerFactory.getLogger(this::class.java)

    const val PROMOTION_CHAR = 'P'

    private val possiblePromotionPieces = listOf(
        Queen.VALUE,
        Bishop.VALUE,
        Rook.VALUE,
        Knight.VALUE
    )

    suspend fun Board.handlePromotionInRange(
        legalMovementList: List<String>,
        position: Position,
    ): List<String> {
        return legalMovementList.parallelMap {
            if (position.piece!! is Pawn && isPawnLastMovement(it.futureStringPosition())) {
                logger.info("Adding promotion movements to pawn in position $position")
                createPromotionMovements(position, it.futureStringPosition())
            } else {
                listOf(it)
            }
        }.flatten()
    }

    fun Board.createPromotionMovements(position: Position, futureChessPosition: String): List<String> {
        val futurePosition = position(futureChessPosition)
        return possiblePromotionPieces.map {
            createMovement(
                originPosition = position,
                futurePosition = futurePosition,
                action = buildCaptureAction(futurePosition, position.piece) +
                    "$PROMOTION_CHAR${promotionPieceWithCase(it, position.piece!!.color)}"
            )
        }
    }

    private fun promotionPieceWithCase(piece: Char, color: Color): String {
        return when (color) {
            Color.WHITE -> piece.uppercase()
            Color.BLACK -> piece.lowercase()
        }
    }

    fun isPawnLastMovement(futureChessPosition: String): Boolean {
        return futureChessPosition.last() == 1.digitToChar() ||
            futureChessPosition.last() == (MovementRange.ALL_BOARD + 1).digitToChar()
    }
}
