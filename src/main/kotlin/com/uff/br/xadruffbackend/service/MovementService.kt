package com.uff.br.xadruffbackend.service

import com.uff.br.xadruffbackend.exception.InvalidMovementException
import com.uff.br.xadruffbackend.extension.BoardMovementsCalculatorExtensions.calculatePseudoLegalMoves
import com.uff.br.xadruffbackend.extension.BoardMovementsCalculatorExtensions.isKingInCheck
import com.uff.br.xadruffbackend.extension.ChessSliceIndex
import com.uff.br.xadruffbackend.extension.deepCopy
import com.uff.br.xadruffbackend.extension.position
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.GameEntity
import com.uff.br.xadruffbackend.model.LegalMovements
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.piece.Pawn
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.stream.Collectors

@Component
class MovementService {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun handleDrawMoveRule(game: GameEntity, move: String) {
        val piece = game.getBoard().position(move.slice(ChessSliceIndex.FIRST_POSITION)).piece
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
        val piece = board.position(move.slice(ChessSliceIndex.FIRST_POSITION)).piece
        board.position(move.slice(ChessSliceIndex.SECOND_POSITION)).piece = piece
        board.position(move.slice(ChessSliceIndex.FIRST_POSITION)).piece = null
    }
}
