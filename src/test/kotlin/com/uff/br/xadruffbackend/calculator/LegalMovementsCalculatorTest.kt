package com.uff.br.xadruffbackend.calculator

import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.utils.buildInitialBoard
import org.junit.jupiter.api.Test

class LegalMovementsCalculatorTest {

    @Test
    fun `should return initial legal movements for white player`(){
        val board = buildInitialBoard()
        val legalMovementsCalculator = LegalMovementsCalculator(board)
        legalMovementsCalculator.calculatePseudoLegalMoves(Color.WHITE)
    }
}