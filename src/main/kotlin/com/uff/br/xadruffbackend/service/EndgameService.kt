package com.uff.br.xadruffbackend.service

import com.uff.br.xadruffbackend.extension.BoardMovementsCalculatorExtensions.isKingInCheck
import com.uff.br.xadruffbackend.model.GameEntity
import com.uff.br.xadruffbackend.model.enum.EndgameMessage
import com.uff.br.xadruffbackend.model.response.EndgameResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class EndgameService {
    private val logger = LoggerFactory.getLogger(this::class.java)

    companion object {
        const val DRAW = "DRAW"
        const val MAX_MOVEMENTS = 50
    }

    fun checkIfGameEnded(game: GameEntity) {
        if (game.getLegalMovements().movements.isEmpty()) {
            handleEndByMate(game)
        } else {
            checkIfHasEndedByMovements(game)
        }
    }

    fun checkIfHasEndedByMovements(game: GameEntity) {
        if (game.blackDrawMoves == MAX_MOVEMENTS && game.whiteDrawMoves == MAX_MOVEMENTS) {
            logger.info("Game ${game.boardId} ended in draw by $MAX_MOVEMENTS movements rule.")
            game.winner = DRAW
            game.endgameMessage = EndgameMessage.DRAW_RULE.message
        }
    }

    fun handleEndByMate(game: GameEntity) {
        val board = game.getBoard()
        if (board.isKingInCheck()) {
            val winner = board.turnColor.not().name
            logger.info("Game ${game.boardId} ended by checkmate, winner = $winner")
            game.winner = winner
            game.endgameMessage = EndgameMessage.CHECKMATE.message
        } else {
            logger.info("Game ${game.boardId} ended in draw by stalemate")
            game.winner = DRAW
            game.endgameMessage = EndgameMessage.STALEMATE.message
        }
    }

    fun buildEndgameResponse(game: GameEntity) = game.winner?.let {
        EndgameResponse(
            winner = it,
            endgameMessage = game.endgameMessage!!
        )
    }
}
