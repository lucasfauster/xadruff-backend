package com.uff.br.xadruffbackend

import com.uff.br.xadruffbackend.extension.toJsonString
import com.uff.br.xadruffbackend.extension.toStringPositions
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.GameEntity
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

internal class ChessServiceTest{

    private val initialBoard: Board = buildInitialBoard()
    private val chessRepository = mockk<ChessRepository>()
    private val chessService: ChessService = ChessService(chessRepository)

    init {
        every {
            chessRepository.save(any<GameEntity>())
        } returns mockGameEntity()
    }

    @Test
    fun `should create an initial board`(){

        val gameEntity = chessService.createInitialBoard()

        assertBoard(boardPositions = gameEntity.getBoard().positions,
            expectedBoardPositions = initialBoard.positions)
        assertNull(gameEntity.allMovements)
        assertNull(gameEntity.legalMovements)
        assertNull(gameEntity.winner)
        assertEquals(gameEntity.blackDrawMoves, 0)
        assertEquals(gameEntity.whiteDrawMoves, 0)
    }

    @Test
    fun `should create a new game with player playing first`(){
        val chessResponse = chessService.createNewGame(StartsBy.PLAYER)
        assertNotNull(chessResponse.legalMovements)
        assertNotNull(chessResponse.boardId)
        assertBoardResponse(boardPositions = chessResponse.board.positions,
            expectedBoardPositions = initialBoard.positions.toStringPositions())

    }

    @Test
    fun `should create a new game with AI playing first`(){
        val chessResponse = chessService.createNewGame(StartsBy.AI)
        assertNotNull(chessResponse.legalMovements)
        assertNotNull(chessResponse.boardId)
        // assertNotEquals(initialBoardPositions, chessResponse.board.positions) TODO após implementar movimentação da IA
    }

    @Test
    fun `should generate initial legal movements from initial board`(){
        val board = buildInitialBoard()
        val legalMoves = chessService.calculateLegalMovements(board)

        val correctLegalMoves = mutableListOf("a2a3", "a2a4", "b2b3", "b2b4", "c2c3", "c2c4", "d2d3", "d2d4",
            "e2e3", "e2e4", "f2f3", "f2f4", "g2g3", "g2g4", "h2h3", "h2h4",
            "b1a3", "b1c3", "g1f3", "g1h3")

        assertEquals(correctLegalMoves, legalMoves.movements)
    }

    @Test
    fun `should generate possible movements of knight `(){
        val board = buildEmptyBoard()
        val knight = Knight(Color.WHITE)
        board.positions[4][4].piece = knight

        val legalMoves = chessService.calculateLegalMovements(board)

        val correctLegalMoves = mutableListOf("e4d6", "e4f6", "e4c5", "e4g5", "e4c3", "e4g3", "e4d2", "e4f2")

        assert(correctLegalMoves.containsAll(legalMoves.movements))
    }

    @Test
    fun `should generate possible movements whit one capture and three moves for knight in the edge`(){
        val board = buildEmptyBoard()
        val knight = Knight(Color.WHITE)
        val blackPawn = Pawn(Color.BLACK)
        board.positions[4][7].piece = knight
        board.positions[3][5].piece = blackPawn

        val legalMoves = chessService.calculateLegalMovements(board)

        val correctLegalMoves = mutableListOf("h4g6", "h4f5C", "h4f3", "h4g2")

        assert(correctLegalMoves.containsAll(legalMoves.movements))
    }

    private fun assertBoard(boardPositions: List<List<Position>>, expectedBoardPositions: List<List<Position>>) {
        for(line in 0..7) {
            for(column in 0..7) {
                assertEquals(boardPositions[line][column].piece?.value,
                    expectedBoardPositions[line][column].piece?.value
                )
            }
        }
    }

    private fun assertBoardResponse(boardPositions: List<List<String>>,
                                    expectedBoardPositions: List<List<String>>) {
        for(line in 0..7) {
            for(column in 0..7) {
                assertEquals(boardPositions[line][column], expectedBoardPositions[line][column])
            }
        }
    }


    private fun mockGameEntity() = GameEntity(board = buildInitialBoard().toJsonString())

}