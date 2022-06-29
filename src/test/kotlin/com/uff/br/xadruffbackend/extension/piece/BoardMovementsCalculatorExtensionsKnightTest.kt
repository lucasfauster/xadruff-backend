package com.uff.br.xadruffbackend.extension.piece

import com.uff.br.xadruffbackend.dto.enum.Color
import com.uff.br.xadruffbackend.dto.piece.Knight
import com.uff.br.xadruffbackend.extension.BoardMovementsCalculatorExtensions.calculateLegalMovementsInPosition
import com.uff.br.xadruffbackend.extension.position
import com.uff.br.xadruffbackend.utils.buildEmptyBoard
import com.uff.br.xadruffbackend.utils.buildInitialBoard
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class BoardMovementsCalculatorExtensionsKnightTest {
    @Test
    fun `should calculate movement from white left knight with initial board`() {
        val board = buildInitialBoard()
        val expectedMovements = listOf("b1a3", "b1c3")
        val legalMovements = runBlocking {
            board.calculateLegalMovementsInPosition(board.position("b1"))
        }
        assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should calculate movement from white right knight with initial board`() {
        val board = buildInitialBoard()
        val expectedMovements = listOf("g1f3", "g1h3")
        val legalMovements = runBlocking {
            board.calculateLegalMovementsInPosition(board.position("g1"))
        }
        assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should calculate movement from black left knight with initial board`() {
        val board = buildInitialBoard()
        val expectedMovements = listOf("b8a6", "b8c6")
        board.turnColor = Color.BLACK
        val legalMovements = runBlocking {
            board.calculateLegalMovementsInPosition(board.position("b8"))
        }
        assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should calculate movement from black right knight with initial board`() {
        val board = buildInitialBoard()
        val expectedMovements = listOf("g8f6", "g8h6")
        board.turnColor = Color.BLACK
        val legalMovements = runBlocking {
            board.calculateLegalMovementsInPosition(board.position("g8"))
        }
        assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should calculate movement from black knight with empty board`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf("e5d7", "e5f7", "e5d3", "e5f3", "e5c6", "e5c4", "e5g6", "e5g4")
        board.position("e5").piece = Knight(Color.BLACK)
        val legalMovements = runBlocking {
            board.calculateLegalMovementsInPosition(board.position("e5"))
        }
        assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should calculate movement from white knight with empty board`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf("e5d7", "e5f7", "e5d3", "e5f3", "e5c6", "e5c4", "e5g6", "e5g4")
        board.position("e5").piece = Knight(Color.WHITE)
        val legalMovements = runBlocking {
            board.calculateLegalMovementsInPosition(board.position("e5"))
        }
        assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should calculate movement from black knight with capture in all directions`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf("e5d7C", "e5f7C", "e5d3C", "e5f3C", "e5c6C", "e5c4C", "e5g6C", "e5g4C")
        board.position("e5").piece = Knight(Color.BLACK)
        board.position("c6").piece = Knight(Color.WHITE)
        board.position("c4").piece = Knight(Color.WHITE)
        board.position("d7").piece = Knight(Color.WHITE)
        board.position("d3").piece = Knight(Color.WHITE)
        board.position("f7").piece = Knight(Color.WHITE)
        board.position("f3").piece = Knight(Color.WHITE)
        board.position("g4").piece = Knight(Color.WHITE)
        board.position("g6").piece = Knight(Color.WHITE)
        val legalMovements = runBlocking {
            board.calculateLegalMovementsInPosition(board.position("e5"))
        }
        assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should calculate movement from white knight with capture in all directions`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf("e5d7C", "e5f7C", "e5d3C", "e5f3C", "e5c6C", "e5c4C", "e5g6C", "e5g4C")
        board.position("e5").piece = Knight(Color.WHITE)
        board.position("c6").piece = Knight(Color.BLACK)
        board.position("c4").piece = Knight(Color.BLACK)
        board.position("d7").piece = Knight(Color.BLACK)
        board.position("d3").piece = Knight(Color.BLACK)
        board.position("f7").piece = Knight(Color.BLACK)
        board.position("f3").piece = Knight(Color.BLACK)
        board.position("g4").piece = Knight(Color.BLACK)
        board.position("g6").piece = Knight(Color.BLACK)
        val legalMovements = runBlocking {
            board.calculateLegalMovementsInPosition(board.position("e5"))
        }
        assertEquals(expectedMovements, legalMovements.movements)
    }
}
