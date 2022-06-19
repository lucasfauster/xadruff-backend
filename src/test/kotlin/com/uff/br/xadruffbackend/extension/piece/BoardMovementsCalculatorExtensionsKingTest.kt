package com.uff.br.xadruffbackend.extension.piece

import com.uff.br.xadruffbackend.extension.BoardMovementsCalculatorExtensions.calculateLegalMovementsInPosition
import com.uff.br.xadruffbackend.extension.position
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.piece.King
import com.uff.br.xadruffbackend.utils.buildEmptyBoard
import com.uff.br.xadruffbackend.utils.buildInitialBoard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class BoardMovementsCalculatorExtensionsKingTest {

    @Test
    fun `should calculate movement from white king with initial board`() {
        val board = buildInitialBoard()
        val legalMovements = runBlocking(Dispatchers.Default) {
            board.calculateLegalMovementsInPosition(board.position("e1"))
        }
        assertEquals(mutableListOf<String>(), legalMovements.movements)
    }

    @Test
    fun `should calculate movement from black king with initial board`() {
        val board = buildInitialBoard()
        board.turnColor = Color.BLACK
        val legalMovements = runBlocking(Dispatchers.Default) {
            board.calculateLegalMovementsInPosition(board.position("d8"))
        }
        assertEquals(mutableListOf<String>(), legalMovements.movements)
    }

    @Test
    fun `should calculate movement from black king with empty board`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf("e5f6", "e5d6", "e5d4", "e5f4", "e5e6", "e5e4", "e5d5", "e5f5")
        board.position("e5").piece = King(Color.BLACK)
        val legalMovements = runBlocking(Dispatchers.Default) {
            board.calculateLegalMovementsInPosition(board.position("e5"))
        }
        assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should calculate movement from white king with empty board`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf("e5f6", "e5d6", "e5d4", "e5f4", "e5e6", "e5e4", "e5d5", "e5f5")
        board.position("e5").piece = King(Color.WHITE)
        val legalMovements = runBlocking(Dispatchers.Default) {
            board.calculateLegalMovementsInPosition(board.position("e5"))
        }
        assertEquals(expectedMovements, legalMovements.movements)
    }
}
