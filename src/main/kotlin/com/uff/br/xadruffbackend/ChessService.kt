package com.uff.br.xadruffbackend

import com.uff.br.xadruffbackend.enum.Color
import com.uff.br.xadruffbackend.enum.StartsBy
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.ChessResponse
import com.uff.br.xadruffbackend.model.GameEntity
import com.uff.br.xadruffbackend.model.LegalMovements
import com.uff.br.xadruffbackend.model.toJsonString
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ChessService(private val chessRepository: ChessRepository) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun createNewGame(startBy: StartsBy): ChessResponse {
        val board = createInitialBoard()
        val game = GameEntity(board = board.toJsonString())
        logger.info("Initialized new game entity with id = {}", game.boardId)

        var activeColor = Color.WHITE
        if(startBy == StartsBy.AI){
            playAITurn(game)
            activeColor = Color.BLACK
        }

        val playerLegalMovements = calculateLegalMovements(game.getBoard(), activeColor)
        game.legalMovements = playerLegalMovements.toJsonString()
        logger.info("Calculated player possible movements, boardId = {}, legalMovements = {}", game.boardId, game.legalMovements)

        chessRepository.save(game)
        return ChessResponse(boardId = game.boardId, legalMovements = playerLegalMovements, board = game.getBoard())
    }

    fun playAITurn(game: GameEntity){ // TODO chamar módulo de movimentação da IA
        // val legalMovements = calculateLegalMovements(game.boardPositions, color)
        // iaService.movePieces(game.boardPositions, blackLegalMovements)
        // game.allMovements = add movimento da IA
        // game.whiteDrawMoves = add movimento se n for peão
    }

    fun calculateLegalMovements(board: Board, color: Color): LegalMovements { // TODO criar lista de movimentos legais
        return LegalMovements(mapOf(Pair("a1", listOf("b1", "c1"))))
    }

    fun createInitialBoard(): Board {
        val positions = listOf(
            mutableListOf("r", "n", "b", "q", "k", "b", "n", "r"),
            mutableListOf("p", "p", "p", "p", "p", "p", "p", "p"),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("P", "P", "P", "P", "P", "P", "P", "P"),
            mutableListOf("R", "N", "B", "Q", "K", "B", "N", "R")
        )

        return Board(positions = positions)
    }

}
