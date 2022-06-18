package com.uff.br.xadruffbackend.service

import com.uff.br.xadruffbackend.GameRepository
import com.uff.br.xadruffbackend.ai.AIService
import com.uff.br.xadruffbackend.exception.GameNotFoundException
import com.uff.br.xadruffbackend.exception.InvalidMovementException
import com.uff.br.xadruffbackend.extension.changeTurn
import com.uff.br.xadruffbackend.extension.position
import com.uff.br.xadruffbackend.extension.toJsonString
import com.uff.br.xadruffbackend.extension.toStringPositions
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.GameEntity
import com.uff.br.xadruffbackend.model.LegalMovements
import com.uff.br.xadruffbackend.model.Position
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.enum.EndgameMessage
import com.uff.br.xadruffbackend.model.enum.StartsBy
import com.uff.br.xadruffbackend.model.piece.Bishop
import com.uff.br.xadruffbackend.model.piece.King
import com.uff.br.xadruffbackend.model.piece.Pawn
import com.uff.br.xadruffbackend.model.piece.Queen
import com.uff.br.xadruffbackend.model.piece.Rook
import com.uff.br.xadruffbackend.utils.buildEmptyBoard
import com.uff.br.xadruffbackend.utils.buildInitialBoard
import com.uff.br.xadruffbackend.utils.buildInitialLegalMovements
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import javax.persistence.EntityNotFoundException

internal class ChessServiceTest {

    private val initialBoard: Board = buildInitialBoard()
    private val gameRepository = mockk<GameRepository>()
    private val movementService = MovementService()
    private val aiService = AIService(movementService)
    private val endgameService = EndgameService()
    private val chessService: ChessService = ChessService(gameRepository, movementService, aiService, endgameService)

    init {
        every {
            gameRepository.save(any<GameEntity>())
        } returns mockGameEntity()
    }

    @Test
    fun `should create an initial board`() {

        val gameEntity = chessService.createInitialGame()

        assertBoard(
            boardPositions = gameEntity.getBoard().positions,
            expectedBoardPositions = initialBoard.positions
        )
        assertTrue(gameEntity.allMovements.isEmpty())
        assertNull(gameEntity.legalMovements)
        assertNull(gameEntity.winner)
        assertEquals(gameEntity.blackDrawMoves, 0)
        assertEquals(gameEntity.whiteDrawMoves, 0)
    }

    @Test
    fun `should create a new game with player playing first`() {
        val chessResponse = chessService.createNewGame(StartsBy.PLAYER)
        assertNotNull(chessResponse.legalMovements)
        assertNotNull(chessResponse.boardId)
        assertNull(chessResponse.aiMovement)
        assertBoardResponse(
            boardPositions = chessResponse.board.positions,
            expectedBoardPositions = initialBoard.positions.toStringPositions()
        )
    }

    @Test
    fun `should create a new game with AI playing first`() {
        val chessResponse = chessService.createNewGame(StartsBy.AI)
        assertNotNull(chessResponse.legalMovements)
        assertNotNull(chessResponse.boardId)
        assertNotEquals("", chessResponse.aiMovement)
        assertNotEquals(buildInitialBoard().positions, chessResponse.board.positions)
    }

    @Test
    fun `should move piece from a2 to a3 with initial board`() {

        val game = mockGameEntity()
        game.legalMovements = LegalMovements(buildInitialLegalMovements()).toJsonString()

        every {
            gameRepository.getById(game.boardId)
        } returns game

        val boardResponse = chessService.movePiece(game.boardId, "a2a3")
        assertEquals(game.boardId, boardResponse.boardId)
        assertEquals("", boardResponse.board.positions[6][0])
        assertEquals(Pawn(Color.WHITE).value.toString(), boardResponse.board.positions[5][0])
    }

    @Test
    fun `should not move piece from a1 to a2 with initial board`() {

        val game = mockGameEntity()
        game.legalMovements = LegalMovements(buildInitialLegalMovements()).toJsonString()

        every {
            gameRepository.getById(game.boardId)
        } returns game

        assertThrows<InvalidMovementException> {
            chessService.movePiece(game.boardId, "a1a2")
        }
    }

    @Test
    fun `should not move piece if game is not found`() {

        val game = mockGameEntity()
        game.legalMovements = LegalMovements(buildInitialLegalMovements()).toJsonString()

        every {
            gameRepository.getById(any<String>())
        } throws JpaObjectRetrievalFailureException(EntityNotFoundException())

        assertThrows<GameNotFoundException> {
            chessService.movePiece(game.boardId, "a2a3")
        }
    }

