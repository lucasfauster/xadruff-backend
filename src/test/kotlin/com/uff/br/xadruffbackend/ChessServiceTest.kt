package com.uff.br.xadruffbackend

import com.uff.br.xadruffbackend.enum.Color
import com.uff.br.xadruffbackend.enum.StartsBy
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.GameEntity
import com.uff.br.xadruffbackend.model.Piece
import com.uff.br.xadruffbackend.model.Position
import com.uff.br.xadruffbackend.model.toJsonString
import com.uff.br.xadruffbackend.model.toStringPositions
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ChessServiceTest{

    companion object {
        val initialBoardPositions: List<MutableList<Piece?>> = listOf(
            mutableListOf(
                Piece(value = 'r', position = Position(0, 0)),
                Piece(value = 'n', position = Position(0, 1)),
                Piece(value = 'b', position = Position(0, 2)),
                Piece(value = 'k', position = Position(0, 3)),
                Piece(value = 'q', position = Position(0, 4)),
                Piece(value = 'b', position = Position(0, 5)),
                Piece(value = 'n', position = Position(0, 6)),
                Piece(value = 'r', position = Position(0, 7))
            ),
            mutableListOf(
                Piece(value = 'p', position = Position(1, 0)),
                Piece(value = 'p', position = Position(1, 1)),
                Piece(value = 'p', position = Position(1, 2)),
                Piece(value = 'p', position = Position(1, 3)),
                Piece(value = 'p', position = Position(1, 4)),
                Piece(value = 'p', position = Position(1, 5)),
                Piece(value = 'p', position = Position(1, 6)),
                Piece(value = 'p', position = Position(1, 7))
            ),
            mutableListOf(null, null, null, null, null, null, null, null),
            mutableListOf(null, null, null, null, null, null, null, null),
            mutableListOf(null, null, null, null, null, null, null, null),
            mutableListOf(null, null, null, null, null, null, null, null),
            mutableListOf(
                Piece(value = 'P', position = Position(6, 0)),
                Piece(value = 'P', position = Position(6, 1)),
                Piece(value = 'P', position = Position(6, 2)),
                Piece(value = 'P', position = Position(6, 3)),
                Piece(value = 'P', position = Position(6, 4)),
                Piece(value = 'P', position = Position(6, 5)),
                Piece(value = 'P', position = Position(6, 6)),
                Piece(value = 'P', position = Position(6, 7))
            ),
            mutableListOf(
                Piece(value = 'R', position = Position(7, 0)),
                Piece(value = 'N', position = Position(7, 1)),
                Piece(value = 'B', position = Position(7, 2)),
                Piece(value = 'Q', position = Position(7, 3)),
                Piece(value = 'K', position = Position(7, 4)),
                Piece(value = 'B', position = Position(7, 5)),
                Piece(value = 'N', position = Position(7, 6)),
                Piece(value = 'R', position = Position(7, 7))
            )
        )
    }

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
            expectedBoardPositions = initialBoardPositions)
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
            expectedBoardPositions = initialBoardPositions.toStringPositions())

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
        val board = Board()
        board.positions = initialBoardPositions
        val legalmoves = chessService.calculateLegalMovements(board, Color.WHITE)

        val correctLegalMoves = mutableListOf("a2a3", "a2a4", "b2b3", "b2b4", "c2c3", "c2c4", "d2d3", "d2d4",
            "e2e3", "e2e4", "f2f3", "f2f4", "g2g3", "g2g4", "h2h3", "h2h4",
            "b1a3", "b1c3", "g1f3", "g1h3")

        assertEquals(correctLegalMoves, legalmoves)
    }

    private fun assertBoard(boardPositions: List<List<Piece?>>, expectedBoardPositions: List<List<Piece?>>) {
        for(line in 0..7) {
            for(column in 0..7) {
                assertEquals(boardPositions[line][column]?.value, expectedBoardPositions[line][column]?.value)
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


    private fun mockGameEntity() = GameEntity(board = Board().toJsonString())

}