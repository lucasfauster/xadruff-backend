package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.model.Position
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.piece.Pawn
import com.uff.br.xadruffbackend.utils.buildInitialBoard
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

internal class PositionExtensionsTest {

    @Test
    fun `should transform position to chess position`(){
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

        for(line in (0..7)){
            for(column in (0..7)){
                val position = Position(line, column)
                val stringPosition = position.toChessPosition()
                Assertions.assertEquals(chessPositions[line][column], stringPosition)
            }
        }
    }

    @Test
    fun `should return true in hasAllyPiece if has an ally`(){
        val board = buildInitialBoard()
        val piece = Pawn(Color.WHITE)
        val hasAllyPiece = board.position("h1").hasAllyPiece(piece)
        assert(hasAllyPiece)
    }

    @Test
    fun `should return false in hasAllyPiece if has an enemy`(){
        val board = buildInitialBoard()
        val piece = Pawn(Color.WHITE)
        val hasAllyPiece = board.position("a8").hasAllyPiece(piece)
        assertFalse(hasAllyPiece)
    }

    @Test
    fun `should return false in hasAllyPiece if is empty`(){
        val board = buildInitialBoard()
        val piece = Pawn(Color.WHITE)
        val hasAllyPiece = board.position("f3").hasAllyPiece(piece)
        assertFalse(hasAllyPiece)
    }

    @Test
    fun `should return false in hasEnemyPiece if has an ally`(){
        val board = buildInitialBoard()
        val piece = Pawn(Color.WHITE)
        val hasEnemyPiece = board.position("h1").hasEnemyPiece(piece)
        assertFalse(hasEnemyPiece)
    }

    @Test
    fun `should return true in hasEnemyPiece if has an enemy`(){
        val board = buildInitialBoard()
        val piece = Pawn(Color.WHITE)
        val hasEnemyPiece = board.position("a8").hasEnemyPiece(piece)
        assert(hasEnemyPiece)
    }

    @Test
    fun `should return false in hasEnemyPiece if is empty`(){
        val board = buildInitialBoard()
        val piece = Pawn(Color.WHITE)
        val hasEnemyPiece = board.position("f3").hasEnemyPiece(piece)
        assertFalse(hasEnemyPiece)
    }

    @Test
    fun `should return false in isEmpty if has an ally`(){
        val board = buildInitialBoard()
        val piece = Pawn(Color.WHITE)
        val isEmpty = board.position("a8").isEmpty(piece)
        assertFalse(isEmpty)
    }

    @Test
    fun `should return false in isEmpty if has an enemy`(){
        val board = buildInitialBoard()
        val piece = Pawn(Color.WHITE)
        val isEmpty = board.position("h1").isEmpty(piece)
        assertFalse(isEmpty)
    }

    @Test
    fun `should return true in isEmpty if is empty`(){
        val board = buildInitialBoard()
        val piece = Pawn(Color.WHITE)
        val isEmpty = board.position("f3").isEmpty(piece)
        assert(isEmpty)
    }
}