    @Test
    fun `should return endgame by checkmate for BLACK`() {
        val board = buildEmptyBoard()
        board.position("a8").piece = King(Color.WHITE)
        board.position("b6").piece = Queen(Color.BLACK)
        board.position("c6").piece = Bishop(Color.BLACK)
        board.position("d5").piece = King(Color.BLACK)
        val legalMovements = LegalMovements(mutableListOf("b6b7", "b6b5"))
        board.changeTurn()
        val game = GameEntity(board = board.toJsonString(), legalMovements = legalMovements.toJsonString())

        every {
            gameRepository.getById(game.boardId)
        } returns game

        val response = chessService.movePiece(game.boardId, "b6b7")
        assertEquals("a8", response.kingInCheck)
        assertEquals(Color.BLACK.name, response.endgame?.winner)
        assertEquals(EndgameMessage.CHECKMATE.message, response.endgame?.endgameMessage)
    }

    @Test
    fun `should return endgame by checkmate for WHITE`() {
        val board = buildEmptyBoard()
        board.position("a8").piece = King(Color.BLACK)
        board.position("b6").piece = Queen(Color.WHITE)
        board.position("c6").piece = Bishop(Color.WHITE)
        board.position("d5").piece = King(Color.WHITE)
        val legalMovements = LegalMovements(mutableListOf("b6b7", "b6b5"))
        val game = GameEntity(board = board.toJsonString(), legalMovements = legalMovements.toJsonString())

        every {
            gameRepository.getById(game.boardId)
        } returns game

        val response = chessService.movePiece(game.boardId, "b6b7")
        assertEquals("a8", response.kingInCheck)
        assertEquals(Color.WHITE.name, response.endgame?.winner)
        assertEquals(EndgameMessage.CHECKMATE.message, response.endgame?.endgameMessage)
    }

    @Test
    fun `should return endgame by stalemate in WHITE move`() {
        val board = buildEmptyBoard()
        board.position("a8").piece = King(Color.BLACK)
        board.position("c6").piece = Rook(Color.WHITE)
        board.position("c7").piece = Rook(Color.WHITE)
        board.position("d5").piece = King(Color.WHITE)
        val legalMovements = LegalMovements(mutableListOf("c6b6", "c6c5"))
        val game = GameEntity(board = board.toJsonString(), legalMovements = legalMovements.toJsonString())

        every {
            gameRepository.getById(game.boardId)
        } returns game

        val response = chessService.movePiece(game.boardId, "c6b6")
        assertNull(response.kingInCheck)
        assertEquals(EndgameService.DRAW, response.endgame?.winner)
        assertEquals(EndgameMessage.STALEMATE.message, response.endgame?.endgameMessage)
    }

    @Test
    fun `should return endgame by stalemate in BLACK move`() {
        val board = buildEmptyBoard()
        board.position("a8").piece = King(Color.WHITE)
        board.position("c6").piece = Rook(Color.BLACK)
        board.position("c7").piece = Rook(Color.BLACK)
        board.position("d5").piece = King(Color.BLACK)
        board.changeTurn()
        val legalMovements = LegalMovements(mutableListOf("c6b6", "c6c5"))
        val game = GameEntity(board = board.toJsonString(), legalMovements = legalMovements.toJsonString())

        every {
            gameRepository.getById(game.boardId)
        } returns game

        val response = chessService.movePiece(game.boardId, "c6b6")
        assertNull(response.kingInCheck)
        assertEquals(EndgameService.DRAW, response.endgame?.winner)
        assertEquals(EndgameMessage.STALEMATE.message, response.endgame?.endgameMessage)
    }

    @Test
    fun `should return endgame by draw move rule in BLACK move`() {
        val board = buildEmptyBoard()
        board.position("a8").piece = King(Color.WHITE)
        board.position("c6").piece = Rook(Color.BLACK)
        board.position("d5").piece = King(Color.BLACK)
        board.changeTurn()
        val legalMovements = LegalMovements(mutableListOf("c6b6", "c6c5"))
        val game = GameEntity(board = board.toJsonString(), legalMovements = legalMovements.toJsonString())
        game.blackDrawMoves = 49
        game.whiteDrawMoves = 50

        every {
            gameRepository.getById(game.boardId)
        } returns game

        val response = chessService.movePiece(game.boardId, "c6b6")
        assertNull(response.kingInCheck)
        assertEquals(EndgameService.DRAW, response.endgame?.winner)
        assertEquals(EndgameMessage.DRAW_RULE.message, response.endgame?.endgameMessage)
    }

