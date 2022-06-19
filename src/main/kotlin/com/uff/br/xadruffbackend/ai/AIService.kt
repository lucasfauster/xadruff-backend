package com.uff.br.xadruffbackend.ai

import com.uff.br.xadruffbackend.ai.model.Weights
import com.uff.br.xadruffbackend.extension.changeTurn
import com.uff.br.xadruffbackend.extension.deepCopy
import com.uff.br.xadruffbackend.extension.toBoardResponse
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.enum.Level
import com.uff.br.xadruffbackend.service.MovementService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class AIService(@Autowired private val movementService: MovementService) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    companion object {
        const val FIRST_DEPTH = 0
        const val INITIAL_MAX_BEST_NUMBER = -10000.0
        const val INITIAL_MIN_BEST_NUMBER = 10000.0
    }

    fun play(level: Level, board: Board): String {
        val bestMoveValue = mutableListOf(-Double.MAX_VALUE, -Double.MAX_VALUE)
        val finalMove = mutableListOf("", "")
        val legalMovements = movementService.calculateLegalMovements(board)
        val depth = level.ordinal + 1

        for (move in legalMovements.movements) {
            logger.debug("Applying move {} in depth {}", move, depth)
            val fakeBoard = board.deepCopy()
            movementService.applyMove(fakeBoard, move)
            fakeBoard.changeTurn()
            val moveValue = max(depth - 1, fakeBoard, -Double.MAX_VALUE, Double.MAX_VALUE)

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
        if (depth == FIRST_DEPTH) {
            return -evaluate(board)
        }
        var bestMoveValue = INITIAL_MAX_BEST_NUMBER
        val legalMovements = movementService.calculateLegalMovements(board)

        for (move in legalMovements.movements) {
            logger.debug("Applying move {} in depth {} for maximize", move, depth)
            val fakeBoard = board.deepCopy()
            movementService.applyMove(fakeBoard, move)
            fakeBoard.changeTurn()
            bestMoveValue =
                bestMoveValue.coerceAtLeast(min(depth - 1, fakeBoard, maxMoveValueLimit, minMoveValueLimit))

            logger.debug("Best value for move {} in depth {} is {}", move, depth, bestMoveValue)
            val maxMoveValue = maxMoveValueLimit.coerceAtLeast(bestMoveValue)
            if (minMoveValueLimit <= maxMoveValue) {
                logger.debug("Value high limit {} is reached for move {} in depth {}", maxMoveValue, move, depth)
                break
            }
        }
        return bestMoveValue
    }

    fun min(depth: Int, board: Board, maxMoveValueLimit: Double, minMoveValueLimit: Double): Double {
        if (depth == FIRST_DEPTH) {
            return -evaluate(board)
        }
        var worseMoveValue = INITIAL_MIN_BEST_NUMBER
        val legalMovements = movementService.calculateLegalMovements(board)

        for (move in legalMovements.movements) {
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
