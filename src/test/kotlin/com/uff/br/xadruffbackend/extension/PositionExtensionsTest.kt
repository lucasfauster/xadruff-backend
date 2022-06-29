package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.model.Position
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.piece.Ghost
import com.uff.br.xadruffbackend.model.piece.Pawn
import com.uff.br.xadruffbackend.model.piece.Queen
import com.uff.br.xadruffbackend.utils.buildInitialBoard
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

internal class PositionExtensionsTest {

    @Test
    fun `should transform position to chess position`() {
        val chessPositions = listOf(
            listOf("a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8"),
            listOf("a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7"),
            listOf("a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6"),
            listOf("a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5"),
            listOf("a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4"),
            listOf("a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3"),
            listOf("a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2"),
            listOf("a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1")
        )

        for (row in (0..7)) {
            for (column in (0..7)) {
                val position = Position(row, column)
                val stringPosition = position.toChessPosition()
                assertEquals(chessPositions[row][column], stringPosition)
            }
        }
    }

    @Test
    fun `should return true in hasAllyPiece if has an ally`() {
        val board = buildInitialBoard()
        val hasAllyPiece = board.position("h1").hasAllyPiece(Color.WHITE)
        assert(hasAllyPiece)
    }

    @Test
    fun `should return false in hasAllyPiece if has an enemy`() {
        val board = buildInitialBoard()
        val hasAllyPiece = board.position("a8").hasAllyPiece(Color.WHITE)
        assertFalse(hasAllyPiece)
    }

    @Test
    fun `should return false in hasAllyPiece if is empty`() {
        val board = buildInitialBoard()
        val hasAllyPiece = board.position("f3").hasAllyPiece(Color.WHITE)
        assertFalse(hasAllyPiece)
    }

    @Test
    fun `should return false in hasAllyPiece if is a ghost ally`() {
        val board = buildInitialBoard()
        val ghost = Ghost(Color.WHITE)
        board.position("f3").piece = ghost
        val hasAllyPiece = board.position("f3").hasAllyPiece(Color.WHITE)
        assertFalse(hasAllyPiece)
    }

    @Test
    fun `should return false in hasAllyPiece if is a ghost enemy`() {
        val board = buildInitialBoard()
        val ghost = Ghost(Color.BLACK)
        board.position("f3").piece = ghost
        val hasAllyPiece = board.position("f3").hasAllyPiece(Color.WHITE)
        assertFalse(hasAllyPiece)
    }

    @Test
    fun `should return false in hasEnemyPiece if has an ally`() {
        val board = buildInitialBoard()
        val piece = Pawn(Color.WHITE)
        val hasEnemyPiece = board.position("h1").hasEnemyPiece(piece)
        assertFalse(hasEnemyPiece)
    }

    @Test
    fun `should return true in hasEnemyPiece if has an enemy`() {
        val board = buildInitialBoard()
        val piece = Pawn(Color.WHITE)
        val hasEnemyPiece = board.position("a8").hasEnemyPiece(piece)
        assert(hasEnemyPiece)
    }

    @Test
    fun `should return true in hasEnemyPiece if has a black ghost enemy`() {
        val board = buildInitialBoard()
        val piece = Pawn(Color.WHITE)
        val ghost = Ghost(Color.BLACK)
        board.position("a8").piece = ghost
        val hasEnemyPiece = board.position("a8").hasEnemyPiece(piece)
        assert(hasEnemyPiece)
    }

    @Test
    fun `should return true in hasEnemyPiece if has a white ghost enemy`() {
        val board = buildInitialBoard()
        val piece = Pawn(Color.BLACK)
        val ghost = Ghost(Color.WHITE)
        board.position("a8").piece = ghost
        val hasEnemyPiece = board.position("a8").hasEnemyPiece(piece)
        assert(hasEnemyPiece)
    }

    @Test
    fun `should return false in hasEnemyPiece if has a ghost ally`() {
        val board = buildInitialBoard()
        val piece = Pawn(Color.WHITE)
        val ghost = Ghost(Color.WHITE)
        board.position("a8").piece = ghost
        val hasEnemyPiece = board.position("a8").hasEnemyPiece(piece)
        assertFalse(hasEnemyPiece)
    }

    @Test
    fun `should return false in hasEnemyPiece if has a ghost enemy but piece is not a pawn`() {
        val board = buildInitialBoard()
        val piece = Queen(Color.WHITE)
        val ghost = Ghost(Color.BLACK)
        board.position("a8").piece = ghost
        val hasEnemyPiece = board.position("a8").hasEnemyPiece(piece)
        assertFalse(hasEnemyPiece)
    }

    @Test
    fun `should return false in hasEnemyPiece if is empty`() {
        val board = buildInitialBoard()
        val piece = Pawn(Color.WHITE)
        val hasEnemyPiece = board.position("f3").hasEnemyPiece(piece)
        assertFalse(hasEnemyPiece)
    }

    @Test
    fun `should return false in isEmpty if has an ally`() {
        val board = buildInitialBoard()
        val isEmpty = board.position("a8").isEmpty()
        assertFalse(isEmpty)
    }

    @Test
    fun `should return false in isEmpty if has an enemy`() {
        val board = buildInitialBoard()
        val isEmpty = board.position("h1").isEmpty()
        assertFalse(isEmpty)
    }

    @Test
    fun `should return true in isEmpty if is empty`() {
        val board = buildInitialBoard()
        val isEmpty = board.position("f3").isEmpty()
        assert(isEmpty)
    }

    @Test
    fun `should return true in isEmpty if is with ghost piece`() {
        val board = buildInitialBoard()
        val piece = Ghost(Color.WHITE)
        board.position("f3").piece = piece
        val isEmpty = board.position("f3").isEmpty()
        assert(isEmpty)
    }

    @Test
    fun `should return white ghost capture with pawn position`() {
        val board = buildInitialBoard()
        val piece = Ghost(Color.WHITE)
        board.position("f3").piece = piece
        assertEquals("Ef4", board.position("f3").getGhostCaptureIfExists())
    }

    @Test
    fun `should return pawn ghost capture with pawn position`() {
        val board = buildInitialBoard()
        val piece = Ghost(Color.BLACK)
        board.position("a6").piece = piece
        assertEquals("Ea5", board.position("a6").getGhostCaptureIfExists())
    }

    @Test
    fun `should return empty when position has another piece`() {
        val board = buildInitialBoard()
        val piece = Pawn(Color.BLACK)
        board.position("a6").piece = piece
        assertEquals("", board.position("a6").getGhostCaptureIfExists())
    }

    @Test
    fun `should return empty when position piece is null`() {
        val board = buildInitialBoard()
        assertEquals("", board.position("a6").getGhostCaptureIfExists())
    }
}
