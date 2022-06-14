package com.uff.br.xadruffbackend.service

import com.uff.br.xadruffbackend.GameRepository
import com.uff.br.xadruffbackend.ai.AIService
import com.uff.br.xadruffbackend.exception.GameNotFoundException
import com.uff.br.xadruffbackend.extension.position
import com.uff.br.xadruffbackend.extension.toBoardResponse
import com.uff.br.xadruffbackend.extension.toJsonString
import com.uff.br.xadruffbackend.extension.toMap
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.ChessResponse
import com.uff.br.xadruffbackend.model.GameEntity
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.enum.Level
import com.uff.br.xadruffbackend.model.enum.StartsBy
import com.uff.br.xadruffbackend.model.piece.Bishop
import com.uff.br.xadruffbackend.model.piece.King
import com.uff.br.xadruffbackend.model.piece.Knight
import com.uff.br.xadruffbackend.model.piece.Pawn
import com.uff.br.xadruffbackend.model.piece.Queen
import com.uff.br.xadruffbackend.model.piece.Rook
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.stereotype.Service

@Service
class ChessService(
    @Autowired private val gameRepository: GameRepository,
    @Autowired private val movementService: MovementService,
    @Autowired private val aiService: AIService
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun createNewGame(startBy: StartsBy, level: Level): ChessResponse {
        val game = createInitialBoard(level)
        logger.info("Initialized new game entity with id = {} and level = {}", game.boardId, level)

        var aiMove = ""
        if (startBy == StartsBy.AI) {
            aiMove = playAITurn(game)
        } else {
            val playerLegalMovements = movementService.calculateLegalMovements(game.getBoard())
            logger.info(
                "Calculated player possible movements = {} for boardId = {}", playerLegalMovements, game.boardId
            )
            game.legalMovements = playerLegalMovements.toJsonString()
            gameRepository.save(game)
        }

        return ChessResponse(
            boardId = game.boardId,
            legalMovements = game.getLegalMovements().movements.toMap(),
            board = game.getBoard().toBoardResponse(),
            iaMovement = aiMove
        )
    }

    fun getGameById(boardId: String): GameEntity =
        try {
            gameRepository.getById(boardId)
        } catch (exc: JpaObjectRetrievalFailureException) {
            throw GameNotFoundException("A game with board-id $boardId was not found.", exc)
        }

    private fun saveGameState(game: GameEntity, board: Board, move: String) {
        game.board = board.toJsonString()
        game.legalMovements = movementService.calculateLegalMovements(board).toJsonString()
        game.allMovements += " $move"
        gameRepository.save(game)
    }

    fun playAITurn(game: GameEntity): String {
        logger.info("Starting AI turn for game with id = {}", game.boardId)
        val board = game.getBoard()
        val aiMove = aiService.play(game.level, board)
        movementService.applyMove(board, aiMove)
        saveGameState(game, board, aiMove)
        return aiMove
    }

    fun movePiece(boardId: String, move: String): ChessResponse {
        val game = getGameById(boardId)
        movementService.verifyIsAllowedMove(game.getLegalMovements(), move)
        val board = game.getBoard()

        movementService.applyMove(board, move)
        saveGameState(game, board, move)
        val aiMove = playAITurn(game)

        return ChessResponse(
            boardId = game.boardId,
            legalMovements = game.getLegalMovements().movements.toMap(),
            board = game.getBoard().toBoardResponse(),
            iaMovement = aiMove
        )
    }

    fun createInitialBoard(level: Level): GameEntity {
        logger.debug("Creating new board")
        val positions = createInitialPositions()
        val board = Board(positions = positions)

        logger.debug("Saving game in database")
        val gameEntity = GameEntity(board = board.toJsonString(), level = level)
        gameRepository.save(gameEntity)

        return gameEntity
    }

    @Suppress("LongMethod")
    private fun createInitialPositions() =
        listOf(
            listOf(
                position("a8", piece = Rook(Color.BLACK)),
                position("b8", piece = Knight(Color.BLACK)),
                position("c8", piece = Bishop(Color.BLACK)),
                position("d8", piece = Queen(Color.BLACK)),
                position("e8", piece = King(Color.BLACK)),
                position("f8", piece = Bishop(Color.BLACK)),
                position("g8", piece = Knight(Color.BLACK)),
                position("h8", piece = Rook(Color.BLACK))
            ),
            listOf(
                position("a7", piece = Pawn(Color.BLACK)),
                position("b7", piece = Pawn(Color.BLACK)),
                position("c7", piece = Pawn(Color.BLACK)),
                position("d7", piece = Pawn(Color.BLACK)),
                position("e7", piece = Pawn(Color.BLACK)),
                position("f7", piece = Pawn(Color.BLACK)),
                position("g7", piece = Pawn(Color.BLACK)),
                position("h7", piece = Pawn(Color.BLACK))
            ),
            listOf(
                position("a6", piece = null),
                position("b6", piece = null),
                position("c6", piece = null),
                position("d6", piece = null),
                position("e6", piece = null),
                position("f6", piece = null),
                position("g6", piece = null),
                position("h6", piece = null)
            ),
            listOf(
                position("a5", piece = null),
                position("b5", piece = null),
                position("c5", piece = null),
                position("d5", piece = null),
                position("e5", piece = null),
                position("f5", piece = null),
                position("g5", piece = null),
                position("h5", piece = null)
            ),
            listOf(
                position("a4", piece = null),
                position("b4", piece = null),
                position("c4", piece = null),
                position("d4", piece = null),
                position("e4", piece = null),
                position("f4", piece = null),
                position("g4", piece = null),
                position("h4", piece = null)
            ),
            listOf(
                position("a3", piece = null),
                position("b3", piece = null),
                position("c3", piece = null),
                position("d3", piece = null),
                position("e3", piece = null),
                position("f3", piece = null),
                position("g3", piece = null),
                position("h3", piece = null)
            ),
            listOf(
                position("a2", piece = Pawn(Color.WHITE)),
                position("b2", piece = Pawn(Color.WHITE)),
                position("c2", piece = Pawn(Color.WHITE)),
                position("d2", piece = Pawn(Color.WHITE)),
                position("e2", piece = Pawn(Color.WHITE)),
                position("f2", piece = Pawn(Color.WHITE)),
                position("g2", piece = Pawn(Color.WHITE)),
                position("h2", piece = Pawn(Color.WHITE))
            ),
            listOf(
                position("a1", piece = Rook(Color.WHITE)),
                position("b1", piece = Knight(Color.WHITE)),
                position("c1", piece = Bishop(Color.WHITE)),
                position("d1", piece = Queen(Color.WHITE)),
                position("e1", piece = King(Color.WHITE)),
                position("f1", piece = Bishop(Color.WHITE)),
                position("g1", piece = Knight(Color.WHITE)),
                position("h1", piece = Rook(Color.WHITE))
            )
        )
}
