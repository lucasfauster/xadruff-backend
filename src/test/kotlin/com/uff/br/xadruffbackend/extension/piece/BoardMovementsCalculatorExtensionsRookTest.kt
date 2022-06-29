package com.uff.br.xadruffbackend.extension.piece

import com.uff.br.xadruffbackend.dto.enum.Color
import com.uff.br.xadruffbackend.dto.piece.Rook
import com.uff.br.xadruffbackend.extension.BoardMovementsCalculatorExtensions.calculateLegalMovementsInPosition
import com.uff.br.xadruffbackend.extension.position
import com.uff.br.xadruffbackend.utils.buildEmptyBoard
import com.uff.br.xadruffbackend.utils.buildInitialBoard
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class BoardMovementsCalculatorExtensionsRookTest {

    @Test
    fun `should calculate movement from white left rook with initial board`() {
        val board = buildInitialBoard()
        val legalMovements = runBlocking {
            board.calculateLegalMovementsInPosition(board.position("a1"))
        }
        assertEquals(mutableListOf<String>(), legalMovements.movements)
    }

    @Test
    fun `should calculate movement from white right rook with initial board`() {
        val board = buildInitialBoard()
        val legalMovements = runBlocking {
            board.calculateLegalMovementsInPosition(board.position("h1"))
        }
        assertEquals(mutableListOf<String>(), legalMovements.movements)
    }

    @Test
    fun `should calculate movement from black left rook with initial board`() {
        val board = buildInitialBoard()
        board.turnColor = Color.BLACK
        val legalMovements = runBlocking {
            board.calculateLegalMovementsInPosition(board.position("a8"))
        }
        assertEquals(mutableListOf<String>(), legalMovements.movements)
    }

    @Test
    fun `should calculate movement from black right rook with initial board`() {
        val board = buildInitialBoard()
        board.turnColor = Color.BLACK
        val legalMovements = runBlocking {
            board.calculateLegalMovementsInPosition(board.position("h8"))
        }
        assertEquals(mutableListOf<String>(), legalMovements.movements)
    }

    @Test
    fun `should calculate movement from black rook with empty board`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf(
            "e5e6", "e5e4", "e5d5", "e5f5", "e5e7", "e5e3", "e5c5", "e5g5", "e5e8",
            "e5e2", "e5b5", "e5h5", "e5e1", "e5a5"
        )
        board.position("e5").piece = Rook(Color.BLACK)
        val legalMovements = runBlocking {
            board.calculateLegalMovementsInPosition(board.position("e5"))
        }
        assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should calculate movement from white rook with empty board`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf(
            "e5e6", "e5e4", "e5d5", "e5f5", "e5e7", "e5e3", "e5c5", "e5g5", "e5e8",
            "e5e2", "e5b5", "e5h5", "e5e1", "e5a5"
        )
        board.position("e5").piece = Rook(Color.WHITE)
        val legalMovements = runBlocking {
            board.calculateLegalMovementsInPosition(board.position("e5"))
        }
        assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should calculate movement from black rook with capture in all directions`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf("e5e6C", "e5e4C", "e5d5C", "e5f5C")
        board.position("e5").piece = Rook(Color.BLACK)
        board.position("d5").piece = Rook(Color.WHITE)
        board.position("e6").piece = Rook(Color.WHITE)
        board.position("f5").piece = Rook(Color.WHITE)
        board.position("e4").piece = Rook(Color.WHITE)
        val legalMovements = runBlocking {
            board.calculateLegalMovementsInPosition(board.position("e5"))
        }
        assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should calculate movement from white rook with capture in all directions`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf("e5e6C", "e5e4C", "e5d5C", "e5f5C")
        board.position("e5").piece = Rook(Color.WHITE)
        board.position("d5").piece = Rook(Color.BLACK)
        board.position("e6").piece = Rook(Color.BLACK)
        board.position("f5").piece = Rook(Color.BLACK)
        board.position("e4").piece = Rook(Color.BLACK)
        val legalMovements = runBlocking {
            board.calculateLegalMovementsInPosition(board.position("e5"))
        }
        assertEquals(expectedMovements, legalMovements.movements)
    }
}
