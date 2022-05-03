package com.uff.br.xadruffbackend

import com.uff.br.xadruffbackend.model.Board
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ChessServiceTest{

    private val chessService: ChessService = ChessService()

    @Test
    fun `should create an initial board`(){
        val chessBoard = chessService.createInitialBoard()

        val positions = listOf(
            mutableListOf("r", "n", "b", "q", "k", "b", "n", "r"),
            mutableListOf("p", "p", "p", "p", "p", "p", "p", "p"),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("P", "P", "P", "P", "P", "P", "P", "P"),
            mutableListOf("R", "N", "B", "Q", "K", "B", "N", "R")
        )
        assertEquals(chessBoard, Board(positions = positions))
    }
}