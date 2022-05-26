package com.uff.br.xadruffbackend

import com.uff.br.xadruffbackend.extension.BoardMovementsCalculatorExtensions.calculatePseudoLegalMoves
import com.uff.br.xadruffbackend.extension.Position
import com.uff.br.xadruffbackend.extension.toBoardResponse
import com.uff.br.xadruffbackend.extension.toJsonString
import com.uff.br.xadruffbackend.extension.toMap
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.ChessResponse
import com.uff.br.xadruffbackend.model.GameEntity
import com.uff.br.xadruffbackend.model.LegalMovements
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
    private val gameRepository: GameRepository,
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun createNewGame(startBy: StartsBy): ChessResponse {
        val game = createInitialBoard()
        logger.info("Initialized new game entity with id = {}", game.boardId)

        if (startBy == StartsBy.AI) {
            playAITurn(game)
        }

        val playerLegalMovements = calculateLegalMovements(game.getBoard())
        game.legalMovements = playerLegalMovements.toJsonString()
        logger.info("Calculated player possible movements, boardId = {}, legalMovements = {}", game.boardId, game.legalMovements)

        gameRepository.save(game)

        return ChessResponse(
            boardId = game.boardId,
            legalMovements = playerLegalMovements.movements.toMap(),
            board = game.getBoard().toBoardResponse()
        )
    }

    fun playAITurn(game: GameEntity) { // TODO chamar módulo de movimentação da IA
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
        gameRepository.save(gameEntity)

        return gameEntity
    }

    private fun createInitialPositions() =
        listOf(
            listOf(
                Position("a8", piece = Rook(Color.BLACK)),
                Position("b8", piece = Knight(Color.BLACK)),
                Position("c8", piece = Bishop(Color.BLACK)),
                Position("d8", piece = Queen(Color.BLACK)),
                Position("e8", piece = King(Color.BLACK)),
                Position("f8", piece = Bishop(Color.BLACK)),
                Position("g8", piece = Knight(Color.BLACK)),
                Position("h8", piece = Rook(Color.BLACK))
            ),
            listOf(
                Position("a7", piece = Pawn(Color.BLACK)),
                Position("b7", piece = Pawn(Color.BLACK)),
                Position("c7", piece = Pawn(Color.BLACK)),
                Position("d7", piece = Pawn(Color.BLACK)),
                Position("e7", piece = Pawn(Color.BLACK)),
                Position("f7", piece = Pawn(Color.BLACK)),
                Position("g7", piece = Pawn(Color.BLACK)),
                Position("h7", piece = Pawn(Color.BLACK))
            ),
            listOf(
                Position("a6", piece = null),
                Position("b6", piece = null),
                Position("c6", piece = null),
                Position("d6", piece = null),
                Position("e6", piece = null),
                Position("f6", piece = null),
                Position("g6", piece = null),
                Position("h6", piece = null)
            ),
            listOf(
                Position("a5", piece = null),
                Position("b5", piece = null),
                Position("c5", piece = null),
                Position("d5", piece = null),
                Position("e5", piece = null),
                Position("f5", piece = null),
                Position("g5", piece = null),
                Position("h5", piece = null)
            ),
            listOf(
                Position("a4", piece = null),
                Position("b4", piece = null),
                Position("c4", piece = null),
                Position("d4", piece = null),
                Position("e4", piece = null),
                Position("f4", piece = null),
                Position("g4", piece = null),
                Position("h4", piece = null)
            ),
            listOf(
                Position("a3", piece = null),
                Position("b3", piece = null),
                Position("c3", piece = null),
                Position("d3", piece = null),
                Position("e3", piece = null),
                Position("f3", piece = null),
                Position("g3", piece = null),
                Position("h3", piece = null)
            ),
            listOf(
                Position("a2", piece = Pawn(Color.WHITE)),
                Position("b2", piece = Pawn(Color.WHITE)),
                Position("c2", piece = Pawn(Color.WHITE)),
                Position("d2", piece = Pawn(Color.WHITE)),
                Position("e2", piece = Pawn(Color.WHITE)),
                Position("f2", piece = Pawn(Color.WHITE)),
                Position("g2", piece = Pawn(Color.WHITE)),
                Position("h2", piece = Pawn(Color.WHITE))
            ),
            listOf(
                Position("a1", piece = Rook(Color.WHITE)),
                Position("b1", piece = Knight(Color.WHITE)),
                Position("c1", piece = Bishop(Color.WHITE)),
                Position("d1", piece = Queen(Color.WHITE)),
                Position("e1", piece = King(Color.WHITE)),
                Position("f1", piece = Bishop(Color.WHITE)),
                Position("g1", piece = Knight(Color.WHITE)),
                Position("h1", piece = Rook(Color.WHITE))
            )
        )
}
