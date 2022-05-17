package com.uff.br.xadruffbackend.calculator

import com.uff.br.xadruffbackend.utils.buildInitialBoard
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class LegalMovementsCalculatorTest {

    private val legalMovementsCalculator = LegalMovementsCalculator()

    @Test
    fun `should return initial legal movements for white player`() {
        val board = buildInitialBoard()
        val actualMovements = legalMovementsCalculator.calculatePseudoLegalMoves(board)

        val expectedMovements = mutableListOf("a2a3", "a2a4", "b2b3", "b2b4", "c2c3", "c2c4", "d2d3", "d2d4",
            "e2e3", "e2e4", "f2f3", "f2f4", "g2g3", "g2g4", "h2h3", "h2h4",
            "b1a3", "b1c3", "g1f3", "g1h3")

        assertEquals(actualMovements.movements, expectedMovements)
    }
}