package com.uff.br.xadruffbackend.service

import com.uff.br.xadruffbackend.GameRepository
import com.uff.br.xadruffbackend.ai.AIService
import com.uff.br.xadruffbackend.exception.GameNotFoundException
import com.uff.br.xadruffbackend.exception.InvalidMovementException
import com.uff.br.xadruffbackend.extension.position
import com.uff.br.xadruffbackend.extension.toJsonString
import com.uff.br.xadruffbackend.extension.toStringPositions
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.GameEntity
import com.uff.br.xadruffbackend.model.LegalMovements
import com.uff.br.xadruffbackend.model.Position
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.enum.Level
import com.uff.br.xadruffbackend.model.enum.StartsBy
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
import org.junit.jupiter.api.Assertions.assertFalse
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

        val gameEntity = chessService.createInitialGame(Level.BEGINNER)

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
        val chessResponse = chessService.createNewGame(StartsBy.PLAYER, Level.BEGINNER)
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
        val chessResponse = chessService.createNewGame(StartsBy.AI, Level.BEGINNER)
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
    fun `should apply move for rook when applied small rook movement for white king`() {
        val board = buildInitialBoard()
        board.position("f1").piece = null
        board.position("g1").piece = null
        val legalMovements = LegalMovements(mutableListOf("e1g1Oh1f1"))
        val game = GameEntity(board = board.toJsonString(), legalMovements = legalMovements.toJsonString())

        chessService.handleMove("e1g1Oh1f1", game)
        assertEquals(Rook.VALUE.uppercaseChar(), game.getBoard().position("f1").piece?.value)
        assertEquals(King.VALUE.uppercaseChar(), game.getBoard().position("g1").piece?.value)
    }

    @Test
    fun `should transform legal movement that causes check in black king`() {
        val board = buildEmptyBoard()
        board.position("c3").piece = Queen(Color.WHITE)
        board.position("e7").piece = King(Color.BLACK)
        board.turnColor = Color.BLACK

        val legalMovements = mutableListOf("e7e6")
        val game =
            GameEntity(
                board = board.toJsonString(),
                legalMovements = LegalMovements(legalMovements).toJsonString()
            )
        chessService.handleMove("e7e6", game)
        val newLegalMovements = game.legalMovements

        assert(newLegalMovements?.contains("c3c4Ke6") ?: false)
    }

    @Test
    fun `should not transform legal movements list when it has no movement that causes check`() {
        val board = buildEmptyBoard()
        board.position("c3").piece = Queen(Color.WHITE)

        val legalMovements = mutableListOf("c3d3")
        val game =
            GameEntity(
                board = board.toJsonString(),
                legalMovements = LegalMovements(legalMovements).toJsonString()
            )
        chessService.handleMove("c3d3", game)
        val newLegalMovements = game.legalMovements

        assertFalse(newLegalMovements?.contains("K") ?: false)
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
