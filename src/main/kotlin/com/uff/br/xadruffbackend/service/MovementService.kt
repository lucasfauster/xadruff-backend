package com.uff.br.xadruffbackend.service

import com.uff.br.xadruffbackend.dto.Board
import com.uff.br.xadruffbackend.dto.LegalMovements
import com.uff.br.xadruffbackend.dto.enum.Color
import com.uff.br.xadruffbackend.dto.piece.Bishop
import com.uff.br.xadruffbackend.dto.piece.King
import com.uff.br.xadruffbackend.dto.piece.Knight
import com.uff.br.xadruffbackend.dto.piece.Pawn
import com.uff.br.xadruffbackend.dto.piece.Piece
import com.uff.br.xadruffbackend.dto.piece.Queen
import com.uff.br.xadruffbackend.dto.piece.Rook
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
import com.uff.br.xadruffbackend.model.GameEntity
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

    fun handleCastleMovement(board: Board, move: String) {
        val piece = board.position(move.originalStringPosition()).piece
        logger.debug("Piece in handle castle movement is = $piece")
        if (piece is King && isCastleMovement(move)) {
            val futurePosition = move.futureStringPosition()
            val row = futurePosition.last()
            val column = futurePosition.first()

            logger.debug("Calculating castle movement for row = $row, column = $column")
            var rookMovement = "h$row" + "f$row"
            if (column == 'c') {
                rookMovement = "a$row" + "d$row"
            }

            applyMove(board, rookMovement)
        }
    }

    private fun isCastleMovement(move: String): Boolean {
        return move.any {
            it == 'O'
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
        handleCastleMovement(board, move)
        val piece = getPromotionPiece(board.position(move.originalStringPosition()).piece!!, move)
        updateCastlePiecesState(piece)
        board.position(move.futureStringPosition()).piece = piece
        board.position(move.originalStringPosition()).piece = null
    }

    private fun updateCastlePiecesState(piece: Piece) {
        when (piece) {
            is Rook -> piece.hasMoved = true
            is King -> piece.hasMoved = true
        }
    }

    fun getPromotionPiece(piece: Piece, move: String): Piece {
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
