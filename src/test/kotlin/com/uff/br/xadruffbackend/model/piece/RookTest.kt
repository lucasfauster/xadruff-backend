package com.uff.br.xadruffbackend.model.piece

import com.uff.br.xadruffbackend.model.LegalMovements
import com.uff.br.xadruffbackend.utils.buildInitialBoard
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RookTest {

    @Test
    fun `should calculate movement from white left rook with initial board`(){
        val board = buildInitialBoard()
        val legalMovements = LegalMovements(mutableListOf())
        val leftRook = board.positions[7][0].piece!!
        leftRook.calculateLegalMovements(7, 0, board, legalMovements)
        assertEquals(legalMovements.movements, mutableListOf<String>())
    }

    @Test
    fun `should calculate movement from white right rook with initial board`(){
        val board = buildInitialBoard()
        val legalMovements = LegalMovements(mutableListOf())
        val rightRook = board.positions[7][7].piece!!
        rightRook.calculateLegalMovements(7, 7, board, legalMovements)
        assertEquals(legalMovements.movements, mutableListOf<String>())
    }
}