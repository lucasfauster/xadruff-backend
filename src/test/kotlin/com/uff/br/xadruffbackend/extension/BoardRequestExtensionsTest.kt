package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.dto.enum.Color
import com.uff.br.xadruffbackend.dto.piece.Bishop
import com.uff.br.xadruffbackend.dto.piece.Ghost
import com.uff.br.xadruffbackend.dto.piece.King
import com.uff.br.xadruffbackend.dto.piece.Knight
import com.uff.br.xadruffbackend.dto.piece.Pawn
import com.uff.br.xadruffbackend.dto.piece.Queen
import com.uff.br.xadruffbackend.dto.piece.Rook
import com.uff.br.xadruffbackend.dto.request.BoardRequest
import com.uff.br.xadruffbackend.utils.assertBoard
import com.uff.br.xadruffbackend.utils.buildInitialBoard
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.security.InvalidParameterException

class BoardRequestExtensionsTest {

    @Test
    fun `should transform board to initial board`() {
        val stringBoard = listOf(
            listOf("r", "n", "b", "q", "k", "b", "n", "r"),
            listOf("p", "p", "p", "p", "p", "p", "p", "p"),
            listOf("", "", "", "", "", "", "", ""),
            listOf("", "", "", "", "", "", "", ""),
            listOf("", "", "", "", "", "", "", ""),
            listOf("", "", "", "", "", "", "", ""),
            listOf("P", "P", "P", "P", "P", "P", "P", "P"),
            listOf("R", "N", "B", "Q", "K", "B", "N", "R")
        )

        val boardRequest = BoardRequest(
            stringBoard,
            Color.WHITE
        )
        assertBoard(buildInitialBoard().positions, boardRequest.toBoard().positions)
        assertEquals(buildInitialBoard().turnColor, boardRequest.toBoard().turnColor)
    }

    @Test
    fun `should get pawn from 'p' or 'P'`() {
        var pawn = "p".getPiece()
        assert(pawn is Pawn)
        assertEquals(Color.BLACK, pawn!!.color)

        pawn = "P".getPiece()
        assert(pawn is Pawn)
        assertEquals(Color.WHITE, pawn!!.color)
    }

    @Test
    fun `should get king from 'k' or 'K'`() {
        var king = "k".getPiece()
        assert(king is King)
        assertEquals(Color.BLACK, king!!.color)

        king = "K".getPiece()
        assert(king is King)
        assertEquals(Color.WHITE, king!!.color)
    }

    @Test
    fun `should get queen from 'q' or 'Q'`() {
        var queen = "q".getPiece()
        assert(queen is Queen)
        assertEquals(Color.BLACK, queen!!.color)

        queen = "Q".getPiece()
        assert(queen is Queen)
        assertEquals(Color.WHITE, queen!!.color)
    }

    @Test
    fun `should get rook from 'r' or 'R'`() {
        var rook = "r".getPiece()
        assert(rook is Rook)
        assertEquals(Color.BLACK, rook!!.color)

        rook = "R".getPiece()
        assert(rook is Rook)
        assertEquals(Color.WHITE, rook!!.color)
    }

    @Test
    fun `should get bishop from 'b' or 'B'`() {
        var bishop = "b".getPiece()
        assert(bishop is Bishop)
        assertEquals(Color.BLACK, bishop!!.color)

        bishop = "B".getPiece()
        assert(bishop is Bishop)
        assertEquals(Color.WHITE, bishop!!.color)
    }

    @Test
    fun `should get knight from 'n' or 'N'`() {
        var knight = "n".getPiece()
        assert(knight is Knight)
        assertEquals(Color.BLACK, knight!!.color)

        knight = "N".getPiece()
        assert(knight is Knight)
        assertEquals(Color.WHITE, knight!!.color)
    }

    @Test
    fun `should get ghost from 'g' or 'G'`() {
        var ghost = "g".getPiece()
        assert(ghost is Ghost)
        assertEquals(Color.BLACK, ghost!!.color)

        ghost = "G".getPiece()
        assert(ghost is Ghost)
        assertEquals(Color.WHITE, ghost!!.color)
    }

    @Test
    fun `should get null from empty spaces`() {
        assertNull("".getPiece())
    }

    @Test
    fun `should get null from whitespace`() {
        assertNull(" ".getPiece())
    }

    @Test
    fun `should throw InvalidParameterException from any other letter`() {
        assertThrows<InvalidParameterException> {
            "a".getPiece()
        }
    }
}
