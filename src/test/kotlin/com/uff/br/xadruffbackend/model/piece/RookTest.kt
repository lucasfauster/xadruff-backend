package com.uff.br.xadruffbackend.model.piece

import com.uff.br.xadruffbackend.utils.buildInitialBoard
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RookTest {

    @Test
    fun `should calculate movement from white left rook with initial board`(){
        val board = buildInitialBoard()
        val leftRook = board.positions[7][0].piece!!
        val leftLegalMovements = leftRook.calculateLegalMovements(7, 0, board)
        assertEquals(leftLegalMovements, mutableListOf<String>())
    }

    @Test
    fun `should calculate movement from white right rook with initial board`(){
        val board = buildInitialBoard()
        val rightRook = board.positions[7][7].piece!!
        val rightLegalMovements = rightRook.calculateLegalMovements(7, 7, board)
        assertEquals(rightLegalMovements, mutableListOf<String>())
    }
}