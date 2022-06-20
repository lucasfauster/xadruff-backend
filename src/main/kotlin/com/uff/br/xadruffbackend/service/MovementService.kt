package com.uff.br.xadruffbackend.service

import com.uff.br.xadruffbackend.exception.InvalidMovementException
import com.uff.br.xadruffbackend.extension.BoardMovementsCalculatorExtensions.calculatePseudoLegalMoves
import com.uff.br.xadruffbackend.extension.BoardMovementsCalculatorExtensions.isKingInCheck
import com.uff.br.xadruffbackend.extension.BoardPromotionExtensions.PROMOTION_CHAR
import com.uff.br.xadruffbackend.extension.actions
import com.uff.br.xadruffbackend.extension.deepCopy
import com.uff.br.xadruffbackend.extension.futureStringPosition
import com.uff.br.xadruffbackend.extension.originalStringPosition
import com.uff.br.xadruffbackend.extension.position
import com.uff.br.xadruffbackend.extension.promotionPiece
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.GameEntity
import com.uff.br.xadruffbackend.model.LegalMovements
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.piece.Bishop
import com.uff.br.xadruffbackend.model.piece.Knight
import com.uff.br.xadruffbackend.model.piece.Pawn
import com.uff.br.xadruffbackend.model.piece.Piece
import com.uff.br.xadruffbackend.model.piece.Queen
import com.uff.br.xadruffbackend.model.piece.Rook
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.stream.Collectors

@Component
class MovementService {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun handleDrawMoveRule(game: GameEntity, move: String) {
        val piece = game.getBoard().position(move.originalStringPosition()).piece
        if (piece !is Pawn && move.last() != 'C') {
            logger.info(
                "Game = ${game.boardId}, " +
                    "movement wasn't a capture or a pawn move, adding one in draw move rule."
            )
            addOneToMoveRule(game)
        } else {
            game.whiteDrawMoves = 0
            game.blackDrawMoves = 0
        }
    }

    private fun addOneToMoveRule(game: GameEntity) {
        when (game.getBoard().turnColor) {
            Color.BLACK -> game.blackDrawMoves += 1
            Color.WHITE -> game.whiteDrawMoves += 1
        }
        logger.info(
            "Game ${game.boardId}, white draw moves = ${game.whiteDrawMoves}, " +
                "black draw moves = ${game.blackDrawMoves}"
        )
    }

    fun calculateLegalMovements(board: Board): LegalMovements {
        logger.debug("Calculating legal movements")
        val legalMovements = board.calculatePseudoLegalMoves()
        logger.debug("Calculated pseudo legal movements: $legalMovements")

        return LegalMovements(
            legalMovements.movements.parallelStream().filter {
                val fakeBoard = board.deepCopy()
                applyMove(fakeBoard, it)
                val hasCheck = fakeBoard.isKingInCheck()
                !hasCheck
            }.collect(Collectors.toList()).also {
                logger.debug("Calculated legal movements: $it")
            }
        )
    }

    fun verifyIsAllowedMove(legalMovements: LegalMovements, move: String) {
        if (legalMovements.movements.none { it == move }) {
            throw InvalidMovementException("The move '$move' is an invalid movement.")
        }
    }

    fun applyMove(board: Board, move: String) {
        val piece = getPiece(board.position(move.originalStringPosition()).piece!!, move)
        board.position(move.futureStringPosition()).piece = piece
        board.position(move.originalStringPosition()).piece = null
    }

    fun getPiece(piece: Piece, move: String): Piece {
        val pieceChar = if (move.actions().contains(PROMOTION_CHAR)) {
            move.promotionPiece()
        } else {
            piece.value
        }

        return when (pieceChar.uppercaseChar()) {
            Queen.VALUE.uppercaseChar() -> Queen(piece.color)
            Rook.VALUE.uppercaseChar() -> Rook(piece.color)
            Knight.VALUE.uppercaseChar() -> Knight(piece.color)
            Bishop.VALUE.uppercaseChar() -> Bishop(piece.color)
            else -> piece
        }
    }
}
