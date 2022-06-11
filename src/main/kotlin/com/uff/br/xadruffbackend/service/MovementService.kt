package com.uff.br.xadruffbackend.service

import com.uff.br.xadruffbackend.exception.InvalidMovementException
import com.uff.br.xadruffbackend.extension.BoardMovementsCalculatorExtensions.calculatePseudoLegalMoves
import com.uff.br.xadruffbackend.extension.BoardMovementsCalculatorExtensions.hasCheckForOpponent
import com.uff.br.xadruffbackend.extension.ChessSliceIndex
import com.uff.br.xadruffbackend.extension.changeTurn
import com.uff.br.xadruffbackend.extension.deepCopy
import com.uff.br.xadruffbackend.extension.position
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.LegalMovements
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.stream.Collectors

@Component
class MovementService {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun calculateLegalMovements(board: Board): LegalMovements {
        logger.debug("Calculating legal movements")
        val legalMovements = board.calculatePseudoLegalMoves()
        logger.debug("Calculated pseudo legal movements: {}", legalMovements)

        return LegalMovements(
            legalMovements.movements.parallelStream().filter {
                val fakeBoard = board.deepCopy()
                applyMove(fakeBoard, it)
                val hasCheck = fakeBoard.hasCheckForOpponent()
                !hasCheck
            }.collect(Collectors.toList())
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
        board.changeTurn()
    }
}
