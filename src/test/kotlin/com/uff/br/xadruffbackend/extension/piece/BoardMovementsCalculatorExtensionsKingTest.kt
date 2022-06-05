package com.uff.br.xadruffbackend.extension.piece

import com.uff.br.xadruffbackend.extension.BoardMovementsCalculatorExtensions.calculateLegalMovementsInPosition
import com.uff.br.xadruffbackend.extension.BoardMovementsCalculatorExtensions.handleCastleMovements
import com.uff.br.xadruffbackend.extension.position
import com.uff.br.xadruffbackend.model.LegalMovements
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.piece.King
import com.uff.br.xadruffbackend.model.piece.Pawn
import com.uff.br.xadruffbackend.model.piece.Rook
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
        board.turnColor = Color.BLACK
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

    @Test
    fun `should not calculate castle movements for white king when it has moved`() {
        val board = buildInitialBoard()
        board.position("b1").piece = null
        board.position("c1").piece = null
        board.position("d1").piece = null
        board.position("f1").piece = null
        board.position("g1").piece = null

        val whiteKing = King(Color.WHITE)
        whiteKing.hasMoved = true
        board.position("e1").piece = whiteKing

        val legalMovements = LegalMovements()
        board.handleCastleMovements(position = board.position("e1"), legalMovements)
        assertEquals(listOf<String>(), legalMovements.movements)
    }

    @Test
    fun `should not calculate castle movements for black king when it has moved`() {
        val board = buildInitialBoard()
        board.position("b8").piece = null
        board.position("c8").piece = null
        board.position("d8").piece = null
        board.position("f8").piece = null
        board.position("g8").piece = null

        val blackKing = King(Color.BLACK)
        blackKing.hasMoved = true
        board.position("e8").piece = blackKing

        val legalMovements = LegalMovements()
        board.handleCastleMovements(position = board.position("e8"), legalMovements)
        assertEquals(listOf<String>(), legalMovements.movements)
    }

    @Test
    fun `should not calculate castle movements for black king when left rook has moved`() {
        val board = buildInitialBoard()
        board.position("b8").piece = null
        board.position("c8").piece = null
        board.position("d8").piece = null
        board.position("f8").piece = null
        board.position("g8").piece = null

        val blackLeftRook = Rook(Color.BLACK)
        blackLeftRook.hasMoved = true
        board.position("a8").piece = blackLeftRook
        board.turnColor = Color.BLACK

        val legalMovements = LegalMovements()
        board.handleCastleMovements(position = board.position("e8"), legalMovements)
        assertEquals(listOf("e8g8"), legalMovements.movements)
    }

    @Test
    fun `should not calculate castle movements for black king when right rook has moved`() {
        val board = buildInitialBoard()
        board.position("b8").piece = null
        board.position("c8").piece = null
        board.position("d8").piece = null
        board.position("f8").piece = null
        board.position("g8").piece = null

        val blackRightRook = Rook(Color.BLACK)
        blackRightRook.hasMoved = true
        board.position("h8").piece = blackRightRook
        board.turnColor = Color.BLACK

        val legalMovements = LegalMovements()
        board.handleCastleMovements(position = board.position("e8"), legalMovements)
        assertEquals(listOf("e8c8"), legalMovements.movements)
    }

    @Test
    fun `should not calculate castle movements for white king when left rook has moved`() {
        val board = buildInitialBoard()
        board.position("b1").piece = null
        board.position("c1").piece = null
        board.position("d1").piece = null
        board.position("f1").piece = null
        board.position("g1").piece = null

        val whiteLeftRook = Rook(Color.WHITE)
        whiteLeftRook.hasMoved = true
        board.position("a1").piece = whiteLeftRook

        val legalMovements = LegalMovements()
        board.handleCastleMovements(position = board.position("e1"), legalMovements)
        assertEquals(listOf("e1g1"), legalMovements.movements)
    }

    @Test
    fun `should not calculate castle movements for white king when right rook has moved`() {
        val board = buildInitialBoard()
        board.position("b1").piece = null
        board.position("c1").piece = null
        board.position("d1").piece = null
        board.position("f1").piece = null
        board.position("g1").piece = null

        val whiteLeftRook = Rook(Color.WHITE)
        whiteLeftRook.hasMoved = true
        board.position("h1").piece = whiteLeftRook

        val legalMovements = LegalMovements()
        board.handleCastleMovements(position = board.position("e1"), legalMovements)
        assertEquals(listOf("e1c1"), legalMovements.movements)
    }

    @Test
    fun `should not calculate castle movements for white king when has threat in both ways`() {
        val board = buildInitialBoard()
        board.position("b1").piece = null
        board.position("c1").piece = null
        board.position("d1").piece = null
        board.position("f1").piece = null
        board.position("g1").piece = null
        board.position("d2").piece = null
        board.position("b2").piece = null
        board.position("f2").piece = null
        board.position("d3").piece = Rook(Color.BLACK)
        board.position("b3").piece = Rook(Color.BLACK)
        board.position("f3").piece = Rook(Color.BLACK)

        val legalMovements = LegalMovements()
        board.handleCastleMovements(position = board.position("e1"), legalMovements)
        assertEquals(emptyList<String>(), legalMovements.movements)
    }

    @Test
    fun `should not calculate left castle movements for white king when has threat in left way`() {
        val board = buildInitialBoard()
        board.position("b1").piece = null
        board.position("c1").piece = null
        board.position("d1").piece = null
        board.position("f1").piece = null
        board.position("g1").piece = null
        board.position("d2").piece = null
        board.position("b2").piece = null
        board.position("d3").piece = Rook(Color.BLACK)
        board.position("b3").piece = Rook(Color.BLACK)

        val legalMovements = LegalMovements()
        board.handleCastleMovements(position = board.position("e1"), legalMovements)
        assertEquals(listOf("e1g1"), legalMovements.movements)
    }

    @Test
    fun `should not calculate right castle movements for white king when has threat in right way`() {
        val board = buildInitialBoard()
        board.position("b1").piece = null
        board.position("c1").piece = null
        board.position("d1").piece = null
        board.position("f1").piece = null
        board.position("g1").piece = null
        board.position("f2").piece = null
        board.position("f3").piece = Rook(Color.BLACK)

        val legalMovements = LegalMovements()
        board.handleCastleMovements(position = board.position("e1"), legalMovements)
        assertEquals(listOf("e1c1"), legalMovements.movements)
    }

    @Test
    fun `should not calculate left castle movements for white king when has pawn threat in left way`() {
        val board = buildInitialBoard()
        board.position("b1").piece = null
        board.position("c1").piece = null
        board.position("d1").piece = null
        board.position("f1").piece = null
        board.position("g1").piece = null
        board.position("c2").piece = Pawn(Color.BLACK)

        val legalMovements = LegalMovements()
        board.handleCastleMovements(position = board.position("e1"), legalMovements)
        assertEquals(listOf("e1g1"), legalMovements.movements)
    }

    @Test
    fun `should not calculate right castle movements for white king when has pawn threat in right way`() {
        val board = buildInitialBoard()
        board.position("b1").piece = null
        board.position("c1").piece = null
        board.position("d1").piece = null
        board.position("f1").piece = null
        board.position("g1").piece = null
        board.position("g2").piece = Pawn(Color.BLACK)

        val legalMovements = LegalMovements()
        board.handleCastleMovements(position = board.position("e1"), legalMovements)
        assertEquals(listOf("e1c1"), legalMovements.movements)
    }

    @Test
    fun `should not calculate castle movements for white king when has pawn threat in both ways`() {
        val board = buildInitialBoard()
        board.position("b1").piece = null
        board.position("c1").piece = null
        board.position("d1").piece = null
        board.position("f1").piece = null
        board.position("g1").piece = null
        board.position("e2").piece = Pawn(Color.BLACK)

        val legalMovements = LegalMovements()
        board.handleCastleMovements(position = board.position("e1"), legalMovements)
        assertEquals(emptyList<String>(), legalMovements.movements)
    }

    @Test
    fun `should calculate left castle movements for white king even if have pawn in front of the castle`() {
        val board = buildInitialBoard()
        board.position("b1").piece = null
        board.position("c1").piece = null
        board.position("d1").piece = null
        board.position("f1").piece = null
        board.position("g1").piece = null
        board.position("a2").piece = Pawn(Color.BLACK)

        val legalMovements = LegalMovements()
        board.handleCastleMovements(position = board.position("e1"), legalMovements)
        assertEquals(listOf("e1c1", "e1g1"), legalMovements.movements)
    }

    @Test
    fun `should not calculate castle movements for black king when has threat in both ways`() {
        val board = buildInitialBoard()
        board.position("b8").piece = null
        board.position("c8").piece = null
        board.position("d8").piece = null
        board.position("f8").piece = null
        board.position("g8").piece = null
        board.position("d7").piece = null
        board.position("f7").piece = null
        board.position("d6").piece = Rook(Color.WHITE)
        board.position("f6").piece = Rook(Color.WHITE)

        board.turnColor = Color.BLACK
        val legalMovements = LegalMovements()
        board.handleCastleMovements(position = board.position("e8"), legalMovements)
        assertEquals(emptyList<String>(), legalMovements.movements)
    }

    @Test
    fun `should not calculate left castle movements for black king when has threat in left way`() {
        val board = buildInitialBoard()
        board.position("b8").piece = null
        board.position("c8").piece = null
        board.position("d8").piece = null
        board.position("f8").piece = null
        board.position("g8").piece = null
        board.position("d7").piece = null
        board.position("d6").piece = Rook(Color.WHITE)

        board.turnColor = Color.BLACK
        val legalMovements = LegalMovements()
        board.handleCastleMovements(position = board.position("e8"), legalMovements)
        assertEquals(listOf("e8g8"), legalMovements.movements)
    }

    @Test
    fun `should not calculate right castle movements for black king when has threat in right way`() {
        val board = buildInitialBoard()
        board.position("b8").piece = null
        board.position("c8").piece = null
        board.position("d8").piece = null
        board.position("f8").piece = null
        board.position("g8").piece = null
        board.position("f7").piece = null
        board.position("f6").piece = Rook(Color.WHITE)

        board.turnColor = Color.BLACK
        val legalMovements = LegalMovements()
        board.handleCastleMovements(position = board.position("e8"), legalMovements)
        assertEquals(listOf("e8c8"), legalMovements.movements)
    }

    @Test
    fun `should not calculate left castle movements for black king when has pawn threat in left way`() {
        val board = buildInitialBoard()
        board.position("b8").piece = null
        board.position("c8").piece = null
        board.position("d8").piece = null
        board.position("f8").piece = null
        board.position("g8").piece = null
        board.position("c7").piece = Pawn(Color.WHITE)

        board.turnColor = Color.BLACK
        val legalMovements = LegalMovements()
        board.handleCastleMovements(position = board.position("e8"), legalMovements)
        assertEquals(listOf("e8g8"), legalMovements.movements)
    }

    @Test
    fun `should not calculate right castle movements for black king when has pawn threat in right way`() {
        val board = buildInitialBoard()
        board.position("b8").piece = null
        board.position("c8").piece = null
        board.position("d8").piece = null
        board.position("f8").piece = null
        board.position("g8").piece = null
        board.position("g7").piece = Pawn(Color.WHITE)

        board.turnColor = Color.BLACK
        val legalMovements = LegalMovements()
        board.handleCastleMovements(position = board.position("e8"), legalMovements)
        assertEquals(listOf("e8c8"), legalMovements.movements)
    }

    @Test
    fun `should not calculate castle movements for black king when has pawn threat in both ways`() {
        val board = buildInitialBoard()
        board.position("b8").piece = null
        board.position("c8").piece = null
        board.position("d8").piece = null
        board.position("f8").piece = null
        board.position("g8").piece = null
        board.position("e7").piece = Pawn(Color.WHITE)

        board.turnColor = Color.BLACK
        val legalMovements = LegalMovements()
        board.handleCastleMovements(position = board.position("e8"), legalMovements)
        assertEquals(emptyList<String>(), legalMovements.movements)
    }

    @Test
    fun `should calculate left castle movements for black king even if have pawn in front of the castle`() {
        val board = buildInitialBoard()
        board.position("b8").piece = null
        board.position("c8").piece = null
        board.position("d8").piece = null
        board.position("f8").piece = null
        board.position("g8").piece = null
        board.position("a7").piece = Pawn(Color.WHITE)

        board.turnColor = Color.BLACK
        val legalMovements = LegalMovements()
        board.handleCastleMovements(position = board.position("e8"), legalMovements)
        assertEquals(listOf("e8c8", "e8g8"), legalMovements.movements)
    }
}
