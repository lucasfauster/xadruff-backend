package com.uff.br.xadruffbackend

import com.uff.br.xadruffbackend.enum.StartsBy
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.GameEntity
import com.uff.br.xadruffbackend.model.toJsonString
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ChessServiceTest{

    companion object {
        val initialBoardPositions = listOf(
            mutableListOf("r", "n", "b", "q", "k", "b", "n", "r"),
            mutableListOf("p", "p", "p", "p", "p", "p", "p", "p"),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("P", "P", "P", "P", "P", "P", "P", "P"),
            mutableListOf("R", "N", "B", "Q", "K", "B", "N", "R")
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

        assertEquals(gameEntity.getBoard().state, initialBoardPositions)
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

    }

    @Test
    fun `should create a new game with AI playing first`(){
        val chessResponse = chessService.createNewGame(StartsBy.AI)
        assertNotNull(chessResponse.legalMovements)
        assertNotNull(chessResponse.boardId)

        // assertNotEquals(initialBoardPositions, chessResponse.board.positions) TODO após implementar movimentação da IA
    }


    private fun mockGameEntity() = GameEntity(board = Board().toJsonString())

}