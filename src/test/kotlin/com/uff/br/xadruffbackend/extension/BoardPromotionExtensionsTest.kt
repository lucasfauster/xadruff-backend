package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.extension.BoardPromotionExtensions.createPromotionMovements
import com.uff.br.xadruffbackend.extension.BoardPromotionExtensions.handlePromotionInRange
import com.uff.br.xadruffbackend.extension.BoardPromotionExtensions.isPawnLastMovement
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.piece.Pawn
import com.uff.br.xadruffbackend.model.piece.Rook
import com.uff.br.xadruffbackend.utils.buildEmptyBoard
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class BoardPromotionExtensionsTest {

    @Test
    fun `should return true that is the last movement of a pawn`() {
        assertTrue(isPawnLastMovement("a8"))
        assertTrue(isPawnLastMovement("a1"))
        assertTrue(isPawnLastMovement("h8"))
        assertTrue(isPawnLastMovement("a8"))
    }

    @Test
    fun `should return false that is the last movement of a pawn`() {
        assertFalse(isPawnLastMovement("a2"))
        assertFalse(isPawnLastMovement("a7"))
        assertFalse(isPawnLastMovement("h3"))
        assertFalse(isPawnLastMovement("a6"))
    }

    @Test
    fun `should create all promotion movements for white pawn`() {
        val board = buildEmptyBoard()
        board.position("a7").piece = Pawn(Color.WHITE)
        val movements = board.createPromotionMovements(board.position("a7"), "a8")
        assertEquals(listOf("a7a8PQ", "a7a8PB", "a7a8PR", "a7a8PN"), movements)
    }

    @Test
    fun `should create all promotion movements for black pawn`() {
        val board = buildEmptyBoard()
        board.position("a2").piece = Pawn(Color.BLACK)
        val movements = board.createPromotionMovements(board.position("a2"), "a1")
        assertEquals(listOf("a2a1Pq", "a2a1Pb", "a2a1Pr", "a2a1Pn"), movements)
    }

    @Test
    fun `should create all promotion movements for white pawn with capture`() {
        val board = buildEmptyBoard()
        board.position("a7").piece = Pawn(Color.WHITE)
        board.position("b8").piece = Rook(Color.BLACK)
        val movements = board.createPromotionMovements(board.position("a7"), "b8")
        assertEquals(listOf("a7b8CPQ", "a7b8CPB", "a7b8CPR", "a7b8CPN"), movements)
    }

    @Test
    fun `should create all promotion movements for black pawn with capture`() {
        val board = buildEmptyBoard()
        board.position("a2").piece = Pawn(Color.BLACK)
        board.position("b1").piece = Rook(Color.WHITE)
        val movements = board.createPromotionMovements(board.position("a2"), "b1")
        assertEquals(listOf("a2b1CPq", "a2b1CPb", "a2b1CPr", "a2b1CPn"), movements)
    }

    @Test
    fun `should remove pawn movement and add promotion movements in white pawn movements`() {
        val board = buildEmptyBoard()
        board.position("b7").piece = Pawn(Color.WHITE)
        board.position("a8").piece = Rook(Color.BLACK)
        board.position("c8").piece = Rook(Color.BLACK)
        val legalMovementsList = listOf("b7a8C", "b7b8", "b7c8C")
        val newLegalMovementsList = runBlocking {
            board.handlePromotionInRange(legalMovementsList, board.position("b7"))
        }
        val expectedList = listOf(
            "b7a8CPQ", "b7a8CPB", "b7a8CPR", "b7a8CPN", "b7b8PQ", "b7b8PB", "b7b8PR",
            "b7b8PN", "b7c8CPQ", "b7c8CPB", "b7c8CPR", "b7c8CPN"
        )
        assertEquals(expectedList, newLegalMovementsList)
    }

    @Test
    fun `should remove pawn movement and add promotion movements in black pawn movements`() {
        val board = buildEmptyBoard()
        board.position("b2").piece = Pawn(Color.BLACK)
        board.position("a1").piece = Rook(Color.WHITE)
        board.position("c1").piece = Rook(Color.WHITE)
        val legalMovementsList = listOf("b2a1C", "b2b1", "b2c1C")
        val newLegalMovementsList = runBlocking {
            board.handlePromotionInRange(legalMovementsList, board.position("b2"))
        }
        val expectedList = listOf(
            "b2a1CPq", "b2a1CPb", "b2a1CPr", "b2a1CPn", "b2b1Pq", "b2b1Pb", "b2b1Pr",
            "b2b1Pn", "b2c1CPq", "b2c1CPb", "b2c1CPr", "b2c1CPn"
        )
        assertEquals(expectedList, newLegalMovementsList)
    }

    @Test
    fun `should not remove pawn movement and add promotion movements in white pawn movements`() {
        val board = buildEmptyBoard()
        board.position("b6").piece = Pawn(Color.WHITE)
        board.position("a7").piece = Rook(Color.BLACK)
        board.position("c7").piece = Rook(Color.BLACK)
        val legalMovementsList = listOf("b6a7C", "b6b7", "b6c7C")
        val newLegalMovementsList = runBlocking {
            board.handlePromotionInRange(legalMovementsList, board.position("b6"))
        }
        assertEquals(legalMovementsList, newLegalMovementsList)
    }

    @Test
    fun `should not remove pawn movement and add promotion movements in black pawn movements`() {
        val board = buildEmptyBoard()
        board.position("b3").piece = Pawn(Color.BLACK)
        board.position("a2").piece = Rook(Color.WHITE)
        board.position("c2").piece = Rook(Color.WHITE)
        val legalMovementsList = listOf("b3a2C", "b3b2", "b3c2C")
        val newLegalMovementsList = runBlocking {
            board.handlePromotionInRange(legalMovementsList, board.position("b3"))
        }
        assertEquals(legalMovementsList, newLegalMovementsList)
    }
}
