package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.model.LegalMovements
import com.uff.br.xadruffbackend.model.Position
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class LegalMovementsExtensionsTest {

    @Test
    fun `should add move from a8 to d3`(){
        val legalMovements = LegalMovements(mutableListOf())
        legalMovements.addNewMove(Position(0, 0), Position(5, 3))
        assertEquals("a8d3", legalMovements.movements.first())
    }

    @Test
    fun `should add move from b8 to c2 with capture`(){
        val legalMovements = LegalMovements(mutableListOf())
        legalMovements.addNewMove(Position(0, 1), Position(6, 2), "C")
        assertEquals("b8c2C", legalMovements.movements.first())
    }
}