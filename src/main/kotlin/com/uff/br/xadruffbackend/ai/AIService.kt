package com.uff.br.xadruffbackend.ai

import com.uff.br.xadruffbackend.ai.model.Weights
import com.uff.br.xadruffbackend.extension.changeTurn
import com.uff.br.xadruffbackend.extension.deepCopy
import com.uff.br.xadruffbackend.extension.futureStringPosition
import com.uff.br.xadruffbackend.extension.isCaptureMove
import com.uff.br.xadruffbackend.extension.isEnpassantMove
import com.uff.br.xadruffbackend.extension.isPromotionMove
import com.uff.br.xadruffbackend.extension.originalStringPosition
import com.uff.br.xadruffbackend.extension.position
import com.uff.br.xadruffbackend.extension.toBoardResponse
import com.uff.br.xadruffbackend.extension.toPositionColumn
import com.uff.br.xadruffbackend.extension.toPositionRow
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.enum.Level
import com.uff.br.xadruffbackend.model.piece.Piece
import com.uff.br.xadruffbackend.service.MovementService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AIService(@Autowired private val movementService: MovementService) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    companion object {
        const val FIRST_DEPTH = 0
        const val INITIAL_MAX_BEST_NUMBER = -10000
        const val INITIAL_MIN_BEST_NUMBER = 10000
    }

    fun play(level: Level, board: Board): String {
        var bestMoveValue = -Int.MAX_VALUE
        var finalMove = ""
        val legalMovements = movementService.calculateLegalMovements(board).movements
        val depth = level.ordinal + 1

        legalMovements.sortWith(compareByDescending { evaluateMove(board, it) })
        logger.debug("\nLegalMovements ordered: $legalMovements")
        for (move in legalMovements) {
            logger.debug("Applying move {} in depth {}", move, depth)
            val fakeBoard = board.deepCopy()
            movementService.applyMove(fakeBoard, move)
            fakeBoard.changeTurn()

            val moveValue = min(depth - 1, fakeBoard, -Int.MAX_VALUE, Int.MAX_VALUE)

            if (moveValue > bestMoveValue) {
                bestMoveValue = moveValue
                finalMove = move
            }
        }

        logger.info("Selected move by AI: {}", finalMove)
        return finalMove
    }

    fun max(depth: Int, board: Board, maxMoveValueLimit: Int, minMoveValueLimit: Int): Int {
        if (depth == FIRST_DEPTH) {
            return evaluate(board)
        }
        var bestMoveValue = INITIAL_MAX_BEST_NUMBER
        val legalMovements = movementService.calculateLegalMovements(board).movements

        legalMovements.sortWith(compareByDescending { evaluateMove(board, it) })
        for (move in legalMovements) {
            logger.debug("Applying move {} in depth {} for maximize", move, depth)
            val fakeBoard = board.deepCopy()
            movementService.applyMove(fakeBoard, move)
            fakeBoard.changeTurn()

            bestMoveValue =
                min(depth - 1, fakeBoard, maxMoveValueLimit, minMoveValueLimit)

            logger.debug("Best value for move {} in depth {} is {}", move, depth, bestMoveValue)
            val maxMoveValue = maxMoveValueLimit.coerceAtLeast(bestMoveValue)
            if (minMoveValueLimit <= maxMoveValue) {
                logger.debug("Value high limit {} is reached for move {} in depth {}", maxMoveValue, move, depth)
                break
            }
        }
        return bestMoveValue
    }

    fun min(depth: Int, board: Board, maxMoveValueLimit: Int, minMoveValueLimit: Int): Int {
        if (depth == FIRST_DEPTH) {
            return evaluate(board)
        }
        var worseMoveValue = INITIAL_MIN_BEST_NUMBER
        val legalMovements = movementService.calculateLegalMovements(board).movements

        legalMovements.sortWith(compareByDescending { evaluateMove(board, it) })
        for (move in legalMovements) {
            logger.debug("Applying move {} in depth {} for minimize", move, depth)
            val fakeBoard = board.deepCopy()
            movementService.applyMove(fakeBoard, move)
            fakeBoard.changeTurn()
            worseMoveValue =
                worseMoveValue.coerceAtMost(max(depth - 1, fakeBoard, maxMoveValueLimit, minMoveValueLimit))

            logger.debug("Worse value for move {} in depth {} is {}", move, depth, worseMoveValue)
            val minMoveValue = minMoveValueLimit.coerceAtMost(worseMoveValue)
            if (minMoveValue <= maxMoveValueLimit) {
                logger.debug("Value low limit {} is reached for move {} in depth {}", minMoveValue, move, depth)
                break
            }
        }
        return worseMoveValue
    }

    fun evaluate(board: Board): Int {
        var weight = 0

        @Suppress("MagicNumber")
        for (row in 0..7) {
            for (column in 0..7) {
                val piece = board.positions[row][column].piece
                val positionWeight = getPiecePosWeight(board, piece, row, column)
                val pieceWeightValue = Weights.pieceWeights.getValue(piece?.value?.uppercaseChar()) + positionWeight

                weight =
                    if (board.turnColor == piece?.color) {
                        weight + pieceWeightValue
                    } else {
                        weight - pieceWeightValue
                    }
            }
        }
        logger.debug("Evaluation is {}, for board {}", weight, board.toBoardResponse().positions)
        return weight
    }

    fun evaluateMove(board: Board, move: String): Int {
        val piece = board.position(move.originalStringPosition()).piece
        if (move.isPromotionMove()) {
            return Int.MAX_VALUE
        }

        val fromValue = getPiecePosWeight(board, piece, move.originalStringPosition())
        val toValue = getPiecePosWeight(board, piece, move.futureStringPosition())
        val positionChange = toValue - fromValue

        var captureValue = 0
        if (move.isCaptureMove()) {
            captureValue = evaluateCapture(board, move)
        }

        return captureValue + positionChange
    }

    private fun getPiecePosWeight(board: Board, piece: Piece?, position: String): Int {
        val row = position.last().toPositionRow()
        val column = position.first().toPositionColumn()
        return getPiecePosWeight(board, piece, row, column)
    }

    private fun getPiecePosWeight(board: Board, piece: Piece?, row: Int, column: Int): Int {
        return when (piece?.color) {
            board.turnColor ->
                Weights.piecePositionWeights.getValue(piece.value.uppercaseChar())[row][column]
            !board.turnColor ->
                Weights.piecePositionWeights.getValue(piece.value.uppercaseChar()).reversed()[row][column]
            else -> 0
        }
    }

    private fun evaluateCapture(board: Board, move: String): Int {
        if (move.isEnpassantMove(board)) {
            return Weights.pieceWeights.getValue('P')
        }
        val pieceInOriginPositionValue = board.position(move.originalStringPosition()).piece?.value?.uppercaseChar()
        val pieceInFuturePositionValue = board.position(move.futureStringPosition()).piece?.value?.uppercaseChar()

        return Weights.pieceWeights.getValue(pieceInFuturePositionValue) -
            Weights.pieceWeights.getValue(pieceInOriginPositionValue)
    }
}
