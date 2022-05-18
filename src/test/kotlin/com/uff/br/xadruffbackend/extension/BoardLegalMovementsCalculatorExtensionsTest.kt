package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.extension.BoardLegalMovementsCalculatorExtensions.buildAction
import com.uff.br.xadruffbackend.extension.BoardLegalMovementsCalculatorExtensions.calculateLegalMovementsInPosition
import com.uff.br.xadruffbackend.extension.BoardLegalMovementsCalculatorExtensions.calculatePseudoLegalMoves
import com.uff.br.xadruffbackend.extension.BoardLegalMovementsCalculatorExtensions.canMove
import com.uff.br.xadruffbackend.model.LegalMovements
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.piece.Pawn
import com.uff.br.xadruffbackend.utils.buildEmptyBoard
import com.uff.br.xadruffbackend.utils.buildInitialBoard
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test


internal class BoardLegalMovementsCalculatorExtensionsTest{

    @Test
    fun `should return initial legal movements for white player`() {
        val board = buildInitialBoard()
        val actualMovements = board.calculatePseudoLegalMoves()

        val expectedMovements = mutableListOf("a2a3", "a2a4", "b2b3", "b2b4", "c2c3", "c2c4", "d2d3", "d2d4",
            "e2e3", "e2e4", "f2f3", "f2f4", "g2g3", "g2g4", "h2h3", "h2h4",
            "b1a3", "b1c3", "g1f3", "g1h3")

        assertEquals(expectedMovements, actualMovements.movements)
    }

    @Test
    fun `should return false in canMove if has an ally`(){
        val board = buildInitialBoard()
        val canMove = board.canMove(board.positions[7][7])
        assertFalse(canMove)
    }

    @Test
    fun `should return true in canMove if has an enemy`(){
        val board = buildInitialBoard()
        val canMove = board.canMove(board.positions[0][0])
        assert(canMove)
    }

    @Test
    fun `should return true in canMove if is empty`(){
        val board = buildInitialBoard()
        val canMove = board.canMove(board.positions[5][5])
        assert(canMove)
    }

    @Test
    fun `should return C in buildAction if has enemy`(){
        val board = buildInitialBoard()
        val action = board.buildAction(board.positions[0][0])
        assertEquals("C", action)

    }

    @Test
    fun `should return empty string in buildAction if has ally`(){
        val board = buildInitialBoard()
        val action = board.buildAction(board.positions[7][7])
        assertEquals("", action)
    }

    @Test
    fun `should return empty string in buildAction if is empty`(){
        val board = buildInitialBoard()
        val action = board.buildAction(board.positions[5][5])
        assertEquals("", action)
    }

    @Test
    fun `should generate possible movements of white pawn in initial position`(){
        val board = buildEmptyBoard()
        val whitePawn = Pawn(Color.WHITE)
        board.positions[6][4].piece = whitePawn

        val legalMovements = board.calculateLegalMovementsInPosition(board.positions[6][4])

        val correctLegalMoves = mutableListOf("e2e3", "e2e4")

        assertEquals(correctLegalMoves, legalMovements.movements)
    }

    @Test
    fun `should generate possible movements of black pawn in initial position`(){
        val board = buildEmptyBoard()
        val blackPawn = Pawn(Color.BLACK)
        board.positions[1][4].piece = blackPawn
        board.turnColor = Color.BLACK

        val legalMovements = board.calculateLegalMovementsInPosition(board.positions[1][4])

        val correctLegalMoves = mutableListOf("e7e6", "e7e5")

        assertEquals(correctLegalMoves, legalMovements.movements)
    }

    @Test
    fun `should generate empty possible movements of pawn in initial position with piece in the way`(){
        val board = buildEmptyBoard()
        val whitePawn = Pawn(Color.WHITE)
        val blackPawn = Pawn(Color.BLACK)
        board.positions[6][4].piece = whitePawn
        board.positions[5][4].piece = blackPawn

        val legalMovements = board.calculateLegalMovementsInPosition(board.positions[6][4])
        board.turnColor = Color.BLACK
        legalMovements.addAll(board.calculateLegalMovementsInPosition(board.positions[5][4]))

        assertEquals(mutableListOf<String>(), legalMovements.movements)
    }

    @Test
    fun `should generate possible movements with capture and one position forward for pawn`(){
        val board = buildEmptyBoard()
        val whitePawn = Pawn(Color.WHITE)
        val blackPawn = Pawn(Color.BLACK)
        board.positions[5][4].piece = whitePawn
        board.positions[4][3].piece = blackPawn

        val legalMovements = board.calculateLegalMovementsInPosition(board.positions[5][4])

        val correctLegalMoves = listOf("e3e4", "e3d4C")

        assertEquals(correctLegalMoves, legalMovements.movements)
    }

    @Test
    fun `should calculate movement from white left rook with initial board`(){
        val board = buildInitialBoard()
        val legalMovements = LegalMovements(mutableListOf())
        board.calculateLegalMovementsInPosition(board.positions[7][0])
        assertEquals(legalMovements.movements, mutableListOf<String>())
    }

    @Test
    fun `should calculate movement from white right rook with initial board`(){
        val board = buildInitialBoard()
        val legalMovements = LegalMovements(mutableListOf())
        board.calculateLegalMovementsInPosition(board.positions[7][7])
        assertEquals(legalMovements.movements, mutableListOf<String>())
    }


}