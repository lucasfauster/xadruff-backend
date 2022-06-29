package com.uff.br.xadruffbackend.ai

import com.uff.br.xadruffbackend.dto.enum.Color
import com.uff.br.xadruffbackend.dto.piece.Bishop
import com.uff.br.xadruffbackend.dto.piece.Pawn
import com.uff.br.xadruffbackend.dto.piece.Queen
import com.uff.br.xadruffbackend.dto.piece.Rook
import com.uff.br.xadruffbackend.extension.position
import com.uff.br.xadruffbackend.service.EnPassantService
import com.uff.br.xadruffbackend.service.MovementService
import com.uff.br.xadruffbackend.utils.buildEmptyBoard
import com.uff.br.xadruffbackend.utils.buildInitialBoard
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AIServiceTest {

    private val enPassantService: EnPassantService = EnPassantService()
    private val movementService: MovementService = MovementService(enPassantService)
    private val aiService = AIService(movementService)

    @Test
    fun `should return value of intial board evaluation`() {
        val evaluation = aiService.evaluate(buildInitialBoard())

        assertEquals(0, evaluation)
    }

    @Test
    fun `should return better evaluation when king pawn moved then intial board`() {
        val board = buildInitialBoard()
        board.position("e2").piece = null
        board.position("e4").piece = Pawn(Color.WHITE)
        val evaluation = aiService.evaluate(board)

        assert(0 < evaluation)
    }

    @Test
    fun `should return worse evaluation when king is threatened then intial board`() {
        val board = buildInitialBoard()
        board.position("f2").piece = null
        board.position("d8").piece = null
        board.position("h4").piece = Queen(Color.BLACK)
        val evaluation = aiService.evaluate(board)
        assert(0 > evaluation)
    }

    @Test
    fun `should return a max positive value for board evaluation`() {
        val board = buildEmptyBoard()
        board.position("b2").piece = Bishop(Color.WHITE)
        board.position("c3").piece = Rook(Color.BLACK)
        val maxValue = aiService.max(1, board, -1000, 1000)
        assert(0 < maxValue)
    }

    @Test
    fun `should return a min negative value for board evaluation`() {
        val board = buildEmptyBoard()
        board.position("b2").piece = Bishop(Color.WHITE)
        board.position("c3").piece = Rook(Color.BLACK)
        val maxValue = aiService.min(1, board, -1000, 1000)
        assert(0 > maxValue)
    }
}
