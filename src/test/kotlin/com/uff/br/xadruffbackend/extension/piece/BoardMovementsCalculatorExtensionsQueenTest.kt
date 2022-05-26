package com.uff.br.xadruffbackend.extension.piece

import com.uff.br.xadruffbackend.extension.BoardMovementsCalculatorExtensions.calculateLegalMovementsInPosition
import com.uff.br.xadruffbackend.extension.position
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.piece.Queen
import com.uff.br.xadruffbackend.utils.buildEmptyBoard
import com.uff.br.xadruffbackend.utils.buildInitialBoard
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class BoardMovementsCalculatorExtensionsQueenTest {

    @Test
    fun `should calculate movement from white queen with initial board`() {
        val board = buildInitialBoard()
        val legalMovements = board.calculateLegalMovementsInPosition(board.position("d1"))
        Assertions.assertEquals(mutableListOf<String>(), legalMovements.movements)
    }

    @Test
    fun `should calculate movement from black queen with initial board`() {
        val board = buildInitialBoard()
        board.turnColor = Color.BLACK
        val legalMovements = board.calculateLegalMovementsInPosition(board.position("e8"))
        Assertions.assertEquals(mutableListOf<String>(), legalMovements.movements)
    }

    @Test
    fun `should calculate movement from black queen with empty board`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf(
            "e5f6", "e5d6", "e5d4", "e5f4", "e5e6", "e5e4", "e5d5", "e5f5",
            "e5g7", "e5c7", "e5c3", "e5g3", "e5e7", "e5e3", "e5c5", "e5g5", "e5h8", "e5b8", "e5b2", "e5h2",
            "e5e8", "e5e2", "e5b5", "e5h5", "e5a1", "e5e1", "e5a5"
        )
        board.position("e5").piece = Queen(Color.BLACK)
        val legalMovements = board.calculateLegalMovementsInPosition(board.position("e5"))
        Assertions.assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should calculate movement from white queen with empty board`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf(
            "e5f6", "e5d6", "e5d4", "e5f4", "e5e6", "e5e4", "e5d5", "e5f5",
            "e5g7", "e5c7", "e5c3", "e5g3", "e5e7", "e5e3", "e5c5", "e5g5", "e5h8", "e5b8", "e5b2", "e5h2",
            "e5e8", "e5e2", "e5b5", "e5h5", "e5a1", "e5e1", "e5a5"
        )
        board.position("e5").piece = Queen(Color.WHITE)
        val legalMovements = board.calculateLegalMovementsInPosition(board.position("e5"))
        Assertions.assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should calculate movement from white queen with capture in all directions`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf("e5f6C", "e5d6C", "e5d4C", "e5f4C", "e5e6C", "e5e4C", "e5d5C", "e5f5C")
        board.position("e5").piece = Queen(Color.WHITE)
        board.position("d4").piece = Queen(Color.BLACK)
        board.position("f4").piece = Queen(Color.BLACK)
        board.position("d6").piece = Queen(Color.BLACK)
        board.position("f6").piece = Queen(Color.BLACK)
        board.position("d5").piece = Queen(Color.BLACK)
        board.position("e6").piece = Queen(Color.BLACK)
        board.position("f5").piece = Queen(Color.BLACK)
        board.position("e4").piece = Queen(Color.BLACK)
        val legalMovements = board.calculateLegalMovementsInPosition(board.position("e5"))
        Assertions.assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should calculate movement from black queen with capture in all directions`() {
        val board = buildEmptyBoard()
        val expectedMovements = listOf("e5f6C", "e5d6C", "e5d4C", "e5f4C", "e5e6C", "e5e4C", "e5d5C", "e5f5C")
        board.position("e5").piece = Queen(Color.BLACK)
        board.position("d4").piece = Queen(Color.WHITE)
        board.position("f4").piece = Queen(Color.WHITE)
        board.position("d6").piece = Queen(Color.WHITE)
        board.position("f6").piece = Queen(Color.WHITE)
        board.position("d5").piece = Queen(Color.WHITE)
        board.position("e6").piece = Queen(Color.WHITE)
        board.position("f5").piece = Queen(Color.WHITE)
        board.position("e4").piece = Queen(Color.WHITE)
        val legalMovements = board.calculateLegalMovementsInPosition(board.position("e5"))
        Assertions.assertEquals(expectedMovements, legalMovements.movements)
    }
}
