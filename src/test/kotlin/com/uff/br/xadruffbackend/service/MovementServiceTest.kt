package com.uff.br.xadruffbackend.service

import com.uff.br.xadruffbackend.extension.position
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.piece.Bishop
import com.uff.br.xadruffbackend.model.piece.King
import com.uff.br.xadruffbackend.model.piece.Knight
import com.uff.br.xadruffbackend.model.piece.Pawn
import com.uff.br.xadruffbackend.utils.buildEmptyBoard
import com.uff.br.xadruffbackend.utils.buildInitialBoard
import com.uff.br.xadruffbackend.utils.buildInitialLegalMovements
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MovementServiceTest {

    private val movementService = MovementService()

    @Test
    fun `should generate initial legal movements from initial board`() {
        val board = buildInitialBoard()
        val legalMoves = movementService.calculateLegalMovements(board)

        val correctLegalMoves = buildInitialLegalMovements()

        Assertions.assertEquals(correctLegalMoves, legalMoves.movements)
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
        Assertions.assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should calculate movement from white king with capture in two directions`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf("e5f6", "e5d6", "e5d4", "e5f4", "e5e6C", "e5e4C")
        board.position("e5").piece = King(Color.WHITE)
        board.position("e4").piece = Pawn(Color.BLACK)
        board.position("e6").piece = Pawn(Color.BLACK)
        val legalMovements = movementService.calculateLegalMovements(board)
        Assertions.assertEquals(expectedMovements, legalMovements.movements)
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
        Assertions.assertEquals(expectedMovements, legalMovements.movements)
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
        Assertions.assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should have no legal movements for pinned pawn`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf("f6g7", "f6e7", "f6g5", "f6f7", "f6f5", "f6e6", "f6g6")
        board.position("f6").piece = King(Color.WHITE)
        board.position("e5").piece = Pawn(Color.WHITE)
        board.position("c3").piece = Bishop(Color.BLACK)
        val legalMovements = movementService.calculateLegalMovements(board)
        Assertions.assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should apply movement a1 to a2`() {

        val board = buildEmptyBoard()
        val pawn = Pawn(Color.WHITE)
        board.position("a1").piece = pawn

        movementService.applyMove(board, "a1a2")
        Assertions.assertNull(board.position("a1").piece)
        Assertions.assertEquals(pawn, board.position("a2").piece)
    }

    @Test
    fun `should apply movement a1 to a2 with capture`() {

        val board = buildEmptyBoard()
        val pawn = Pawn(Color.WHITE)
        board.position("a1").piece = pawn

        movementService.applyMove(board, "a1a2C")
        Assertions.assertNull(board.position("a1").piece)
        Assertions.assertEquals(pawn, board.position("a2").piece)
    }
}
