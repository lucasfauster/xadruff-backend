// package com.uff.br.xadruffbackend.model
//
//
// import kotlin.random.Random
//
// class BoardMock {
//    var state = mutableListOf("a")
//    var legalMoves = mutableListOf("b")
//    var position = listOf(mutableListOf('r'))
//
//    fun applyMove(movement: String){
//        state += movement
//    }
//
//    fun revertLastMove(){
//        state.removeLast()
//    }
//
//    fun getPieceAt(position: Int): Piece = com.uff.br.xadruffbackend.model.piece.Piece('N')
// }
//
// class Minimax {
//    private val pieceWeights = mapOf(null to 0,
//                                     'P' to 10,
//                                     'N' to 30,
//                                     'B' to 30,
//                                     'R' to 50,
//                                     'Q' to 90,
//                                     'K' to 900)
//
//    private val piecePositionWeights =mapOf(
//                                            null to listOf( mutableListOf(0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0),
//                                                            mutableListOf(0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0),
//                                                            mutableListOf(0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0),
//                                                            mutableListOf(0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0),
//                                                            mutableListOf(0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0),
//                                                            mutableListOf(0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0),
//                                                            mutableListOf(0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0),
//                                                            mutableListOf(0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0) ),
//                                            'P' to listOf( mutableListOf(0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0),
//                                                            mutableListOf(5.0,  5.0,  5.0,  5.0,  5.0,  5.0,  5.0,  5.0),
//                                                            mutableListOf(1.0,  1.0,  2.0,  3.0,  3.0,  2.0,  1.0,  1.0),
//                                                            mutableListOf(0.5,  0.5,  1.0,  2.5,  2.5,  1.0,  0.5,  0.5),
//                                                            mutableListOf(0.0,  0.0,  0.0,  2.0,  2.0,  0.0,  0.0,  0.0),
//                                                            mutableListOf(0.5, -0.5, -1.0,  0.0,  0.0, -1.0, -0.5,  0.5),
//                                                            mutableListOf(0.5,  1.0, 1.0,  -2.0, -2.0,  1.0,  1.0,  0.5),
//                                                            mutableListOf(0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0) ),
//                                            'N' to listOf(  mutableListOf(-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0),
//                                                mutableListOf(-4.0, -2.0,  0.0,  0.0,  0.0,  0.0, -2.0, -4.0),
//                                                mutableListOf(-3.0,  0.0,  1.0,  1.5,  1.5,  1.0,  0.0, -3.0),
//                                                mutableListOf(-3.0,  0.5,  1.5,  2.0,  2.0,  1.5,  0.5, -3.0),
//                                                mutableListOf(-3.0,  0.0,  1.5,  2.0,  2.0,  1.5,  0.0, -3.0),
//                                                mutableListOf(-3.0,  0.5,  1.0,  1.5,  1.5,  1.0,  0.5, -3.0),
//                                                mutableListOf(-4.0, -2.0,  0.0,  0.5,  0.5,  0.0, -2.0, -4.0),
//                                                mutableListOf(-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0) ),
//                                            'B' to listOf(   mutableListOf(-2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0),
//                                                mutableListOf(-1.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -1.0),
//                                                mutableListOf(-1.0,  0.0,  0.5,  1.0,  1.0,  0.5,  0.0, -1.0),
//                                                mutableListOf(-1.0,  0.5,  0.5,  1.0,  1.0,  0.5,  0.5, -1.0),
//                                                mutableListOf(-1.0,  0.0,  1.0,  1.0,  1.0,  1.0,  0.0, -1.0),
//                                                mutableListOf(-1.0,  1.0,  1.0,  1.0,  1.0,  1.0,  1.0, -1.0),
//                                                mutableListOf(-1.0,  0.5,  0.0,  0.0,  0.0,  0.0,  0.5, -1.0),
//                                                mutableListOf(-2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0) ),
//                                            'R' to listOf(  mutableListOf(0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0),
//                                                mutableListOf(0.5,  1.0,  1.0,  1.0,  1.0,  1.0,  1.0,  0.5),
//                                                mutableListOf(-0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5),
//                                                mutableListOf(-0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5),
//                                                mutableListOf(-0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5),
//                                                mutableListOf(-0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5),
//                                                mutableListOf(-0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5),
//                                                mutableListOf(0.0,   0.0, 0.0,  0.5,  0.5,  0.0,  0.0,  0.0) ),
//                                            'Q' to listOf(   mutableListOf(-2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0),
//                                                mutableListOf(-1.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -1.0),
//                                                mutableListOf(-1.0,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -1.0),
//                                                mutableListOf(-0.5,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -0.5),
//                                                mutableListOf(0.0,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -0.5),
//                                                mutableListOf(-1.0,  0.5,  0.5,  0.5,  0.5,  0.5,  0.0, -1.0),
//                                                mutableListOf(-1.0,  0.0,  0.5,  0.0,  0.0,  0.0,  0.0, -1.0),
//                                                mutableListOf(-2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0) ),
//                                            'K' to listOf(   mutableListOf(-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0),
//                                                mutableListOf(-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0),
//                                                mutableListOf(-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0),
//                                                mutableListOf(-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0),
//                                                mutableListOf(-2.0, -3.0, -3.0, -4.0, -4.0, -3.0, -3.0, -2.0),
//                                                mutableListOf(-1.0, -2.0, -2.0, -2.0, -2.0, -2.0, -2.0, -1.0),
//                                                mutableListOf(2.0,  2.0,  0.0,  0.0,  0.0,  0.0,  2.0,  2.0),
//                                                mutableListOf(2.0,  3.0,  1.0,  0.0,  0.0,  1.0,  3.0,  2.0) ))
//
//    fun getMovement(depth: Int, boardMock: BoardMock): String {
//        var bestMoveValue = mutableListOf(Double.MIN_VALUE, Double.MIN_VALUE)
//        var finalMove = mutableListOf("", "")
//
//        for (move in boardMock.legalMoves){
//            boardMock.applyMove(move)
//            var moveValue = minimax(depth - 1, boardMock, Double.MIN_VALUE, Double.MAX_VALUE, false)
//            boardMock.revertLastMove()
//
//            for(i in 0..1){
//                if(moveValue > bestMoveValue[i]){
//                    bestMoveValue[i] = moveValue
//                    finalMove[i] = move
//                    break
//                }
//            }
//        }
//        val moveId = Random.nextInt(0,1)
//        return finalMove[moveId]
//    }
//
//    private fun minimax(depth: Int, boardMock: BoardMock, alpha: Double, beta: Double, isMaximizing: Boolean): Double {
//        if(depth == 0) return -evaluate(boardMock)
//
//        if(isMaximizing){
//            var bestMoveValue = Double.MIN_VALUE
//
//            for (move in boardMock.legalMoves){
//                boardMock.applyMove(move)
//                bestMoveValue = bestMoveValue.coerceAtLeast(minimax(depth - 1, boardMock, alpha, beta, !isMaximizing))
//                boardMock.revertLastMove()
//
//                var alpha = alpha.coerceAtLeast(bestMoveValue)
//                if (beta <= alpha) return bestMoveValue
//            }
//            return bestMoveValue
//        } else {
//            var bestMoveValue = Double.MAX_VALUE
//
//            for(move in boardMock.legalMoves){
//                boardMock.applyMove(move)
//                bestMoveValue = bestMoveValue.coerceAtMost(minimax(depth - 1, boardMock, alpha, beta, !isMaximizing))
//                boardMock.revertLastMove()
//
//                var beta = beta.coerceAtMost(bestMoveValue)
//                if (beta <= alpha) return bestMoveValue
//            }
//            return bestMoveValue
//        }
//    }
//
//    private fun evaluate(boardMock: BoardMock): Double {
//        var weight = 0.0
//        val playerColor = boardMock.getPieceAt(0).getColor() // cor das peças do jogador humano (deve ser verificado de outra forma)
//
//        for(i in 0..7){
//            for(j in 0..7) {
//                val piece = boardMock.position[i][j].uppercaseChar()
//                val pieceColor = boardMock.position[i][j].isUpperCase()
//                val positionWeight =
//                    if (playerColor == pieceColor) piecePositionWeights.getValue(piece)[i][j] else piecePositionWeights.getValue(piece).reversed()[i][j]
//
//                val pieceWeightValue = pieceWeights.getValue(piece) + positionWeight
//                weight = if (playerColor == pieceColor) weight + pieceWeightValue else weight - pieceWeightValue
//            }
//        }
//        return weight
//    }
// }
