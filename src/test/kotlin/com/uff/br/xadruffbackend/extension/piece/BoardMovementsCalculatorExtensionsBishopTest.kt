package com.uff.br.xadruffbackend.extension.piece

import com.uff.br.xadruffbackend.extension.BoardMovementsCalculatorExtensions.calculateLegalMovementsInPosition
import com.uff.br.xadruffbackend.extension.position
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.piece.Bishop
import com.uff.br.xadruffbackend.utils.buildEmptyBoard
import com.uff.br.xadruffbackend.utils.buildInitialBoard
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


internal class BoardMovementsCalculatorExtensionsBishopTest{

    @Test
    fun `should calculate movement from white left bishop with initial board`(){
        val board = buildInitialBoard()
        val legalMovements = board.calculateLegalMovementsInPosition(board.position("c1"))
        Assertions.assertEquals(mutableListOf<String>(), legalMovements.movements)
    }

    @Test
    fun `should calculate movement from white right bishop with initial board`(){
        val board = buildInitialBoard()
        val legalMovements = board.calculateLegalMovementsInPosition(board.position("f1"))
        Assertions.assertEquals(mutableListOf<String>(), legalMovements.movements)
    }

    @Test
    fun `should calculate movement from black left bishop with initial board`(){
        val board = buildInitialBoard()
        board.turnColor = Color.BLACK
        val legalMovements = board.calculateLegalMovementsInPosition(board.position("c8"))
        Assertions.assertEquals(mutableListOf<String>(), legalMovements.movements)
    }

    @Test
    fun `should calculate movement from black right bishop with initial board`(){
        val board = buildInitialBoard()
        board.turnColor = Color.BLACK
        val legalMovements = board.calculateLegalMovementsInPosition(board.position("f8"))
        Assertions.assertEquals(mutableListOf<String>(), legalMovements.movements)
    }

    @Test
    fun `should calculate movement from black bishop with empty board`(){
        val board = buildEmptyBoard()
        val expectedMovements = listOf("e5f6", "e5d6", "e5d4", "e5f4", "e5g7", "e5c7", "e5c3",
            "e5g3", "e5h8", "e5b8", "e5b2", "e5h2", "e5a1")
        board.position("e5").piece = Bishop(Color.BLACK)
        val legalMovements = board.calculateLegalMovementsInPosition(board.position("e5"))
        Assertions.assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should calculate movement from white bishop with empty board`(){
        val board = buildEmptyBoard()
        val expectedMovements = listOf("e5f6", "e5d6", "e5d4", "e5f4", "e5g7", "e5c7", "e5c3",
            "e5g3", "e5h8", "e5b8", "e5b2", "e5h2", "e5a1")
        board.position("e5").piece = Bishop(Color.WHITE)
        val legalMovements = board.calculateLegalMovementsInPosition(board.position("e5"))
        Assertions.assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should calculate movement from white bishop with capture in all directions`(){
        val board = buildEmptyBoard()
        val expectedMovements = listOf("e5f6C", "e5d6C", "e5d4C", "e5f4C")
        board.position("e5").piece = Bishop(Color.WHITE)
        board.position("d4").piece = Bishop(Color.BLACK)
        board.position("f4").piece = Bishop(Color.BLACK)
        board.position("d6").piece = Bishop(Color.BLACK)
        board.position("f6").piece = Bishop(Color.BLACK)
        val legalMovements = board.calculateLegalMovementsInPosition(board.position("e5"))
        Assertions.assertEquals(expectedMovements, legalMovements.movements)
    }

    @Test
    fun `should calculate movement from black bishop with capture in all directions`(){
        val board = buildEmptyBoard()
        val expectedMovements = listOf("e5f6C", "e5d6C", "e5d4C", "e5f4C")
        board.position("e5").piece = Bishop(Color.BLACK)
        board.position("d4").piece = Bishop(Color.WHITE)
        board.position("f4").piece = Bishop(Color.WHITE)
        board.position("d6").piece = Bishop(Color.WHITE)
        board.position("f6").piece = Bishop(Color.WHITE)
        val legalMovements = board.calculateLegalMovementsInPosition(board.position("e5"))
        Assertions.assertEquals(expectedMovements, legalMovements.movements)
    }

}