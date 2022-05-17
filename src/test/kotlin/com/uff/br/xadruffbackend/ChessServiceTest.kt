package com.uff.br.xadruffbackend

import com.uff.br.xadruffbackend.calculator.LegalMovementsCalculator
import com.uff.br.xadruffbackend.model.enum.StartsBy
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.GameEntity
import com.uff.br.xadruffbackend.model.Position
import com.uff.br.xadruffbackend.model.toJsonString
import com.uff.br.xadruffbackend.model.toStringPositions
import com.uff.br.xadruffbackend.utils.buildInitialBoard
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ChessServiceTest{

    private val initialBoard: Board = buildInitialBoard()
    private val chessRepository = mockk<ChessRepository>()
    private val legalMovementsCalculator = LegalMovementsCalculator()
    private val chessService: ChessService = ChessService(chessRepository, legalMovementsCalculator)

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