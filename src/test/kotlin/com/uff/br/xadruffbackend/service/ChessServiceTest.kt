package com.uff.br.xadruffbackend.service

import com.uff.br.xadruffbackend.GameRepository
import com.uff.br.xadruffbackend.ai.AIService
import com.uff.br.xadruffbackend.exception.GameNotFoundException
import com.uff.br.xadruffbackend.exception.InvalidMovementException
import com.uff.br.xadruffbackend.extension.toJsonString
import com.uff.br.xadruffbackend.extension.toStringPositions
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.GameEntity
import com.uff.br.xadruffbackend.model.LegalMovements
import com.uff.br.xadruffbackend.model.Position
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.enum.Level
import com.uff.br.xadruffbackend.model.enum.StartsBy
import com.uff.br.xadruffbackend.model.piece.Pawn
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
    private val chessService: ChessService = ChessService(gameRepository, movementService, aiService)

    init {
        every {
            gameRepository.save(any<GameEntity>())
        } returns mockGameEntity()
    }

    @Test
    fun `should create an initial board`() {

        val gameEntity = chessService.createInitialBoard(Level.BEGINNER)

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
        assertEquals("", chessResponse.iaMovement)
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
        assertNotEquals("", chessResponse.iaMovement)
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