    @Test
    fun `should return endgame by draw move rule in WHITE move`() {
        val board = buildEmptyBoard()
        board.position("a8").piece = King(Color.BLACK)
        board.position("c6").piece = Rook(Color.WHITE)
        board.position("d5").piece = King(Color.WHITE)
        val legalMovements = LegalMovements(mutableListOf("c6b6", "c6c5"))
        val game = GameEntity(board = board.toJsonString(), legalMovements = legalMovements.toJsonString())
        game.blackDrawMoves = 50
        game.whiteDrawMoves = 49

        every {
            gameRepository.getById(game.boardId)
        } returns game

        val response = chessService.movePiece(game.boardId, "c6b6")
        assertNull(response.kingInCheck)
        assertEquals(EndgameService.DRAW, response.endgame?.winner)
        assertEquals(EndgameMessage.DRAW_RULE.message, response.endgame?.endgameMessage)
    }

    @Test
    fun `should reset draw moves if it was a capture`() {
        val board = buildEmptyBoard()
        board.position("a8").piece = King(Color.BLACK)
        board.position("b6").piece = Rook(Color.BLACK)
        board.position("c6").piece = Rook(Color.WHITE)
        board.position("d5").piece = King(Color.WHITE)
        val legalMovements = LegalMovements(mutableListOf("c6b6C", "c6c5"))
        val game = GameEntity(board = board.toJsonString(), legalMovements = legalMovements.toJsonString())
        game.blackDrawMoves = 50
        game.whiteDrawMoves = 49

        every {
            gameRepository.getById(game.boardId)
        } returns game

        val response = chessService.movePiece(game.boardId, "c6b6C")
        assertNull(response.kingInCheck)
        assertNull(response.endgame)
        assertEquals(0, game.whiteDrawMoves)
    }

    @Test
    fun `should reset draw moves if it was a pawn movement`() {
        val board = buildEmptyBoard()
        board.position("a8").piece = King(Color.WHITE)
        board.position("b6").piece = Pawn(Color.BLACK)
        board.position("d5").piece = King(Color.BLACK)
        board.changeTurn()
        val legalMovements = LegalMovements(mutableListOf("b6c6", "c6c5"))
        val game = GameEntity(board = board.toJsonString(), legalMovements = legalMovements.toJsonString())
        game.blackDrawMoves = 49
        game.whiteDrawMoves = 50

        every {
            gameRepository.getById(game.boardId)
        } returns game

        val response = chessService.movePiece(game.boardId, "b6c6")
        assertNull(response.kingInCheck)
        assertNull(response.endgame)
        assertEquals(0, game.blackDrawMoves)
    }

    @Test
    fun `should return white king in check`() {
        val board = buildEmptyBoard()
        board.position("a8").piece = King(Color.BLACK)
        board.position("b6").piece = Rook(Color.WHITE)
        board.position("c7").piece = Rook(Color.WHITE)
        board.position("g6").piece = Pawn(Color.BLACK)
        board.position("h3").piece = King(Color.WHITE)
        val legalMovements = LegalMovements(mutableListOf("h3h4", "c6c5"))
        val game = GameEntity(board = board.toJsonString(), legalMovements = legalMovements.toJsonString())

        every {
            gameRepository.getById(game.boardId)
        } returns game

        val response = chessService.movePiece(game.boardId, "h3h4")
        assertEquals("h4", response.kingInCheck)
        assertNull(response.endgame)
        assertEquals("g6g5", response.aiMovement)
    }

    @Test
    fun `should return black king in check`() {
        val board = buildEmptyBoard()
        board.position("h1").piece = King(Color.WHITE)
        board.position("f2").piece = Rook(Color.BLACK)
        board.position("g3").piece = Rook(Color.BLACK)
        board.position("b3").piece = Pawn(Color.WHITE)
        board.position("a6").piece = King(Color.BLACK)
        board.changeTurn()
        val legalMovements = LegalMovements(mutableListOf("a6a5", "c6c5"))
        val game = GameEntity(board = board.toJsonString(), legalMovements = legalMovements.toJsonString())

        every {
            gameRepository.getById(game.boardId)
        } returns game

        val response = chessService.movePiece(game.boardId, "a6a5")
        assertEquals("b3b4", response.aiMovement)
        assertEquals("a5", response.kingInCheck)
        assertNull(response.endgame)
    }

    private fun assertBoard(boardPositions: List<List<Position>>, expectedBoardPositions: List<List<Position>>) {
        for (row in 0..7) {
            for (column in 0..7) {
                assertEquals(
                    boardPositions[row][column].piece?.value,
                    expectedBoardPositions[row][column].piece?.value
                )
            }
        }
    }

    private fun assertBoardResponse(
        boardPositions: List<List<String>>,
        expectedBoardPositions: List<List<String>>
    ) {
        for (row in 0..7) {
            for (column in 0..7) {
                assertEquals(boardPositions[row][column], expectedBoardPositions[row][column])
            }
        }
    }

    private fun mockGameEntity() = GameEntity(board = buildInitialBoard().toJsonString())
}
