package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.dto.LegalMovements
import com.uff.br.xadruffbackend.dto.enum.Color
import com.uff.br.xadruffbackend.dto.piece.Bishop
import com.uff.br.xadruffbackend.dto.piece.King
import com.uff.br.xadruffbackend.dto.piece.Pawn
import com.uff.br.xadruffbackend.dto.piece.Queen
import com.uff.br.xadruffbackend.dto.piece.Rook
import com.uff.br.xadruffbackend.extension.BoardCastleExtensions.getCastleMovement
import com.uff.br.xadruffbackend.extension.BoardCastleExtensions.getFutureCastleKingPosition
import com.uff.br.xadruffbackend.extension.BoardCastleExtensions.getFutureCastleRookPosition
import com.uff.br.xadruffbackend.extension.BoardCastleExtensions.handleCastleMovements
import com.uff.br.xadruffbackend.extension.BoardCastleExtensions.hasPawnThreat
import com.uff.br.xadruffbackend.extension.BoardCastleExtensions.hasThreatInTheWay
import com.uff.br.xadruffbackend.extension.BoardCastleExtensions.isEmptyBetween
import com.uff.br.xadruffbackend.extension.BoardCastleExtensions.isPossibleToCastle
import com.uff.br.xadruffbackend.utils.buildEmptyBoard
import com.uff.br.xadruffbackend.utils.buildInitialBoard
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
@Suppress("LargeClass")
class BoardCastleExtensionsTest {

    @Test
    fun `should be possible to castle white king to left`() {
        val board = buildInitialBoard()
        board.position("b1").piece = null
        board.position("c1").piece = null
        board.position("d1").piece = null
        assertTrue(board.isPossibleToCastle(board.position("a1").piece as Rook, "e1", "a1"))
    }

    @Test
    fun `should be possible to castle white king to right`() {
        val board = buildInitialBoard()
        board.position("f1").piece = null
        board.position("g1").piece = null
        assertTrue(board.isPossibleToCastle(board.position("h1").piece as Rook, "e1", "h1"))
    }

    @Test
    fun `should be possible to castle black king to left`() {
        val board = buildInitialBoard()
        board.turnColor = Color.BLACK
        board.position("b8").piece = null
        board.position("c8").piece = null
        board.position("d8").piece = null
        assertTrue(board.isPossibleToCastle(board.position("a8").piece as Rook, "e8", "a8"))
    }

    @Test
    fun `should be possible to castle black king to right`() {
        val board = buildInitialBoard()
        board.turnColor = Color.BLACK
        board.position("f8").piece = null
        board.position("g8").piece = null
        assertTrue(board.isPossibleToCastle(board.position("h8").piece as Rook, "e8", "h8"))
    }

    @Test
    fun `should not be possible to castle black king to right when king in check`() {
        val board = buildInitialBoard()
        board.turnColor = Color.BLACK
        board.position("f8").piece = null
        board.position("g8").piece = null
        board.position("f7").piece = null
        board.position("h5").piece = Bishop(Color.WHITE)
        assertFalse(board.isPossibleToCastle(board.position("h8").piece as Rook, "e8", "h8"))
    }

    @Test
    fun `should not be possible to castle black king to left when king in check`() {
        val board = buildInitialBoard()
        board.turnColor = Color.BLACK
        board.position("b8").piece = null
        board.position("c8").piece = null
        board.position("d8").piece = null
        board.position("d7").piece = null
        board.position("b5").piece = Bishop(Color.WHITE)
        assertFalse(board.isPossibleToCastle(board.position("a8").piece as Rook, "e8", "a8"))
    }

    @Test
    fun `should not be possible to castle white king to right when king in check`() {
        val board = buildInitialBoard()
        board.position("f1").piece = null
        board.position("g1").piece = null
        board.position("f2").piece = null
        board.position("h4").piece = Bishop(Color.BLACK)
        assertFalse(board.isPossibleToCastle(board.position("h1").piece as Rook, "e1", "h1"))
    }

    @Test
    fun `should not be possible to castle white king to left when king in check`() {
        val board = buildInitialBoard()
        board.position("b1").piece = null
        board.position("c1").piece = null
        board.position("d1").piece = null
        board.position("d2").piece = null
        board.position("b4").piece = Bishop(Color.BLACK)
        assertFalse(board.isPossibleToCastle(board.position("a1").piece as Rook, "e1", "a1"))
    }

