package com.uff.br.xadruffbackend.extension.piece

import com.uff.br.xadruffbackend.extension.BoardMovementsCalculatorExtensions.calculateLegalMovementsInPosition
import com.uff.br.xadruffbackend.extension.position
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.piece.King
import com.uff.br.xadruffbackend.model.piece.Pawn
import com.uff.br.xadruffbackend.utils.buildEmptyBoard
import com.uff.br.xadruffbackend.utils.buildInitialBoard
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class BoardMovementsCalculatorExtensionsKingTest {

    @Test
    fun `should calculate movement from white king with initial board`() {
        val board = buildInitialBoard()
        val legalMovements = board.calculateLegalMovementsInPosition(board.position("e1"))
        Assertions.assertEquals(mutableListOf<String>(), legalMovements.movements)
    }

    @Test
    fun `should calculate movement from black king with initial board`() {
        val board = buildInitialBoard()
        board.turnColor = Color.BLACK
        val legalMovements = board.calculateLegalMovementsInPosition(board.position("d8"))
        Assertions.assertEquals(mutableListOf<String>(), legalMovements.movements)
    }

    @Test
    fun `should calculate movement from black king with empty board`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf("e5f6", "e5d6", "e5d4", "e5f4", "e5e6", "e5e4", "e5d5", "e5f5")
        board.position("e5").piece = King(Color.BLACK)
        val legalMovements = board.calculateLegalMovementsInPosition(board.position("e5"))
        Assertions.assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should calculate movement from white king with empty board`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf("e5f6", "e5d6", "e5d4", "e5f4", "e5e6", "e5e4", "e5d5", "e5f5")
        board.position("e5").piece = King(Color.WHITE)
        val legalMovements = board.calculateLegalMovementsInPosition(board.position("e5"))
        Assertions.assertEquals(expectedMovements, legalMovements.movements)
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
        Assertions.assertEquals(expectedMovements, legalMovements.movements)
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
        Assertions.assertEquals(expectedMovements, legalMovements.movements)
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
        Assertions.assertEquals(expectedMovements, legalMovements.movements)
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
        Assertions.assertEquals(expectedMovements, legalMovements.movements)
    }
}
