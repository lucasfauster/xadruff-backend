package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.piece.Pawn
import com.uff.br.xadruffbackend.utils.buildInitialBoard
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

internal class PieceExtensionsTest {

    @Test
    fun `should return false in canMove if piece is white and has an ally`(){
        val board = buildInitialBoard()
        val piece = Pawn(Color.WHITE)
        val canMove = piece.canMove(board.position("a1"))
        assertFalse(canMove)
    }

    @Test
    fun `should return true in canMove if piece is white and has an enemy`(){
        val board = buildInitialBoard()
        val piece = Pawn(Color.WHITE)
        val canMove = piece.canMove(board.position("a8"))
        assert(canMove)
    }

    @Test
    fun `should return true in canMove if piece is white and is empty`(){
        val board = buildInitialBoard()
        val piece = Pawn(Color.WHITE)
        val canMove = piece.canMove(board.position("d5"))
        assert(canMove)
    }

    @Test
    fun `should return false in canMove if piece is black and has an ally`(){
        val board = buildInitialBoard()
        val piece = Pawn(Color.BLACK)
        val canMove = piece.canMove(board.position("a8"))
        assertFalse(canMove)
    }

    @Test
    fun `should return true in canMove if piece is black and has an enemy`(){
        val board = buildInitialBoard()
        val piece = Pawn(Color.BLACK)
        val canMove = piece.canMove(board.position("a1"))
        assert(canMove)
    }

    @Test
    fun `should return true in canMove if piece is black and is empty`(){
        val board = buildInitialBoard()
        val piece = Pawn(Color.BLACK)
        val canMove = piece.canMove(board.position("d5"))
        assert(canMove)
    }

}