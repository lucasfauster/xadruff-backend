package com.uff.br.xadruffbackend.calculator

import com.uff.br.xadruffbackend.utils.buildInitialBoard
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.mockito.Mockito


internal class AbstractLegalMovementsCalculatorTest{

    private val abstractLegalMovementsCalculator = Mockito.mock(
        AbstractLegalMovementsCalculator::class.java,
        Mockito.CALLS_REAL_METHODS
    )

    @Test
    fun `should return true in hasAlly if has an ally`(){
        val board = buildInitialBoard()
        val hasAlly = abstractLegalMovementsCalculator.hasAlly(7, 7, board)
        assert(hasAlly)
    }

    @Test
    fun `should return false in hasAlly if has an enemy`(){
        val board = buildInitialBoard()
        val hasAlly = abstractLegalMovementsCalculator.hasAlly(0, 0, board)
        assertFalse(hasAlly)
    }

    @Test
    fun `should return false in hasAlly if is empty`(){
        val board = buildInitialBoard()
        val hasAlly = abstractLegalMovementsCalculator.hasAlly(5, 5, board)
        assertFalse(hasAlly)
    }

    @Test
    fun `should return false in hasAlly if line or column is invalid`(){
        val board = buildInitialBoard()
        val hasAlly = abstractLegalMovementsCalculator.hasAlly(-1, 7, board)
        val hasAlly2 = abstractLegalMovementsCalculator.hasAlly(1, -7, board)
        val hasAlly3 = abstractLegalMovementsCalculator.hasAlly(-1, -7, board)
        assertFalse(hasAlly)
        assertFalse(hasAlly2)
        assertFalse(hasAlly3)
    }

    @Test
    fun `should return false in hasEnemy if has an ally`(){
        val board = buildInitialBoard()
        val hasEnemy = abstractLegalMovementsCalculator.hasEnemy(7, 7, board)
        assertFalse(hasEnemy)
    }

    @Test
    fun `should return true in hasEnemy if has an enemy`(){
        val board = buildInitialBoard()
        val hasEnemy = abstractLegalMovementsCalculator.hasEnemy(0, 0, board)
        assert(hasEnemy)
    }

    @Test
    fun `should return false in hasEnemy if is empty`(){
        val board = buildInitialBoard()
        val hasEnemy = abstractLegalMovementsCalculator.hasEnemy(5, 5, board)
        assertFalse(hasEnemy)
    }

    @Test
    fun `should return false in hasEnemy if line or column is invalid`(){
        val board = buildInitialBoard()
        val hasEnemy = abstractLegalMovementsCalculator.hasEnemy(-1, 7, board)
        val hasEnemy2 = abstractLegalMovementsCalculator.hasEnemy(1, -7, board)
        val hasEnemy3 = abstractLegalMovementsCalculator.hasEnemy(-1, -7, board)
        assertFalse(hasEnemy)
        assertFalse(hasEnemy2)
        assertFalse(hasEnemy3)
    }

    @Test
    fun `should return false in isEmpty if has an ally`(){
        val board = buildInitialBoard()
        val isEmpty = abstractLegalMovementsCalculator.isEmpty(0, 0, board)
        assertFalse(isEmpty)
    }

    @Test
    fun `should return false in isEmpty if has an enemy`(){
        val board = buildInitialBoard()
        val isEmpty = abstractLegalMovementsCalculator.isEmpty(7, 7, board)
        assertFalse(isEmpty)
    }

    @Test
    fun `should return true in isEmpty if is empty`(){
        val board = buildInitialBoard()
        val isEmpty = abstractLegalMovementsCalculator.isEmpty(5, 5, board)
        assert(isEmpty)
    }

    @Test
    fun `should return false in isEmpty if line or column is invalid`(){
        val board = buildInitialBoard()
        val isEmpty = abstractLegalMovementsCalculator.isEmpty(-1, 7, board)
        val isEmpty2 = abstractLegalMovementsCalculator.isEmpty(1, -7, board)
        val isEmpty3 = abstractLegalMovementsCalculator.isEmpty(-1, -7, board)
        assertFalse(isEmpty)
        assertFalse(isEmpty2)
        assertFalse(isEmpty3)
    }

    @Test
    fun `should return false in canMove if has an ally`(){
        val board = buildInitialBoard()
        val canMove = abstractLegalMovementsCalculator.canMove(7, 7, board)
        assertFalse(canMove)
    }

    @Test
    fun `should return true in canMove if has an enemy`(){
        val board = buildInitialBoard()
        val canMove = abstractLegalMovementsCalculator.canMove(0, 0, board)
        assert(canMove)
    }

    @Test
    fun `should return true in canMove if is empty`(){
        val board = buildInitialBoard()
        val canMove = abstractLegalMovementsCalculator.canMove(5, 5, board)
        assert(canMove)
    }

    @Test
    fun `should return false in canMove if line or column is invalid`(){
        val board = buildInitialBoard()
        val canMove = abstractLegalMovementsCalculator.canMove(-1, 7, board)
        val canMove2 = abstractLegalMovementsCalculator.canMove(1, -7, board)
        val canMove3 = abstractLegalMovementsCalculator.canMove(-1, -7, board)
        assertFalse(canMove)
        assertFalse(canMove2)
        assertFalse(canMove3)
    }

    @Test
    fun `should return C in buildAction if has enemy`(){
        val board = buildInitialBoard()
        val action = abstractLegalMovementsCalculator.buildAction(0, 0, board)
        assertEquals("C", action)

    }

    @Test
    fun `should return empty string in buildAction if has ally`(){
        val board = buildInitialBoard()
        val action = abstractLegalMovementsCalculator.buildAction(7, 7, board)
        assertEquals("", action)
    }

    @Test
    fun `should return empty string in buildAction if is empty`(){
        val board = buildInitialBoard()
        val action = abstractLegalMovementsCalculator.buildAction(5, 5, board)
        assertEquals("", action)
    }

    @Test
    fun `should return empty string in buildAction if line or column is invalid`(){
        val board = buildInitialBoard()

        val action = abstractLegalMovementsCalculator.buildAction(-1, 7, board)
        val action2 = abstractLegalMovementsCalculator.buildAction(1, -7, board)
        val action3 = abstractLegalMovementsCalculator.buildAction(-1, -7, board)
        assertEquals("", action)
        assertEquals("", action2)
        assertEquals("", action3)
    }


}