package com.uff.br.xadruffbackend

import com.uff.br.xadruffbackend.model.BoardMock
import com.uff.br.xadruffbackend.model.Minimax
import org.junit.jupiter.api.Test

class MinimaxTest {

    private val boardMock = BoardMock()
    private val minimax = Minimax()

    @Test
    fun `should return a move`(){
        val move = minimax.getMovement(2, boardMock)

        assert(move is String )
    }
}