package com.uff.br.xadruffbackend

import com.uff.br.xadruffbackend.model.Board
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ChessServiceTest{

    private val chessService: ChessService = ChessService()

    @Test
    fun `should create an initial board`(){
        val chessBoard = chessService.createInitialBoard()

        val positions = mutableListOf(
            "R", "N", "B", "Q", "K", "B", "N", "R",
            "P", "P", "P", "P", "P", "P", "P", "P",
            null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null,
            "p", "p", "p", "p", "p", "p", "p", "p",
            "r", "n", "b", "q", "k", "b", "n", "r"
        )
        assertEquals(chessBoard, Board(positions = positions))
    }
}