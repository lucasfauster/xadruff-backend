package com.uff.br.xadruffbackend.extension.piece

import com.uff.br.xadruffbackend.extension.BoardMovementsCalculatorExtensions.calculateLegalMovementsInPosition
import com.uff.br.xadruffbackend.extension.BoardMovementsCalculatorExtensions.handleCastleMovements
import com.uff.br.xadruffbackend.extension.position
import com.uff.br.xadruffbackend.model.LegalMovements
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.piece.King
import com.uff.br.xadruffbackend.model.piece.Pawn
import com.uff.br.xadruffbackend.utils.buildEmptyBoard
import com.uff.br.xadruffbackend.utils.buildInitialBoard
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class BoardMovementsCalculatorExtensionsKingTest {

    @Test
    fun `should calculate movement from white king with initial board`() {
        val board = buildInitialBoard()
        val legalMovements = board.calculateLegalMovementsInPosition(board.position("e1"))
        assertEquals(mutableListOf<String>(), legalMovements.movements)
    }

    @Test
    fun `should calculate movement from black king with initial board`() {
        val board = buildInitialBoard()
        board.turnColor = Color.BLACK
        val legalMovements = board.calculateLegalMovementsInPosition(board.position("d8"))
        assertEquals(mutableListOf<String>(), legalMovements.movements)
    }

    @Test
    fun `should calculate movement from black king with empty board`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf("e5f6", "e5d6", "e5d4", "e5f4", "e5e6", "e5e4", "e5d5", "e5f5")
        board.position("e5").piece = King(Color.BLACK)
        val legalMovements = board.calculateLegalMovementsInPosition(board.position("e5"))
        assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should calculate movement from white king with empty board`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf("e5f6", "e5d6", "e5d4", "e5f4", "e5e6", "e5e4", "e5d5", "e5f5")
        board.position("e5").piece = King(Color.WHITE)
        val legalMovements = board.calculateLegalMovementsInPosition(board.position("e5"))
        assertEquals(expectedMovements, legalMovements.movements)
    }

    // TODO - Tirar ignore após criação do cheque
//    @Test
    fun `should calculate movement from white king with capture in six directions`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf("e5f6C", "e5d6C", "e5d4C", "e5f4C", "e5d5C", "e5f5C")
        board.position("e5").piece = King(Color.WHITE)
        board.position("d4").piece = Pawn(Color.BLACK)
        board.position("f4").piece = Pawn(Color.BLACK)
        board.position("d6").piece = Pawn(Color.BLACK)
        board.position("f6").piece = Pawn(Color.BLACK)
        board.position("d5").piece = Pawn(Color.BLACK)
        board.position("f5").piece = Pawn(Color.BLACK)
        val legalMovements = board.calculateLegalMovementsInPosition(board.position("e5"))
        assertEquals(expectedMovements, legalMovements.movements)
    }

    // TODO - Tirar ignore após criação do cheque
//    @Test
    fun `should calculate movement from white king with capture in two directions`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf("e5f6", "e5d6", "e5d4", "e5f4", "e5e6C", "e5e4C")
        board.position("e5").piece = King(Color.WHITE)
        board.position("e4").piece = Pawn(Color.BLACK)
        board.position("e6").piece = Pawn(Color.BLACK)
        val legalMovements = board.calculateLegalMovementsInPosition(board.position("e5"))
        assertEquals(expectedMovements, legalMovements.movements)
    }

    // TODO - Tirar ignore após criação do cheque
//    @Test
    fun `should calculate movement from black king with capture in six directions`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf("e5f6C", "e5d6C", "e5d4C", "e5f4C", "e5d5C", "e5f5C")
        board.position("e5").piece = King(Color.BLACK)
        board.position("d4").piece = Pawn(Color.WHITE)
        board.position("f4").piece = Pawn(Color.WHITE)
        board.position("d6").piece = Pawn(Color.WHITE)
        board.position("f6").piece = Pawn(Color.WHITE)
        board.position("d5").piece = Pawn(Color.WHITE)
        board.position("f5").piece = Pawn(Color.WHITE)
        val legalMovements = board.calculateLegalMovementsInPosition(board.position("e5"))
        assertEquals(expectedMovements, legalMovements.movements)
    }

    // TODO - Tirar ignore após criação do cheque
//    @Test
    fun `should calculate movement from black king with capture in two directions`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf("e5f6", "e5d6", "e5d4", "e5f4", "e5e6C", "e5e4C")
        board.position("e5").piece = King(Color.BLACK)
        board.position("e4").piece = Pawn(Color.WHITE)
        board.position("e6").piece = Pawn(Color.WHITE)
        val legalMovements = board.calculateLegalMovementsInPosition(board.position("e5"))
        assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should calculate castle movements for white king`() {
        val board = buildInitialBoard()
        board.position("b1").piece = null
        board.position("c1").piece = null
        board.position("d1").piece = null
        board.position("f1").piece = null
        board.position("g1").piece = null
        val legalMovements = LegalMovements()
        board.handleCastleMovements(position = board.position("e1"), legalMovements)
        assertEquals(listOf("e1c1", "e1g1"), legalMovements.movements)
    }

    @Test
    fun `should calculate castle movements for black king`() {
        val board = buildInitialBoard()
        board.position("b8").piece = null
        board.position("c8").piece = null
        board.position("d8").piece = null
        board.position("f8").piece = null
        board.position("g8").piece = null
        val legalMovements = LegalMovements()
        board.handleCastleMovements(position = board.position("e8"), legalMovements)
        assertEquals(listOf("e8c8", "e8g8"), legalMovements.movements)
    }

    @Test
    fun `should not calculate left castle movements for white king`() {
        listOf(
            listOf("b1", "c1"),
            listOf("c1", "d1"),
            listOf("b1", "d1"),
            listOf("b1"),
            listOf("c1"),
            listOf("d1")
        ).forEach { list ->
            val board = buildInitialBoard()
            list.forEach {
                board.position(it).piece = null
            }
            val legalMovements = LegalMovements()
            board.handleCastleMovements(position = board.position("e1"), legalMovements)
            assertEquals(listOf<String>(), legalMovements.movements)
        }
    }

    @Test
    fun `should not calculate right castle movements for white king`() {
        listOf("f1", "g1").forEach {
            val board = buildInitialBoard()
            board.position(it).piece = null
            val legalMovements = LegalMovements()
            board.handleCastleMovements(position = board.position("e1"), legalMovements)
            assertEquals(listOf<String>(), legalMovements.movements)
        }
    }

    @Test
    fun `should not calculate left castle movements for black king`() {
        listOf(
            listOf("b8", "c8"),
            listOf("c8", "d8"),
            listOf("b8", "d8"),
            listOf("b8"),
            listOf("c8"),
            listOf("d8")
        ).forEach { list ->
            val board = buildInitialBoard()
            list.forEach {
                board.position(it).piece = null
            }
            val legalMovements = LegalMovements()
            board.handleCastleMovements(position = board.position("e8"), legalMovements)
            assertEquals(listOf<String>(), legalMovements.movements)
        }
    }

    @Test
    fun `should not calculate right castle movements for black king`() {
        listOf("f8", "g8").forEach {
            val board = buildInitialBoard()
            board.position(it).piece = null
            val legalMovements = LegalMovements()
            board.handleCastleMovements(position = board.position("e8"), legalMovements)
            assertEquals(listOf<String>(), legalMovements.movements)
        }
    }
}
