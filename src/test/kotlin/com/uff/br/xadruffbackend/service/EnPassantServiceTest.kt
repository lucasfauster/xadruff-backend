package com.uff.br.xadruffbackend.service

import com.uff.br.xadruffbackend.extension.position
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.piece.Ghost
import com.uff.br.xadruffbackend.model.piece.King
import com.uff.br.xadruffbackend.model.piece.Pawn
import com.uff.br.xadruffbackend.model.piece.Rook
import com.uff.br.xadruffbackend.utils.buildEmptyBoard
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class EnPassantServiceTest {

    private val enPassantService = EnPassantService()

    @Test
    fun `should erase a white ghost`() {
        val board = buildEmptyBoard()
        board.position("b3").piece = Ghost(Color.WHITE)
        enPassantService.eraseGhost(board)
        assertNull(board.position("b3").piece)
    }

    @Test
    fun `should erase a black ghost`() {
        val board = buildEmptyBoard()
        board.position("b6").piece = Ghost(Color.BLACK)
        enPassantService.eraseGhost(board)
        assertNull(board.position("b6").piece)
    }

    @Test
    fun `should not erase another piece`() {
        val board = buildEmptyBoard()
        val piece = Pawn(Color.WHITE)
        board.position("b3").piece = piece
        enPassantService.eraseGhost(board)
        assertEquals(piece, board.position("b3").piece)
    }

    @Test
    fun `should apply en passant in white ghost`() {
        val board = buildEmptyBoard()
        val pawn = Pawn(Color.WHITE)
        val pawnAttacker = Pawn(Color.BLACK)
        val ghost = Ghost(Color.WHITE)
        board.position("b3").piece = ghost
        board.position("b4").piece = pawn
        board.position("c4").piece = pawnAttacker
        enPassantService.applyEnPassant(board, "c4b3C")
        assertNull(board.position("b4").piece)
    }

    @Test
    fun `should apply en passant in black ghost`() {
        val board = buildEmptyBoard()
        val pawn = Pawn(Color.BLACK)
        val pawnAttacker = Pawn(Color.WHITE)
        val ghost = Ghost(Color.BLACK)
        board.position("b6").piece = ghost
        board.position("b5").piece = pawn
        board.position("c5").piece = pawnAttacker
        enPassantService.applyEnPassant(board, "c5b6C")
        assertNull(board.position("b5").piece)
    }

    @Test
    fun `should not apply en passant when piece is not a ghost`() {
        val board = buildEmptyBoard()
        val pawn = Pawn(Color.WHITE)
        val pawnAttacker = Pawn(Color.BLACK)
        val king = King(Color.WHITE)
        board.position("b3").piece = king
        board.position("b4").piece = pawn
        board.position("c4").piece = pawnAttacker
        enPassantService.applyEnPassant(board, "c4b3C")
        assertEquals(pawn, board.position("b4").piece)
    }

    @Test
    fun `should not apply en passant when piece is not a pawn`() {
        val board = buildEmptyBoard()
        val pawn = Pawn(Color.WHITE)
        val king = King(Color.BLACK)
        val ghost = Ghost(Color.WHITE)
        board.position("b3").piece = ghost
        board.position("b4").piece = pawn
        board.position("c4").piece = king
        enPassantService.applyEnPassant(board, "c4b3C")
        assertEquals(pawn, board.position("b4").piece)
    }

    @Test
    fun `should create a white ghost`() {
        val board = buildEmptyBoard()
        val pawn = Pawn(Color.WHITE)
        board.position("b2").piece = pawn
        enPassantService.handleGhostCreation(board, "b2b4", pawn)
        val ghost = board.position("b3").piece
        assert(ghost is Ghost)
        assertEquals(Color.WHITE, ghost!!.color)
    }

    @Test
    fun `should create a black ghost`() {
        val board = buildEmptyBoard()
        val pawn = Pawn(Color.BLACK)
        board.position("b7").piece = pawn
        enPassantService.handleGhostCreation(board, "b7b5", pawn)
        val ghost = board.position("b6").piece
        assert(ghost is Ghost)
        assertEquals(Color.BLACK, ghost!!.color)
    }

    @Test
    fun `should not create a white ghost when is not a pawn movement`() {
        val board = buildEmptyBoard()
        val rook = Rook(Color.WHITE)
        board.position("b2").piece = rook
        enPassantService.handleGhostCreation(board, "b2b4", rook)
        assertNull(board.position("b3").piece)
    }

    @Test
    fun `should not create a black ghost when is not a pawn movement`() {
        val board = buildEmptyBoard()
        val rook = Rook(Color.BLACK)
        board.position("b7").piece = rook
        enPassantService.handleGhostCreation(board, "b7b5", rook)
        assertNull(board.position("b6").piece)
    }

    @Test
    fun `should not create a white ghost when pawn move one square`() {
        val board = buildEmptyBoard()
        val pawn = Pawn(Color.WHITE)
        board.position("b2").piece = pawn
        enPassantService.handleGhostCreation(board, "b2b3", pawn)
        assertNull(board.position("b3").piece)
    }

    @Test
    fun `should not create a black ghost when pawn move one square`() {
        val board = buildEmptyBoard()
        val pawn = Pawn(Color.BLACK)
        board.position("b7").piece = pawn
        enPassantService.handleGhostCreation(board, "b7b6", pawn)
        assertNull(board.position("b6").piece)
    }

    @Test
    fun `should create and not erase ghost`() {
        val board = buildEmptyBoard()
        val pawn = Pawn(Color.BLACK)
        board.position("b7").piece = pawn
        enPassantService.handleEnPassant(board, "b7b5", pawn)
        val ghost = board.position("b6").piece
        assert(ghost is Ghost)
        assertEquals(Color.BLACK, ghost!!.color)
    }

    @Test
    fun `should not erase ghost before a capture`() {
        val board = buildEmptyBoard()
        val pawn = Pawn(Color.BLACK)
        val pawnAttacker = Pawn(Color.WHITE)
        val ghost = Ghost(Color.BLACK)
        board.position("b6").piece = ghost
        board.position("b5").piece = pawn
        board.position("c5").piece = pawnAttacker
        enPassantService.handleEnPassant(board, "c5b6C", pawnAttacker)
        assertNull(board.position("b5").piece)
    }
}
