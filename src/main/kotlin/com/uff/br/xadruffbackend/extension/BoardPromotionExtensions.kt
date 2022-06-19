package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.extension.BoardMovementsCalculatorExtensions.buildCaptureAction
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.Position
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.piece.Bishop
import com.uff.br.xadruffbackend.model.piece.Knight
import com.uff.br.xadruffbackend.model.piece.MovementRange
import com.uff.br.xadruffbackend.model.piece.Pawn
import com.uff.br.xadruffbackend.model.piece.Queen
import com.uff.br.xadruffbackend.model.piece.Rook
import com.uff.br.xadruffbackend.util.parallelMap

object BoardPromotionExtensions {

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
