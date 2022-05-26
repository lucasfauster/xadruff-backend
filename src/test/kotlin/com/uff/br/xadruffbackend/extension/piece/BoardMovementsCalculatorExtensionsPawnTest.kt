package com.uff.br.xadruffbackend.extension.piece

import com.uff.br.xadruffbackend.extension.BoardMovementsCalculatorExtensions.calculateLegalMovementsInPosition
import com.uff.br.xadruffbackend.extension.addAll
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.piece.Pawn
import com.uff.br.xadruffbackend.utils.buildEmptyBoard
import com.uff.br.xadruffbackend.utils.buildInitialBoard
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class BoardMovementsCalculatorExtensionsPawnTest {

    @Test
    fun `should generate possible movements of white pawn in initial position`() {
        val board = buildInitialBoard()

        val legalMovements = board.calculateLegalMovementsInPosition(board.positions[6][4])

        val correctLegalMoves = mutableListOf("e2e3", "e2e4")

        assertEquals(correctLegalMoves, legalMovements.movements)
    }

    @Test
    fun `should generate possible movements of black pawn in initial position`() {
        val board = buildInitialBoard()

        board.turnColor = Color.BLACK
        val legalMovements = board.calculateLegalMovementsInPosition(board.positions[1][4])

        val correctLegalMoves = mutableListOf("e7e6", "e7e5")

        assertEquals(correctLegalMoves, legalMovements.movements)
    }

    @Test
    fun `should generate empty possible movements of pawn in initial position with piece in the way`() {
        val board = buildEmptyBoard()
        val whitePawn = Pawn(Color.WHITE)
        val blackPawn = Pawn(Color.BLACK)
        board.positions[6][4].piece = whitePawn
        board.positions[5][4].piece = blackPawn

        val legalMovements = board.calculateLegalMovementsInPosition(board.positions[6][4])
        board.turnColor = Color.BLACK
        legalMovements.addAll(board.calculateLegalMovementsInPosition(board.positions[5][4]))

        assertEquals(mutableListOf<String>(), legalMovements.movements)
    }

    @Test
    fun `should generate possible movements with capture and one position forward for pawn`() {
        val board = buildEmptyBoard()
        val whitePawn = Pawn(Color.WHITE)
        val blackPawn = Pawn(Color.BLACK)
        board.positions[5][4].piece = whitePawn
        board.positions[4][3].piece = blackPawn

        val legalMovements = board.calculateLegalMovementsInPosition(board.positions[5][4])

        val correctLegalMoves = listOf("e3e4", "e3d4C")

        assertEquals(correctLegalMoves, legalMovements.movements)
    }
}
