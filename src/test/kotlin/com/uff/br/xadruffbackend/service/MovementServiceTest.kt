package com.uff.br.xadruffbackend.service

import com.uff.br.xadruffbackend.extension.changeTurn
import com.uff.br.xadruffbackend.extension.position
import com.uff.br.xadruffbackend.extension.toJsonString
import com.uff.br.xadruffbackend.model.GameEntity
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.piece.Bishop
import com.uff.br.xadruffbackend.model.piece.King
import com.uff.br.xadruffbackend.model.piece.Knight
import com.uff.br.xadruffbackend.model.piece.Pawn
import com.uff.br.xadruffbackend.model.piece.Queen
import com.uff.br.xadruffbackend.model.piece.Rook
import com.uff.br.xadruffbackend.utils.buildEmptyBoard
import com.uff.br.xadruffbackend.utils.buildInitialBoard
import com.uff.br.xadruffbackend.utils.buildInitialLegalMovements
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class MovementServiceTest {

    private val enPassantService: EnPassantService = EnPassantService()
    private val movementService: MovementService = MovementService(enPassantService)

    @Test
    fun `should generate initial legal movements from initial board`() {
        val board = buildInitialBoard()
        val legalMoves = movementService.calculateLegalMovements(board)

        val correctLegalMoves = buildInitialLegalMovements()

        assertEquals(correctLegalMoves, legalMoves.movements)
    }

    @Test
    fun `should generate possible movements with one capture and three moves for knight in the edge`() {
        val board = buildEmptyBoard()
        val knight = Knight(Color.WHITE)
        val blackPawn = Pawn(Color.BLACK)
        board.position("h4").piece = knight
        board.position("f5").piece = blackPawn

        val legalMoves = movementService.calculateLegalMovements(board)

        val correctLegalMoves = mutableListOf("h4g6", "h4f5C", "h4f3", "h4g2")

        assert(correctLegalMoves.containsAll(legalMoves.movements))
    }

    @Test
    fun `should calculate movement from white king with capture in six directions`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf("e5f6C", "e5d6C", "e5d4C", "e5f4C", "e5e6", "e5d5C", "e5f5C")
        board.position("e5").piece = King(Color.WHITE)
        board.position("d4").piece = Pawn(Color.BLACK)
        board.position("f4").piece = Pawn(Color.BLACK)
        board.position("d6").piece = Pawn(Color.BLACK)
        board.position("f6").piece = Pawn(Color.BLACK)
        board.position("d5").piece = Pawn(Color.BLACK)
        board.position("f5").piece = Pawn(Color.BLACK)
        val legalMovements = movementService.calculateLegalMovements(board)
        assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should calculate movement from white king with capture in two directions`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf("e5f6", "e5d6", "e5d4", "e5f4", "e5e6C", "e5e4C")
        board.position("e5").piece = King(Color.WHITE)
        board.position("e4").piece = Pawn(Color.BLACK)
        board.position("e6").piece = Pawn(Color.BLACK)
        val legalMovements = movementService.calculateLegalMovements(board)
        assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should calculate movement from black king with capture in six directions`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf("e5f6C", "e5d6C", "e5d4C", "e5f4C", "e5e4", "e5d5C", "e5f5C")
        board.position("e5").piece = King(Color.BLACK)
        board.position("d4").piece = Pawn(Color.WHITE)
        board.position("f4").piece = Pawn(Color.WHITE)
        board.position("d6").piece = Pawn(Color.WHITE)
        board.position("f6").piece = Pawn(Color.WHITE)
        board.position("d5").piece = Pawn(Color.WHITE)
        board.position("f5").piece = Pawn(Color.WHITE)
        board.turnColor = Color.BLACK
        val legalMovements = movementService.calculateLegalMovements(board)
        assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should calculate movement from black king with capture in two directions`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf("e5f6", "e5d6", "e5d4", "e5f4", "e5e6C", "e5e4C")
        board.position("e5").piece = King(Color.BLACK)
        board.position("e4").piece = Pawn(Color.WHITE)
        board.position("e6").piece = Pawn(Color.WHITE)
        board.turnColor = Color.BLACK
        val legalMovements = movementService.calculateLegalMovements(board)
        assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should have no legal movements for pinned pawn`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf("f6g7", "f6e7", "f6g5", "f6f7", "f6f5", "f6e6", "f6g6")
        board.position("f6").piece = King(Color.WHITE)
        board.position("e5").piece = Pawn(Color.WHITE)
        board.position("c3").piece = Bishop(Color.BLACK)
        val legalMovements = movementService.calculateLegalMovements(board)
        assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should apply movement a1 to a2`() {

        val board = buildEmptyBoard()
        val pawn = Pawn(Color.WHITE)
        board.position("a1").piece = pawn

        movementService.applyMove(board, "a1a2")
        assertNull(board.position("a1").piece)
        assertEquals(pawn, board.position("a2").piece)
    }

    @Test
    fun `should apply movement a1 to a2 with capture`() {

        val board = buildEmptyBoard()
        val pawn = Pawn(Color.WHITE)
        board.position("a1").piece = pawn

        movementService.applyMove(board, "a1a2C")
        assertNull(board.position("a1").piece)
        assertEquals(pawn, board.position("a2").piece)
    }

    @Test
    fun `should add one to draw moves for WHITE player in handleDrawMoveRule`() {
        val board = buildEmptyBoard()
        board.position("b3").piece = Knight(Color.WHITE)
        val game = GameEntity(board = board.toJsonString())
        movementService.handleDrawMoveRule(game, "b1b3")
        assertEquals(1, game.whiteDrawMoves)
        assertEquals(0, game.blackDrawMoves)
    }

    @Test
    fun `should add one to draw moves for BLACK player in handleDrawMoveRule`() {
        val board = buildEmptyBoard()
        board.position("b3").piece = Knight(Color.BLACK)
        board.changeTurn()
        val game = GameEntity(board = board.toJsonString())
        movementService.handleDrawMoveRule(game, "b1b3")
        assertEquals(0, game.whiteDrawMoves)
        assertEquals(1, game.blackDrawMoves)
    }

    @Test
    fun `should not add one to draw moves because it will be a pawn move`() {
        val board = buildEmptyBoard()
        board.position("b1").piece = Pawn(Color.BLACK)
        val game = GameEntity(board = board.toJsonString())
        movementService.handleDrawMoveRule(game, "b1b3")
        assertEquals(0, game.whiteDrawMoves)
        assertEquals(0, game.blackDrawMoves)
    }

    @Test
    fun `should not add one to draw moves because it will be a capture move`() {
        val board = buildEmptyBoard()
        board.position("b3").piece = Knight(Color.BLACK)
        val game = GameEntity(board = board.toJsonString())
        movementService.handleDrawMoveRule(game, "b1b3C")
        assertEquals(0, game.whiteDrawMoves)
        assertEquals(0, game.blackDrawMoves)
    }

    @Test
    fun `should not add one to draw moves because was a capture move with a pawn`() {
        val board = buildEmptyBoard()
        board.position("b3").piece = Pawn(Color.BLACK)
        val game = GameEntity(board = board.toJsonString())
        movementService.handleDrawMoveRule(game, "b1b3C")
        assertEquals(0, game.whiteDrawMoves)
        assertEquals(0, game.blackDrawMoves)
    }

    @Test
    fun `should get queen piece in a promotion movement`() {
        val blackPawn = Pawn(Color.BLACK)
        val whitePawn = Pawn(Color.WHITE)
        assertEquals(Queen(Color.BLACK).value, movementService.getPiece(blackPawn, "a7a8Pq").value)
        assertEquals(Queen(Color.WHITE).value, movementService.getPiece(whitePawn, "a7a8PQ").value)
    }

    @Test
    fun `should get knight piece in a promotion movement`() {
        val blackPawn = Pawn(Color.BLACK)
        val whitePawn = Pawn(Color.WHITE)
        assertEquals(Knight(Color.BLACK).value, movementService.getPiece(blackPawn, "a7a8Pn").value)
        assertEquals(Knight(Color.WHITE).value, movementService.getPiece(whitePawn, "a7a8PN").value)
    }

    @Test
    fun `should get bishop piece in a promotion movement`() {
        val blackPawn = Pawn(Color.BLACK)
        val whitePawn = Pawn(Color.WHITE)
        assertEquals(Bishop(Color.BLACK).value, movementService.getPiece(blackPawn, "a7a8Pb").value)
        assertEquals(Bishop(Color.WHITE).value, movementService.getPiece(whitePawn, "a7a8PB").value)
    }

    @Test
    fun `should get rook piece in a promotion movement`() {
        val blackPawn = Pawn(Color.BLACK)
        val whitePawn = Pawn(Color.WHITE)
        assertEquals(Rook(Color.BLACK).value, movementService.getPiece(blackPawn, "a7a8Pr").value)
        assertEquals(Rook(Color.WHITE).value, movementService.getPiece(whitePawn, "a7a8PR").value)
    }

    @Test
    fun `should get queen piece in a promotion movement with capture`() {
        val blackPawn = Pawn(Color.BLACK)
        val whitePawn = Pawn(Color.WHITE)
        assertEquals(Queen(Color.BLACK).value, movementService.getPiece(blackPawn, "a7a8CPq").value)
        assertEquals(Queen(Color.WHITE).value, movementService.getPiece(whitePawn, "a7a8CPQ").value)
    }

    @Test
    fun `should get knight piece in a promotion movement with capture`() {
        val blackPawn = Pawn(Color.BLACK)
        val whitePawn = Pawn(Color.WHITE)
        assertEquals(Knight(Color.BLACK).value, movementService.getPiece(blackPawn, "a7a8CPn").value)
        assertEquals(Knight(Color.WHITE).value, movementService.getPiece(whitePawn, "a7a8CPN").value)
    }

    @Test
    fun `should get bishop piece in a promotion movement with capture`() {
        val blackPawn = Pawn(Color.BLACK)
        val whitePawn = Pawn(Color.WHITE)
        assertEquals(Bishop(Color.BLACK).value, movementService.getPiece(blackPawn, "a7a8CPb").value)
        assertEquals(Bishop(Color.WHITE).value, movementService.getPiece(whitePawn, "a7a8CPB").value)
    }

    @Test
    fun `should get rook piece in a promotion movement with capture`() {
        val blackPawn = Pawn(Color.BLACK)
        val whitePawn = Pawn(Color.WHITE)
        assertEquals(Rook(Color.BLACK).value, movementService.getPiece(blackPawn, "a7a8CPr").value)
        assertEquals(Rook(Color.WHITE).value, movementService.getPiece(whitePawn, "a7a8CPR").value)
    }

    @Test
    fun `should apply a promotion movement`() {
        val board = buildEmptyBoard()
        val pawn = Pawn(Color.BLACK)
        board.position("a2").piece = pawn

        movementService.applyMove(board, "a2a1Pq")
        assertNull(board.position("a2").piece)
        assertEquals(Queen.VALUE, board.position("a1").piece!!.value)
    }

    @Test
    fun `should apply a promotion movement with capture`() {
        val board = buildEmptyBoard()
        val pawn = Pawn(Color.BLACK)
        board.position("a1").piece = Rook(Color.WHITE)
        board.position("a2").piece = pawn

        movementService.applyMove(board, "a2a1CPq")
        assertNull(board.position("a2").piece)
        assertEquals(Queen.VALUE, board.position("a1").piece!!.value)
    }

    @Test
    fun `should apply move for rook when applied small rook movement for white king`() {
        val board = buildInitialBoard()
        board.position("f1").piece = null
        board.position("g1").piece = null

        movementService.handleCastleMovement(board, "e1g1")
        assertEquals(Rook.VALUE.uppercaseChar(), board.position("f1").piece!!.value)
    }

    @Test
    fun `should apply move for rook when applied big rook movement for white king`() {
        val board = buildInitialBoard()
        board.position("b1").piece = null
        board.position("c1").piece = null
        board.position("d1").piece = null

        movementService.handleCastleMovement(board, "e1c1")
        assertEquals(Rook.VALUE.uppercaseChar(), board.position("d1").piece!!.value)
    }

    @Test
    fun `should apply move for rook when applied small rook movement for black king`() {
        val board = buildInitialBoard()
        board.position("f8").piece = null
        board.position("g8").piece = null
        board.turnColor = Color.BLACK

        movementService.handleCastleMovement(board, "e8g8")
        assertEquals(Rook.VALUE, board.position("f8").piece!!.value)
    }

    @Test
    fun `should apply move for rook when applied big rook movement for black king`() {
        val board = buildInitialBoard()
        board.position("b8").piece = null
        board.position("c8").piece = null
        board.position("d8").piece = null
        board.turnColor = Color.BLACK

        movementService.handleCastleMovement(board, "e8c8")
        assertEquals(Rook.VALUE, board.position("d8").piece!!.value)
    }
}
