package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.extension.BoardMovementsCalculatorExtensions.buildAction
import com.uff.br.xadruffbackend.extension.BoardMovementsCalculatorExtensions.calculatePseudoLegalMoves
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.utils.buildInitialBoard
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


internal class BoardMovementsCalculatorExtensionsTest{

    @Test
    fun `should return initial legal movements for white player`() {
        val board = buildInitialBoard()
        val actualMovements = board.calculatePseudoLegalMoves()

        val expectedMovements = mutableListOf("a2a3", "a2a4", "b2b3", "b2b4", "c2c3", "c2c4", "d2d3", "d2d4",
            "e2e3", "e2e4", "f2f3", "f2f4", "g2g3", "g2g4", "h2h3", "h2h4",
            "b1a3", "b1c3", "g1f3", "g1h3")

        assertEquals(expectedMovements, actualMovements.movements)
    }

    @Test
    fun `should return C in buildAction for white piece if has enemy`(){
        val board = buildInitialBoard()
        val action = buildAction(board.positions[0][0], Color.WHITE)
        assertEquals("C", action)
    }

    @Test
    fun `should return empty string in buildAction for white piece if has ally`(){
        val board = buildInitialBoard()
        val action = buildAction(board.positions[7][7], Color.WHITE)
        assertEquals("", action)
    }

    @Test
    fun `should return empty string in buildAction for white piece if is empty`(){
        val board = buildInitialBoard()
        val action = buildAction(board.positions[5][5], Color.WHITE)
        assertEquals("", action)
    }

    @Test
    fun `should return C in buildAction for black piece if has enemy`(){
        val board = buildInitialBoard()
        val action = buildAction(board.positions[7][7], Color.BLACK)
        assertEquals("C", action)
    }

    @Test
    fun `should return empty string in buildAction for black piece if has ally`(){
        val board = buildInitialBoard()
        val action = buildAction(board.positions[0][0], Color.BLACK)
        assertEquals("", action)
    }

    @Test
    fun `should return empty string in buildAction for black piece if is empty`(){
        val board = buildInitialBoard()
        val action = buildAction(board.positions[5][5], Color.BLACK)
        assertEquals("", action)
    }
}