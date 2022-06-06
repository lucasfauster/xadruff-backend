package com.uff.br.xadruffbackend.ai

import com.uff.br.xadruffbackend.ChessService
import com.uff.br.xadruffbackend.ai.model.Weights
import com.uff.br.xadruffbackend.extension.deepCopy
import com.uff.br.xadruffbackend.model.Board
import org.slf4j.LoggerFactory
import kotlin.random.Random

class AIService(private val chessService: ChessService) {
    private val logger = LoggerFactory.getLogger(this::class.java)

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

    private fun max(depth: Int, board: Board, maxMoveValueLimit: Double, minMoveValueLimit: Double): Double {
        if (depth == 0) {
            return -evaluate(board)
        }
        var bestMoveValue = Double.MIN_VALUE
        val legalMovements = chessService.calculateLegalMovements(board)

        for (move in legalMovements.movements) {
            logger.debug("Applying move {} in depth {} for maximize", move, depth)
            val fakeBoard = board.deepCopy()
            chessService.applyMove(fakeBoard, move)
            bestMoveValue = bestMoveValue.coerceAtLeast(min(depth - 1, fakeBoard, maxMoveValueLimit, minMoveValueLimit))

            val maxMoveValue = maxMoveValueLimit.coerceAtLeast(bestMoveValue)
            if (minMoveValueLimit <= maxMoveValue) {
                return bestMoveValue
            }
        }
        return bestMoveValue
    }

    private fun min(depth: Int, board: Board, maxMoveValueLimit: Double, minMoveValueLimit: Double): Double {
        if (depth == 0) {
            return -evaluate(board)
        }
        var worseMoveValue = Double.MAX_VALUE
        val legalMovements = chessService.calculateLegalMovements(board)

        for (move in legalMovements.movements) {
            logger.debug("Applying move {} in depth {} for minimize", move, depth)
            val fakeBoard = board.deepCopy()
            chessService.applyMove(fakeBoard, move)
            worseMoveValue = worseMoveValue.coerceAtMost(max(depth - 1, fakeBoard, maxMoveValueLimit, minMoveValueLimit))

            val minMoveValue = minMoveValueLimit.coerceAtMost(worseMoveValue)
            if (minMoveValue <= maxMoveValueLimit) {
                return worseMoveValue
            }
        }
        return worseMoveValue
    }

    private fun evaluate(board: Board): Double {
        var weight = 0.0

        @Suppress("MagicNumber")
        for (i in 0..7) {
            for (j in 0..7) {
                val piece = board.positions[i][j].piece
                val positionWeight =
                    when (piece?.color) {
                        board.turnColor -> Weights.piecePositionWeights.getValue(piece.value.uppercaseChar())[i][j]
                        !board.turnColor -> Weights.piecePositionWeights.getValue(piece.value.uppercaseChar()).reversed()[i][j]
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
        return weight
    }
}