    @Test
    fun `should return true to is empty between king and rook`() {
        val board = buildInitialBoard()
        board.position("b1").piece = null
        board.position("c1").piece = null
        board.position("d1").piece = null
        board.position("f1").piece = null
        board.position("g1").piece = null
        board.position("b8").piece = null
        board.position("c8").piece = null
        board.position("d8").piece = null
        board.position("f8").piece = null
        board.position("g8").piece = null

        assertTrue(
            board.isEmptyBetween(
                kingPosition = board.position("e1"),
                rookPosition = board.position("a1")
            )
        )
        assertTrue(
            board.isEmptyBetween(
                kingPosition = board.position("e1"),
                rookPosition = board.position("h1")
            )
        )
        assertTrue(
            board.isEmptyBetween(
                kingPosition = board.position("e8"),
                rookPosition = board.position("a8")
            )
        )
        assertTrue(
            board.isEmptyBetween(
                kingPosition = board.position("e8"),
                rookPosition = board.position("a8")
            )
        )
    }

    @Test
    fun `should return false to has threat in the way with initial board`() {
        val board = buildInitialBoard()

        assertFalse(board.hasThreatInTheWay("a1"))
        assertFalse(board.hasThreatInTheWay("a8"))
        assertFalse(board.hasThreatInTheWay("h1"))
        assertFalse(board.hasThreatInTheWay("h8"))
    }

    @Test
    fun `should return true to has threat in the way for white king in left castle with empty board with changes`() {
        val board = buildEmptyBoard()
        board.turnColor = Color.BLACK
        board.position("a1").piece = Rook(Color.WHITE)
        board.position("h1").piece = Rook(Color.WHITE)
        board.position("e1").piece = King(Color.WHITE)
        board.position("b8").piece = Queen(Color.BLACK)
        assertTrue(board.hasThreatInTheWay("a1"))
    }

    @Test
    fun `should return true to has threat in the way for white king in right castle with empty board with changes`() {
        val board = buildEmptyBoard()
        board.position("h1").piece = Rook(Color.WHITE)
        board.position("e1").piece = King(Color.WHITE)
        board.position("f8").piece = Queen(Color.BLACK)
        assertTrue(board.hasThreatInTheWay("h1"))
    }

    @Test
    fun `should return true to has threat in the way for black king in left castle with empty board with changes`() {
        val board = buildEmptyBoard()
        board.position("a8").piece = Rook(Color.BLACK)
        board.position("h8").piece = Rook(Color.BLACK)
        board.position("e8").piece = King(Color.BLACK)
        board.position("b1").piece = Queen(Color.WHITE)
        assertTrue(board.hasThreatInTheWay("a8"))
    }

    @Test
    fun `should return true to has threat in the way for black king in right castle with empty board with changes`() {
        val board = buildEmptyBoard()
        board.turnColor = Color.BLACK
        board.position("h8").piece = Rook(Color.BLACK)
        board.position("e8").piece = King(Color.BLACK)
        board.position("f1").piece = Queen(Color.WHITE)
        assertTrue(board.hasThreatInTheWay("h8"))
    }

    @Test
    fun `should get future castle king position`() {
        assertEquals("c1", getFutureCastleKingPosition("a1"))
        assertEquals("g1", getFutureCastleKingPosition("h1"))
        assertEquals("c8", getFutureCastleKingPosition("a8"))
        assertEquals("g8", getFutureCastleKingPosition("h8"))
    }

    @Test
    fun `should get future castle rook position`() {
        assertEquals("d1", getFutureCastleRookPosition("a1"))
        assertEquals("f1", getFutureCastleRookPosition("h1"))
        assertEquals("d8", getFutureCastleRookPosition("a8"))
        assertEquals("f8", getFutureCastleRookPosition("h8"))
    }

    @Test
    fun `should get castle movements for white king with empty board`() {
        val board = buildEmptyBoard()
        board.position("a1").piece = Rook(Color.WHITE)
        board.position("h1").piece = Rook(Color.WHITE)
        board.position("e1").piece = King(Color.WHITE)
        assertEquals("e1c1Oa1d1", board.getCastleMovement("e1", "a1"))
        assertEquals("e1g1Oh1f1", board.getCastleMovement("e1", "h1"))
    }

