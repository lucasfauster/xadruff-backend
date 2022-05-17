package com.uff.br.xadruffbackend

import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.enum.StartsBy
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.ChessResponse
import com.uff.br.xadruffbackend.model.GameEntity
import com.uff.br.xadruffbackend.model.LegalMovements
import com.uff.br.xadruffbackend.model.toBoardResponse
import com.uff.br.xadruffbackend.model.toJsonString
import org.springframework.stereotype.Service

@Service
class ChessService(private val chessRepository: ChessRepository) {

    fun createNewGame(startBy: StartsBy): ChessResponse {
        val game = createInitialBoard()
        var color = Color.WHITE

        if(startBy == StartsBy.AI){
            playAITurn(game)
            color = Color.BLACK
        }

        val playerLegalMovements = calculateLegalMovements(game.getBoard(), color)
        game.legalMovements = playerLegalMovements.toJsonString()
        chessRepository.save(game)
        return ChessResponse(boardId = game.boardId,
            legalMovements = playerLegalMovements,
            board = game.getBoard().toBoardResponse())
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

    fun createInitialBoard(): GameEntity {
        val board = Board()
        val gameEntity = GameEntity(board = board.toJsonString())
        chessRepository.save(gameEntity)
        return gameEntity
    }

}
