package com.uff.br.xadruffbackend.model

import kotlin.random.Random
import org.slf4j.LoggerFactory

class Piece(value: Char) {
    private val value = value

    fun getColor() = this.value.isUpperCase() // upperCase = Branco = True

    fun getValue() = this.value
}

class BoardMock {
    var state = listOf(
        mutableListOf("r", "n", "b", "q", "k", "b", "n", "r"),
        mutableListOf("p", "p", "p", "p", "p", "p", "p", "p"),
        mutableListOf("", "", "", "", "", "", "", ""),
        mutableListOf("", "", "", "", "", "", "", ""),
        mutableListOf("", "", "", "", "", "", "", ""),
        mutableListOf("", "", "", "", "", "", "", ""),
        mutableListOf("P", "P", "P", "P", "P", "P", "P", "P"),
        mutableListOf("R", "N", "B", "Q", "K", "B", "N", "R"))
    var legalMoves = mutableListOf("a2a3", "a2a4", "b2b3", "b2b4", "c2c3", "c2c4", "d2d3", "d2d4",
        "e2e3", "e2e4", "f2f3", "f2f4", "g2g3", "g2g4", "h2h3", "h2h4",
        "b1a3", "b1c3", "g1f3", "g1h3", "a7a6", "a7a5", "b7b6", "b7b5",
        "c7c6", "c7c5", "d7d6", "d7d5", "e7e6", "e7e5", "f7f6", "f7f5",
        "g7g6", "g7g5", "h7h6", "h7h5", "c8b6", "c8d6", "g8f6", "g8h6")

    fun applyMove(movement: String){
         state = listOf(
            mutableListOf("r", "n", "b", "q", "k", "b", "n", "r"),
            mutableListOf("p", "p", "p", "p", "p", "p", "p", "p"),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("", "", "", "", "P", "", "", ""),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("P", "P", "P", "", "P", "P", "P", "P"),
            mutableListOf("R", "N", "B", "Q", "K", "B", "N", "R"))
    }

    fun revertLastMove(){
        state = listOf(
            mutableListOf("r", "n", "b", "q", "k", "b", "n", "r"),
            mutableListOf("p", "p", "p", "p", "p", "p", "p", "p"),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("P", "P", "P", "P", "P", "P", "P", "P"),
            mutableListOf("R", "N", "B", "Q", "K", "B", "N", "R"))
    }
}

class Minimax {
    private val logger = LoggerFactory.getLogger(this::class.java)

    private val pieceWeights = mapOf("" to 0,
                                     "P" to 10,
                                     "N" to 30,
                                     "B" to 30,
                                     "R" to 50,
                                     "Q" to 90,
                                     "K" to 900)

    private val piecePositionWeights =mapOf(
                                            "" to listOf( mutableListOf(0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0),
                                                            mutableListOf(0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0),
                                                            mutableListOf(0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0),
                                                            mutableListOf(0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0),
                                                            mutableListOf(0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0),
                                                            mutableListOf(0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0),
                                                            mutableListOf(0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0),
                                                            mutableListOf(0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0) ),
                                            "P" to listOf( mutableListOf(0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0),
                                                            mutableListOf(5.0,  5.0,  5.0,  5.0,  5.0,  5.0,  5.0,  5.0),
                                                            mutableListOf(1.0,  1.0,  2.0,  3.0,  3.0,  2.0,  1.0,  1.0),
                                                            mutableListOf(0.5,  0.5,  1.0,  2.5,  2.5,  1.0,  0.5,  0.5),
                                                            mutableListOf(0.0,  0.0,  0.0,  2.0,  2.0,  0.0,  0.0,  0.0),
                                                            mutableListOf(0.5, -0.5, -1.0,  0.0,  0.0, -1.0, -0.5,  0.5),
                                                            mutableListOf(0.5,  1.0, 1.0,  -2.0, -2.0,  1.0,  1.0,  0.5),
                                                            mutableListOf(0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0) ),
                                            "N" to listOf(  mutableListOf(-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0),
                                                mutableListOf(-4.0, -2.0,  0.0,  0.0,  0.0,  0.0, -2.0, -4.0),
                                                mutableListOf(-3.0,  0.0,  1.0,  1.5,  1.5,  1.0,  0.0, -3.0),
                                                mutableListOf(-3.0,  0.5,  1.5,  2.0,  2.0,  1.5,  0.5, -3.0),
                                                mutableListOf(-3.0,  0.0,  1.5,  2.0,  2.0,  1.5,  0.0, -3.0),
                                                mutableListOf(-3.0,  0.5,  1.0,  1.5,  1.5,  1.0,  0.5, -3.0),
                                                mutableListOf(-4.0, -2.0,  0.0,  0.5,  0.5,  0.0, -2.0, -4.0),
                                                mutableListOf(-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0) ),
                                            "B" to listOf(   mutableListOf(-2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0),
                                                mutableListOf(-1.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -1.0),
                                                mutableListOf(-1.0,  0.0,  0.5,  1.0,  1.0,  0.5,  0.0, -1.0),
                                                mutableListOf(-1.0,  0.5,  0.5,  1.0,  1.0,  0.5,  0.5, -1.0),
                                                mutableListOf(-1.0,  0.0,  1.0,  1.0,  1.0,  1.0,  0.0, -1.0),
                                                mutableListOf(-1.0,  1.0,  1.0,  1.0,  1.0,  1.0,  1.0, -1.0),
                                                mutableListOf(-1.0,  0.5,  0.0,  0.0,  0.0,  0.0,  0.5, -1.0),
                                                mutableListOf(-2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0) ),
                                            "R" to listOf(  mutableListOf(0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0),
                                                mutableListOf(0.5,  1.0,  1.0,  1.0,  1.0,  1.0,  1.0,  0.5),
                                                mutableListOf(-0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5),
                                                mutableListOf(-0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5),
                                                mutableListOf(-0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5),
                                                mutableListOf(-0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5),
                                                mutableListOf(-0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5),
                                                mutableListOf(0.0,   0.0, 0.0,  0.5,  0.5,  0.0,  0.0,  0.0) ),
                                            "Q" to listOf(   mutableListOf(-2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0),
                                                mutableListOf(-1.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -1.0),
                                                mutableListOf(-1.0,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -1.0),
                                                mutableListOf(-0.5,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -0.5),
                                                mutableListOf(0.0,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -0.5),
                                                mutableListOf(-1.0,  0.5,  0.5,  0.5,  0.5,  0.5,  0.0, -1.0),
                                                mutableListOf(-1.0,  0.0,  0.5,  0.0,  0.0,  0.0,  0.0, -1.0),
                                                mutableListOf(-2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0) ),
                                            "K" to listOf(   mutableListOf(-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0),
                                                mutableListOf(-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0),
                                                mutableListOf(-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0),
                                                mutableListOf(-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0),
                                                mutableListOf(-2.0, -3.0, -3.0, -4.0, -4.0, -3.0, -3.0, -2.0),
                                                mutableListOf(-1.0, -2.0, -2.0, -2.0, -2.0, -2.0, -2.0, -1.0),
                                                mutableListOf(2.0,  2.0,  0.0,  0.0,  0.0,  0.0,  2.0,  2.0),
                                                mutableListOf(2.0,  3.0,  1.0,  0.0,  0.0,  1.0,  3.0,  2.0) ))

