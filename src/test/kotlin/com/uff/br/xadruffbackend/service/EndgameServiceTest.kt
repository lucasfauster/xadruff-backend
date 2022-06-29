package com.uff.br.xadruffbackend.service

import com.uff.br.xadruffbackend.dto.LegalMovements
import com.uff.br.xadruffbackend.dto.enum.Color
import com.uff.br.xadruffbackend.dto.enum.EndgameMessage
import com.uff.br.xadruffbackend.dto.piece.King
import com.uff.br.xadruffbackend.dto.piece.Queen
import com.uff.br.xadruffbackend.extension.changeTurn
import com.uff.br.xadruffbackend.extension.position
import com.uff.br.xadruffbackend.extension.toJsonString
import com.uff.br.xadruffbackend.extension.toLegalMovements
import com.uff.br.xadruffbackend.model.GameEntity
import com.uff.br.xadruffbackend.utils.buildEmptyBoard
import com.uff.br.xadruffbackend.utils.buildInitialBoard
import com.uff.br.xadruffbackend.utils.buildInitialLegalMovements
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class EndgameServiceTest {

    private val endgameService = EndgameService()

    @Test
    fun `should set winner to DRAW by draw moves in checkIfHasEndedByMovements`() {
        val board = buildEmptyBoard()
        val game = GameEntity(board = board.toJsonString())
        game.blackDrawMoves = 50
        game.whiteDrawMoves = 50
        endgameService.checkIfHasEndedByMovements(game)
        assertEquals(EndgameService.DRAW, game.winner)
        assertEquals(EndgameMessage.DRAW_RULE.message, game.endgameMessage)
    }

    @Test
    fun `should not set winner to DRAW by draw moves in checkIfHasEndedByMovements because white is below 50`() {
        val board = buildEmptyBoard()
        val game = GameEntity(board = board.toJsonString())
        game.blackDrawMoves = 50
        game.whiteDrawMoves = 49
        endgameService.checkIfHasEndedByMovements(game)
        assertNull(game.winner)
        assertNull(game.endgameMessage)
    }

    @Test
    fun `should not set winner to DRAW by draw moves in checkIfHasEndedByMovements because black is below 50`() {
        val board = buildEmptyBoard()
        val game = GameEntity(board = board.toJsonString())
        game.blackDrawMoves = 49
        game.whiteDrawMoves = 50
        endgameService.checkIfHasEndedByMovements(game)
        assertNull(game.winner)
        assertNull(game.endgameMessage)
    }

    @Test
    fun `should not set winner to DRAW by draw moves in checkIfHasEndedByMovements because both is below 50`() {
        val board = buildEmptyBoard()
        val game = GameEntity(board = board.toJsonString())
        game.blackDrawMoves = 49
        game.whiteDrawMoves = 49
        endgameService.checkIfHasEndedByMovements(game)
        assertNull(game.winner)
        assertNull(game.endgameMessage)
    }

    @Test
    fun `should set winner to DRAW by draw moves in checkIfGameEnded`() {
        val board = buildEmptyBoard()
        val game = GameEntity(board = board.toJsonString())
        game.legalMovements = buildInitialLegalMovements().toLegalMovements().toJsonString()
        game.blackDrawMoves = 50
        game.whiteDrawMoves = 50
        endgameService.checkIfGameEnded(game)
        assertEquals(EndgameService.DRAW, game.winner)
        assertEquals(EndgameMessage.DRAW_RULE.message, game.endgameMessage)
    }

    @Test
    fun `should set winner to BLACK by checkmate in endedByMate`() {
        val board = buildEmptyBoard()
        board.position("a1").piece = King(Color.WHITE)
        board.position("a3").piece = Queen(Color.BLACK)
        val game = GameEntity(board = board.toJsonString())
        endgameService.handleEndByMate(game)
        assertEquals(Color.BLACK.name, game.winner)
        assertEquals(EndgameMessage.CHECKMATE.message, game.endgameMessage)
    }

    @Test
    fun `should set winner to WHITE by checkmate in endedByMate`() {
        val board = buildEmptyBoard()
        board.position("a1").piece = King(Color.BLACK)
        board.position("a3").piece = Queen(Color.WHITE)
        board.changeTurn()
        val game = GameEntity(board = board.toJsonString())
        endgameService.handleEndByMate(game)
        assertEquals(Color.WHITE.name, game.winner)
        assertEquals(EndgameMessage.CHECKMATE.message, game.endgameMessage)
    }

    @Test
    fun `should set winner to BLACK by checkmate in checkIfGameEnded`() {
        val board = buildEmptyBoard()
        board.position("a1").piece = King(Color.WHITE)
        board.position("a3").piece = Queen(Color.BLACK)
        val game = GameEntity(
            board = board.toJsonString(),
            legalMovements = LegalMovements(mutableListOf()).toJsonString()
        )
        endgameService.checkIfGameEnded(game)
        assertEquals(Color.BLACK.name, game.winner)
        assertEquals(EndgameMessage.CHECKMATE.message, game.endgameMessage)
    }

    @Test
    fun `should set winner to WHITE by checkmate in checkIfGameEnded`() {
        val board = buildEmptyBoard()
        board.position("a1").piece = King(Color.BLACK)
        board.position("a3").piece = Queen(Color.WHITE)
        board.changeTurn()
        val game = GameEntity(
            board = board.toJsonString(),
            legalMovements = LegalMovements(mutableListOf()).toJsonString()
        )
        endgameService.checkIfGameEnded(game)
        assertEquals(Color.WHITE.name, game.winner)
        assertEquals(EndgameMessage.CHECKMATE.message, game.endgameMessage)
    }

    @Test
    fun `should set winner by DRAW for Stalemate in white turn in endedByMate`() {
        val board = buildInitialBoard()
        val game = GameEntity(board = board.toJsonString())
        endgameService.handleEndByMate(game)
        assertEquals(EndgameService.DRAW, game.winner)
        assertEquals(EndgameMessage.STALEMATE.message, game.endgameMessage)
    }

    @Test
    fun `should set winner by DRAW for Stalemate in black turn in endedByMate`() {
        val board = buildInitialBoard()
        board.changeTurn()
        val game = GameEntity(board = board.toJsonString())
        endgameService.handleEndByMate(game)
        assertEquals(EndgameService.DRAW, game.winner)
        assertEquals(EndgameMessage.STALEMATE.message, game.endgameMessage)
    }

    @Test
    fun `should set winner by DRAW for Stalemate in white turn in checkIfGameEnded`() {
        val board = buildInitialBoard()
        val legalMovements = mutableListOf<String>()
        val game = GameEntity(
            board = board.toJsonString(),
            legalMovements = legalMovements.toLegalMovements().toJsonString()
        )
        endgameService.checkIfGameEnded(game)
        assertEquals(EndgameService.DRAW, game.winner)
        assertEquals(EndgameMessage.STALEMATE.message, game.endgameMessage)
    }

    @Test
    fun `should set winner by DRAW for Stalemate in black turn in checkIfGameEnded`() {
        val board = buildInitialBoard()
        board.changeTurn()
        val legalMovements = mutableListOf<String>()
        val game = GameEntity(
            board = board.toJsonString(),
            legalMovements = legalMovements.toLegalMovements().toJsonString()
        )
        endgameService.checkIfGameEnded(game)
        assertEquals(EndgameService.DRAW, game.winner)
        assertEquals(EndgameMessage.STALEMATE.message, game.endgameMessage)
    }

    @Test
    fun `should not set winner in BLACK turn in checkIfGameEnded`() {
        val board = buildInitialBoard()
        board.changeTurn()
        val legalMovements = buildInitialLegalMovements()
        val game = GameEntity(
            board = board.toJsonString(),
            legalMovements = legalMovements.toLegalMovements().toJsonString()
        )
        endgameService.checkIfGameEnded(game)
        assertNull(game.winner)
        assertNull(game.endgameMessage)
    }

    @Test
    fun `should not set winner in WHITE turn in checkIfGameEnded`() {
        val board = buildInitialBoard()
        val legalMovements = buildInitialLegalMovements()
        val game = GameEntity(
            board = board.toJsonString(),
            legalMovements = legalMovements.toLegalMovements().toJsonString()
        )
        endgameService.checkIfGameEnded(game)
        assertNull(game.winner)
        assertNull(game.endgameMessage)
    }
}
