package com.uff.br.xadruffbackend

import com.uff.br.xadruffbackend.extension.BoardMovementsCalculatorExtensions.calculatePseudoLegalMoves
import com.uff.br.xadruffbackend.extension.toBoardResponse
import com.uff.br.xadruffbackend.extension.toJsonString
import com.uff.br.xadruffbackend.extension.toMap
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.ChessResponse
import com.uff.br.xadruffbackend.model.GameEntity
import com.uff.br.xadruffbackend.model.LegalMovements
import com.uff.br.xadruffbackend.model.Position
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.enum.StartsBy
import com.uff.br.xadruffbackend.model.piece.Bishop
import com.uff.br.xadruffbackend.model.piece.King
import com.uff.br.xadruffbackend.model.piece.Knight
import com.uff.br.xadruffbackend.model.piece.Pawn
import com.uff.br.xadruffbackend.model.piece.Queen
import com.uff.br.xadruffbackend.model.piece.Rook
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ChessService(
    private val chessRepository: ChessRepository,
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun createNewGame(startBy: StartsBy): ChessResponse {
        val game = createInitialBoard()
        logger.info("Initialized new game entity with id = {}", game.boardId)

        if(startBy == StartsBy.AI){
            playAITurn(game)
        }

        val playerLegalMovements = calculateLegalMovements(game.getBoard())
        game.legalMovements = playerLegalMovements.toJsonString()
        logger.info("Calculated player possible movements, boardId = {}, legalMovements = {}", game.boardId, game.legalMovements)

        chessRepository.save(game)

        return ChessResponse(
            boardId = game.boardId,
            legalMovements = playerLegalMovements.movements.toMap(),
            board = game.getBoard().toBoardResponse()
        )
    }

    fun playAITurn(game: GameEntity){ // TODO chamar módulo de movimentação da IA
        // val legalMovements = calculateLegalMovements(game.boardPositions, color)
        // iaService.movePieces(game.boardPositions, blackLegalMovements)
        // game.allMovements = add movimento da IA
        // game.whiteDrawMoves = add movimento se n for peão
    }

    fun calculateLegalMovements(board: Board): LegalMovements {
        logger.debug("Calculating legal movements")
        return board.calculatePseudoLegalMoves()
    }

    fun createInitialBoard(): GameEntity {
        logger.debug("Creating new board")
        val positions = createInitialPositions()
        val board = Board(positions = positions)

        logger.debug("Saving game in database")
        val gameEntity = GameEntity(board = board.toJsonString())
        chessRepository.save(gameEntity)

        return gameEntity
    }

    private fun createInitialPositions() =
        listOf(
            listOf(
                Position(line = 0, column = 0, piece = Rook(Color.BLACK)),
                Position(line = 0, column = 1, piece = Knight(Color.BLACK)),
                Position(line = 0, column = 2, piece = Bishop(Color.BLACK)),
                Position(line = 0, column = 3, piece = King(Color.BLACK)),
                Position(line = 0, column = 4, piece = Queen(Color.BLACK)),
                Position(line = 0, column = 5, piece = Bishop(Color.BLACK)),
                Position(line = 0, column = 6, piece = Knight(Color.BLACK)),
                Position(line = 0, column = 7, piece = Rook(Color.BLACK))
            ),
            listOf(
                Position(line = 1, column = 0, piece = Pawn(Color.BLACK)),
                Position(line = 1, column = 1, piece = Pawn(Color.BLACK)),
                Position(line = 1, column = 2, piece = Pawn(Color.BLACK)),
                Position(line = 1, column = 3, piece = Pawn(Color.BLACK)),
                Position(line = 1, column = 4, piece = Pawn(Color.BLACK)),
                Position(line = 1, column = 5, piece = Pawn(Color.BLACK)),
                Position(line = 1, column = 6, piece = Pawn(Color.BLACK)),
                Position(line = 1, column = 7, piece = Pawn(Color.BLACK))
            ),
            listOf(
                Position(line = 2, column = 0, piece = null),
                Position(line = 2, column = 1, piece = null),
                Position(line = 2, column = 2, piece = null),
                Position(line = 2, column = 3, piece = null),
                Position(line = 2, column = 4, piece = null),
                Position(line = 2, column = 5, piece = null),
                Position(line = 2, column = 6, piece = null),
                Position(line = 2, column = 7, piece = null)
            ),
            listOf(
                Position(line = 3, column = 0, piece = null),
                Position(line = 3, column = 1, piece = null),
                Position(line = 3, column = 2, piece = null),
                Position(line = 3, column = 3, piece = null),
                Position(line = 3, column = 4, piece = null),
                Position(line = 3, column = 5, piece = null),
                Position(line = 3, column = 6, piece = null),
                Position(line = 3, column = 7, piece = null)
            ),
            listOf(
                Position(line = 4, column = 0, piece = null),
                Position(line = 4, column = 1, piece = null),
                Position(line = 4, column = 2, piece = null),
                Position(line = 4, column = 3, piece = null),
                Position(line = 4, column = 4, piece = null),
                Position(line = 4, column = 5, piece = null),
                Position(line = 4, column = 6, piece = null),
                Position(line = 4, column = 7, piece = null)
            ),
            listOf(
                Position(line = 5, column = 0, piece = null),
                Position(line = 5, column = 1, piece = null),
                Position(line = 5, column = 2, piece = null),
                Position(line = 5, column = 3, piece = null),
                Position(line = 5, column = 4, piece = null),
                Position(line = 5, column = 5, piece = null),
                Position(line = 5, column = 6, piece = null),
                Position(line = 5, column = 7, piece = null)
            ),
            listOf(
                Position(line = 6, column = 0, piece = Pawn(Color.WHITE)),
                Position(line = 6, column = 1, piece = Pawn(Color.WHITE)),
                Position(line = 6, column = 2, piece = Pawn(Color.WHITE)),
                Position(line = 6, column = 3, piece = Pawn(Color.WHITE)),
                Position(line = 6, column = 4, piece = Pawn(Color.WHITE)),
                Position(line = 6, column = 5, piece = Pawn(Color.WHITE)),
                Position(line = 6, column = 6, piece = Pawn(Color.WHITE)),
                Position(line = 6, column = 7, piece = Pawn(Color.WHITE))
            ),
            listOf(
                Position(line = 7, column = 0, piece = Rook(Color.WHITE)),
                Position(line = 7, column = 1, piece = Knight(Color.WHITE)),
                Position(line = 7, column = 2, piece = Bishop(Color.WHITE)),
                Position(line = 7, column = 3, piece = Queen(Color.WHITE)),
                Position(line = 7, column = 4, piece = King(Color.WHITE)),
                Position(line = 7, column = 5, piece = Bishop(Color.WHITE)),
                Position(line = 7, column = 6, piece = Knight(Color.WHITE)),
                Position(line = 7, column = 7, piece = Rook(Color.WHITE))
            )
        )

}
