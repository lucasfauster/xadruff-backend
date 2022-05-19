package com.uff.br.xadruffbackend.ai

import com.uff.br.xadruffbackend.ai.model.BoardMock
import com.uff.br.xadruffbackend.ai.model.Weights
import org.slf4j.LoggerFactory
import kotlin.random.Random

class AIService {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun getMovement(depth: Int, boardMock: BoardMock): String {
        val bestMoveValue = mutableListOf(Double.MIN_VALUE, Double.MIN_VALUE)
        val finalMove = mutableListOf("", "")

        for (move in boardMock.legalMoves) {
            logger.debug("Applying move {} in depth {}", move, depth)
            boardMock.applyMove(move)
            val moveValue = minimax(depth - 1, boardMock, Double.MIN_VALUE, Double.MAX_VALUE, false)
            logger.debug("Reverting move {} in depth {} with value {}", move, depth, moveValue)
            boardMock.revertLastMove()

            for (i in 0..1) {
                if (moveValue > bestMoveValue[i]) {
                    bestMoveValue[i] = moveValue
                    finalMove[i] = move
                    break
                }
            }
        }
        val moveId = Random.nextInt(0, 1)
        return finalMove[moveId]
    }

    private fun minimax(depth: Int, boardMock: BoardMock, maxMoveValueLimit: Double, minMoveValueLimit: Double, isMaximizing: Boolean): Double {
        if (depth == 0) {
            return -evaluate(boardMock)
        }

        if (isMaximizing) {
            var bestMoveValue = Double.MIN_VALUE
            var maxMoveValueLimit = maxMoveValueLimit

            for (move in boardMock.legalMoves) {
                logger.debug("Applying move {} in depth {} for maximize", move, depth)
                boardMock.applyMove(move)
                bestMoveValue = bestMoveValue.coerceAtLeast(minimax(depth - 1, boardMock, maxMoveValueLimit, minMoveValueLimit, !isMaximizing))
                logger.debug("Reverting move {} in depth {} for maximize with value {}", move, depth, bestMoveValue)
                boardMock.revertLastMove()

                maxMoveValueLimit = maxMoveValueLimit.coerceAtLeast(bestMoveValue)
                if (minMoveValueLimit <= maxMoveValueLimit) {
                    return bestMoveValue
                }
            }
            return bestMoveValue
        } else {
            var bestMoveValue = Double.MAX_VALUE
            var minMoveValueLimit = minMoveValueLimit

            for (move in boardMock.legalMoves) {
                logger.debug("Applying move {} in depth {} for minimize", move, depth)
                boardMock.applyMove(move)
                bestMoveValue = bestMoveValue.coerceAtMost(minimax(depth - 1, boardMock, maxMoveValueLimit, minMoveValueLimit, !isMaximizing))
                logger.debug("Reverting move {} in depth {} for minimize with value {}", move, depth, bestMoveValue)
                boardMock.revertLastMove()

                minMoveValueLimit = minMoveValueLimit.coerceAtMost(bestMoveValue)
                if (minMoveValueLimit <= maxMoveValueLimit) {
                    return bestMoveValue
                }
            }
            return bestMoveValue
        }
    }

    private fun evaluate(boardMock: BoardMock): Double {
        var weight = 0.0
        val playerColor = true

        for (i in 0..7) {
            for (j in 0..7) {
                val piece = boardMock.state[i][j].uppercase()
                val pieceColor = boardMock.state[i][j].getOrNull(0)?.isUpperCase()
                val positionWeight =
                    if (playerColor == pieceColor) {
                        Weights.piecePositionWeights.getValue(piece)[i][j]
                    } else {
                        Weights.piecePositionWeights.getValue(piece).reversed()[i][j]
                    }

                val pieceWeightValue = Weights.pieceWeights.getValue(piece) + positionWeight
                weight =
                    if (playerColor == pieceColor) {
                        weight + pieceWeightValue
                    } else {
                        weight - pieceWeightValue
                    }
            }
        }
        return weight
    }
}