    fun getMovement(depth: Int, boardMock: BoardMock): String {
        val bestMoveValue = mutableListOf(Double.MIN_VALUE, Double.MIN_VALUE)
        val finalMove = mutableListOf("", "")

        for (move in boardMock.legalMoves){
            logger.debug("Applying move {} in depth {}", move, depth)
            boardMock.applyMove(move)
            val moveValue = minimax(depth - 1, boardMock, Double.MIN_VALUE, Double.MAX_VALUE, false)
            logger.debug("Reverting move {} in depth {} with value {}", move, depth, moveValue)
            boardMock.revertLastMove()

            for(i in 0..1){
                if(moveValue > bestMoveValue[i]){
                    bestMoveValue[i] = moveValue
                    finalMove[i] = move
                    break
                }
            }
        }
        val moveId = Random.nextInt(0,1)
        return finalMove[moveId]
    }

    private fun minimax(depth: Int, boardMock: BoardMock, alpha: Double, beta: Double, isMaximizing: Boolean): Double {
        if(depth == 0) return -evaluate(boardMock)

        if(isMaximizing){
            var bestMoveValue = Double.MIN_VALUE

            for (move in boardMock.legalMoves){
                logger.debug("Applying move {} in depth {} for maximize", move, depth)
                boardMock.applyMove(move)
                bestMoveValue = bestMoveValue.coerceAtLeast(minimax(depth - 1, boardMock, alpha, beta, !isMaximizing))
                logger.debug("Reverting move {} in depth {} for maximize with value {}", move, depth, bestMoveValue)
                boardMock.revertLastMove()

                val alpha = alpha.coerceAtLeast(bestMoveValue)
                if (beta <= alpha) return bestMoveValue
            }
            return bestMoveValue
        } else {
            var bestMoveValue = Double.MAX_VALUE

            for(move in boardMock.legalMoves){
                logger.debug("Applying move {} in depth {} for minimize", move, depth)
                boardMock.applyMove(move)
                bestMoveValue = bestMoveValue.coerceAtMost(minimax(depth - 1, boardMock, alpha, beta, !isMaximizing))
                logger.debug("Reverting move {} in depth {} for minimize with value {}", move, depth, bestMoveValue)
                boardMock.revertLastMove()

                val beta = beta.coerceAtMost(bestMoveValue)
                if (beta <= alpha) return bestMoveValue
            }
            return bestMoveValue
        }
    }

    private fun evaluate(boardMock: BoardMock): Double {
        var weight = 0.0
        val playerColor = true

        for(i in 0..7){
            for(j in 0..7) {
                val piece = boardMock.state[i][j].uppercase()
                val pieceColor = boardMock.state[i][j].getOrNull(0)?.isUpperCase()
                val positionWeight =
                    if (playerColor == pieceColor) piecePositionWeights.getValue(piece)[i][j] else piecePositionWeights.getValue(piece).reversed()[i][j]

                val pieceWeightValue = pieceWeights.getValue(piece) + positionWeight
                weight = if (playerColor == pieceColor) weight + pieceWeightValue else weight - pieceWeightValue
            }
        }
        return weight
    }
}