    @Test
    fun `should get castle movements for black king with empty board`() {
        val board = buildEmptyBoard()
        board.position("a8").piece = Rook(Color.WHITE)
        board.position("h8").piece = Rook(Color.WHITE)
        board.position("e8").piece = King(Color.WHITE)
        assertEquals("e8c8Oa8d8", board.getCastleMovement("e8", "a8"))
        assertEquals("e8g8Oh8f8", board.getCastleMovement("e8", "h8"))
    }

    @Test
    fun `should return false to has pawn threat with initial board`() {
        val board = buildInitialBoard()
        assertFalse(board.hasPawnThreat('a'))
        assertFalse(board.hasPawnThreat('h'))
        board.changeTurn()
        assertFalse(board.hasPawnThreat('a'))
        assertFalse(board.hasPawnThreat('h'))
    }

    @Test
    fun `should return true to has pawn threat with empty board with changes for white castle`() {
        val board = buildEmptyBoard()
        board.position("a1").piece = Rook(Color.WHITE)
        board.position("h1").piece = Rook(Color.WHITE)
        board.position("e1").piece = King(Color.WHITE)
        board.position("c2").piece = Pawn(Color.BLACK)
        board.position("g2").piece = Pawn(Color.BLACK)
        val fakeBoard = board.deepCopy()
        fakeBoard.changeTurn()
        assertTrue(fakeBoard.hasPawnThreat('a'))
        assertTrue(fakeBoard.hasPawnThreat('h'))
    }

    @Test
    fun `should return true to has pawn threat with empty board with changes for black castle`() {
        val board = buildEmptyBoard()
        board.changeTurn()
        board.position("a8").piece = Rook(Color.BLACK)
        board.position("h8").piece = Rook(Color.BLACK)
        board.position("e8").piece = King(Color.BLACK)
        board.position("c7").piece = Pawn(Color.WHITE)
        board.position("g7").piece = Pawn(Color.WHITE)
        val fakeBoard = board.deepCopy()
        fakeBoard.changeTurn()
        assertTrue(fakeBoard.hasPawnThreat('a'))
        assertTrue(fakeBoard.hasPawnThreat('h'))
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
        assertEquals(listOf("e1c1Oa1d1", "e1g1Oh1f1"), legalMovements.movements)
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
        assertEquals(listOf("e8c8Oa8d8", "e8g8Oh8f8"), legalMovements.movements)
    }

    @Test
    fun `should not calculate left castle movements for white king because has piece between`() {
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
    fun `should not calculate right castle movements for white king because has piece between`() {
        listOf("f1", "g1").forEach {
            val board = buildInitialBoard()
            board.position(it).piece = null
            val legalMovements = LegalMovements()
            board.handleCastleMovements(position = board.position("e1"), legalMovements)
            assertEquals(listOf<String>(), legalMovements.movements)
        }
    }

    @Test
    fun `should not calculate left castle movements for black king because has piece between`() {
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
    fun `should not calculate right castle movements for black king because has piece between`() {
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
        assertEquals(listOf("e8g8Oh8f8"), legalMovements.movements)
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
        assertEquals(listOf("e8c8Oa8d8"), legalMovements.movements)
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
        assertEquals(listOf("e1g1Oh1f1"), legalMovements.movements)
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
        assertEquals(listOf("e1c1Oa1d1"), legalMovements.movements)
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
        assertEquals(listOf("e1g1Oh1f1"), legalMovements.movements)
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
        assertEquals(listOf("e1c1Oa1d1"), legalMovements.movements)
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
        assertEquals(listOf("e1g1Oh1f1"), legalMovements.movements)
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
        assertEquals(listOf("e1c1Oa1d1"), legalMovements.movements)
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
        assertEquals(listOf("e1c1Oa1d1", "e1g1Oh1f1"), legalMovements.movements)
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
        assertEquals(listOf("e8g8Oh8f8"), legalMovements.movements)
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
        assertEquals(listOf("e8c8Oa8d8"), legalMovements.movements)
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
        assertEquals(listOf("e8g8Oh8f8"), legalMovements.movements)
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
        assertEquals(listOf("e8c8Oa8d8"), legalMovements.movements)
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
    fun `should calculate left castle movements for black king even if has pawn in front of the castle`() {
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
        assertEquals(listOf("e8c8Oa8d8", "e8g8Oh8f8"), legalMovements.movements)
    }
}
