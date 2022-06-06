package com.uff.br.xadruffbackend

import com.uff.br.xadruffbackend.ai.AIService
import org.junit.jupiter.api.Test

class MinimaxTest {

    private val boardMock = BoardMock()
    private val minimax = AIService()

    @Test
    fun `should return a move`() {
        val move = minimax.play(2, boardMock)

        assert(move is String)
    }
}
