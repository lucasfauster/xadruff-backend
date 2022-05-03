package com.uff.br.xadruffbackend

import com.uff.br.xadruffbackend.enum.StartsBy
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.GameEntity
import com.uff.br.xadruffbackend.model.toJsonString
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
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
        val board = chessService.createInitialBoard()
        assertEquals(board.positions, initialBoardPositions)
    }

    @Test
    fun `should create a new game with player playing first`(){
        val chessResponse = chessService.createNewGame(StartsBy.PLAYER)
        assertNotNull(chessResponse.legalMovements)
        assertNotNull(chessResponse.boardId)
        assertEquals(chessResponse.board.positions, initialBoardPositions)
    }

    @Test
    fun `should create a new game with AI playing first`(){
        val chessResponse = chessService.createNewGame(StartsBy.AI)
        assertNotNull(chessResponse.legalMovements)
        assertNotNull(chessResponse.boardId)
        // assertNotEquals(initialBoardPositions, chessResponse.board.positions) TODO após implementar movimentação da IA
    }


    private fun mockGameEntity() = GameEntity(board = Board(positions = initialBoardPositions).toJsonString())

}