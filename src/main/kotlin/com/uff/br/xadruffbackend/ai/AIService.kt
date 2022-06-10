package com.uff.br.xadruffbackend.ai

import com.uff.br.xadruffbackend.ChessService
import com.uff.br.xadruffbackend.ai.model.Weights
import com.uff.br.xadruffbackend.extension.deepCopy
import com.uff.br.xadruffbackend.extension.position
import com.uff.br.xadruffbackend.extension.toBoardResponse
import com.uff.br.xadruffbackend.extension.toJsonString
import com.uff.br.xadruffbackend.model.Board
import org.slf4j.LoggerFactory
import kotlin.random.Random

class AIService(private val chessService: ChessService) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    companion object {
        const val DEPTH = 2
    }

    fun play(depth: Int, board: Board): String {
        val bestMoveValue = mutableListOf(Double.MIN_VALUE, Double.MIN_VALUE)
        val finalMove = mutableListOf("", "")
        val legalMovements = chessService.calculateLegalMovements(board)

        for (move in legalMovements.movements) {
            logger.debug("Applying move {} in depth {}", move, depth)
            val fakeBoard = board.deepCopy()
            chessService.applyMove(fakeBoard, move)
            val moveValue = max(depth - 1, fakeBoard, Double.MIN_VALUE, Double.MAX_VALUE)

            for (i in 0..1) {
                if (moveValue > bestMoveValue[i]) {
                    bestMoveValue[i] = moveValue
                    finalMove[i] = move
                    break
                }
            }
        }
        val moveId = Random.nextInt(0, 1)
        val selectedMovement = finalMove[moveId]
        logger.info("Selected move by AI: {}", selectedMovement)
        return selectedMovement
    }

    fun max(depth: Int, board: Board, maxMoveValueLimit: Double, minMoveValueLimit: Double): Double {
        if (depth == 0) {
            return -evaluate(board)
        }
        var bestMoveValue = -10000.0
        val legalMovements = chessService.calculateLegalMovements(board)

        for (move in legalMovements.movements) {
            logger.debug("Applying move {} in depth {} for maximize", move, depth)
            val fakeBoard = board.deepCopy()
            chessService.applyMove(fakeBoard, move)
            bestMoveValue =
                bestMoveValue.coerceAtLeast(min(depth - 1, fakeBoard, maxMoveValueLimit, minMoveValueLimit))

            logger.debug("Best value for move {} in depth {} is {}", move, depth, bestMoveValue)
            val maxMoveValue = maxMoveValueLimit.coerceAtLeast(bestMoveValue)
            if (minMoveValueLimit <= maxMoveValue) {
                logger.debug("Value high limit {} is reached for move {} in depth {}", maxMoveValue, move, depth)
                return bestMoveValue
            }
        }
        return bestMoveValue
    }

    fun min(depth: Int, board: Board, maxMoveValueLimit: Double, minMoveValueLimit: Double): Double {
        if (depth == 0) {
            return -evaluate(board)
        }
        var worseMoveValue = 10000.0
        val legalMovements = chessService.calculateLegalMovements(board)

        for (move in legalMovements.movements) {
            logger.debug("Applying move {} in depth {} for minimize", move, depth)
            val fakeBoard = board.deepCopy()
            chessService.applyMove(fakeBoard, move)
            worseMoveValue =
                worseMoveValue.coerceAtMost(max(depth - 1, fakeBoard, maxMoveValueLimit, minMoveValueLimit))

            logger.debug("Worse value for move {} in depth {} is {}", move, depth, worseMoveValue)
            val minMoveValue = minMoveValueLimit.coerceAtMost(worseMoveValue)
            if (minMoveValue <= maxMoveValueLimit) {
                logger.debug("Value low limit {} is reached for move {} in depth {}", minMoveValue, move, depth)
                return worseMoveValue
            }
        }
        return worseMoveValue
    }

    fun evaluate(board: Board): Double {
        var weight = 0.0

        @Suppress("MagicNumber")
        for (row in 0..7) {
            for (column in 0..7) {
                val piece = board.positions[row][column].piece
                val positionWeight =
                    when (piece?.color) {
                        board.turnColor ->
                            Weights.piecePositionWeights.getValue(piece.value.uppercaseChar())[row][column]
                        !board.turnColor ->
                            Weights.piecePositionWeights.getValue(piece.value.uppercaseChar()).reversed()[row][column]
                        else -> 0.0
                    }

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
}
