package com.uff.br.xadruffbackend

import com.uff.br.xadruffbackend.extension.position
import com.uff.br.xadruffbackend.extension.toJsonString
import com.uff.br.xadruffbackend.extension.toStringPositions
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.GameEntity
import com.uff.br.xadruffbackend.model.LegalMovements
import com.uff.br.xadruffbackend.model.Position
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.enum.StartsBy
import com.uff.br.xadruffbackend.model.piece.Knight
import com.uff.br.xadruffbackend.model.piece.Pawn
import com.uff.br.xadruffbackend.utils.buildEmptyBoard
import com.uff.br.xadruffbackend.utils.buildInitialBoard
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import javax.persistence.EntityNotFoundException

internal class ChessServiceTest {

    private val initialBoard: Board = buildInitialBoard()
    private val gameRepository = mockk<GameRepository>()
    private val chessService: ChessService = ChessService(gameRepository)

    init {
        every {
            gameRepository.save(any<GameEntity>())
        } returns mockGameEntity()
    }

    @Test
    fun `should create an initial board`() {

        val gameEntity = chessService.createInitialBoard()

        assertBoard(
            boardPositions = gameEntity.getBoard().positions,
            expectedBoardPositions = initialBoard.positions
        )
        assertNull(gameEntity.allMovements)
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
        // assertNotEquals(initialBoardPositions, chessResponse.board.positions) TODO após implementar movimentação da IA
    }

    @Test
    fun `should generate initial legal movements from initial board`() {
        val board = buildInitialBoard()
        val legalMoves = chessService.calculateLegalMovements(board)

        val correctLegalMoves = buildInitialLegalMovements()

        assertEquals(correctLegalMoves, legalMoves.movements)
    }

    @Test
    fun `should generate possible movements with one capture and three moves for knight in the edge`() {
        val board = buildEmptyBoard()
        val knight = Knight(Color.WHITE)
        val blackPawn = Pawn(Color.BLACK)
        board.position("h4").piece = knight
        board.position("f5").piece = blackPawn

        val legalMoves = chessService.calculateLegalMovements(board)

        val correctLegalMoves = mutableListOf("h4g6", "h4f5C", "h4f3", "h4g2")

        assert(correctLegalMoves.containsAll(legalMoves.movements))
    }

    @Test
    fun `should apply movement a1 to a2`(){

        val board = buildEmptyBoard()
        val pawn = Pawn(Color.WHITE)
        board.position("a1").piece = pawn

        chessService.applyMove(board, "a1a2")
        assertNull(board.position("a1").piece)
        assertEquals(pawn, board.position("a2").piece)

    }

    @Test
    fun `should apply movement a1 to a2 with capture`(){

        val board = buildEmptyBoard()
        val pawn = Pawn(Color.WHITE)
        board.position("a1").piece = pawn

        chessService.applyMove(board, "a1a2C")
        assertNull(board.position("a1").piece)
        assertEquals(pawn, board.position("a2").piece)

    }

    @Test
    fun `should move piece from a2 to a3 with initial board`(){

        val game = mockGameEntity()
        game.legalMovements = LegalMovements(buildInitialLegalMovements()).toJsonString()

        every {
            chessRepository.getById(game.boardId)
        } returns game

        val boardResponse = chessService.movePiece(game.boardId, "a2a3")
        assertEquals(game.boardId, boardResponse!!.boardId)
        assertEquals("",boardResponse!!.board.positions[6][0])
        assertEquals(Pawn(Color.WHITE).value.toString(), boardResponse.board.positions[5][0])

    }

    //@Test
    fun `should not move piece from a1 to a2 with initial board`(){

        val game = mockGameEntity()
        game.legalMovements = LegalMovements(buildInitialLegalMovements()).toJsonString()

        every {
            chessRepository.getById(game.boardId)
        } returns game

        val boardResponse = chessService.movePiece(game.boardId, "a1a2")
        assertEquals(game.boardId, boardResponse!!.boardId)
        assertEquals("",boardResponse!!.board.positions[6][0])
        assertEquals(Pawn(Color.WHITE).value.toString(), boardResponse.board.positions[5][0])

    }

    @Test
    fun `should not move piece if game is not found`(){

        val game = mockGameEntity()
        game.legalMovements = LegalMovements(buildInitialLegalMovements()).toJsonString()

        every {
            chessRepository.getById(any<String>())
        } throws EntityNotFoundException()

        assertThrows<EntityNotFoundException> {
            chessService.movePiece(game.boardId, "a2a3")
        }

    }

    fun buildInitialLegalMovements(): MutableList<String> {

        return mutableListOf(
            "a2a3", "a2a4", "b2b3", "b2b4", "c2c3", "c2c4", "d2d3", "d2d4",
            "e2e3", "e2e4", "f2f3", "f2f4", "g2g3", "g2g4", "h2h3", "h2h4",
            "b1a3", "b1c3", "g1f3", "g1h3"
        )

    }

    private fun assertBoard(boardPositions: List<List<Position>>, expectedBoardPositions: List<List<Position>>) {
        for (line in 0..7) {
            for (column in 0..7) {
                assertEquals(
                    boardPositions[line][column].piece?.value,
                    expectedBoardPositions[line][column].piece?.value
                )
            }
        }
    }

    private fun assertBoardResponse(
        boardPositions: List<List<String>>,
        expectedBoardPositions: List<List<String>>
    ) {
        for (line in 0..7) {
            for (column in 0..7) {
                assertEquals(boardPositions[line][column], expectedBoardPositions[line][column])
            }
        }
    }

    private fun mockGameEntity() = GameEntity(board = buildInitialBoard().toJsonString())
}
