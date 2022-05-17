package com.uff.br.xadruffbackend

import com.uff.br.xadruffbackend.model.enum.StartsBy
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.GameEntity
import com.uff.br.xadruffbackend.model.Position
import com.uff.br.xadruffbackend.model.piece.Bishop
import com.uff.br.xadruffbackend.model.piece.King
import com.uff.br.xadruffbackend.model.piece.Knight
import com.uff.br.xadruffbackend.model.piece.Pawn
import com.uff.br.xadruffbackend.model.piece.Queen
import com.uff.br.xadruffbackend.model.piece.Rook
import com.uff.br.xadruffbackend.model.toJsonString
import com.uff.br.xadruffbackend.model.toStringPositions
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ChessServiceTest{

    companion object {
        val initialBoardPositions: List<MutableList<Position>> = listOf(
                mutableListOf(
                    Position(line = 0, column = 0, piece = Rook(value = 'r')),
                    Position(line = 0, column = 1, piece = Knight(value = 'n')),
                    Position(line = 0, column = 2, piece = Bishop(value = 'b')),
                    Position(line = 0, column = 3, piece = King(value = 'k')),
                    Position(line = 0, column = 4, piece = Queen(value = 'q')),
                    Position(line = 0, column = 5, piece = Bishop(value = 'b')),
                    Position(line = 0, column = 6, piece = Knight(value = 'n')),
                    Position(line = 0, column = 7, piece = Rook(value = 'r'))
                ),
                mutableListOf(
                    Position(line = 1, column = 0, piece = Pawn(value = 'p')),
                    Position(line = 1, column = 1, piece = Pawn(value = 'p')),
                    Position(line = 1, column = 2, piece = Pawn(value = 'p')),
                    Position(line = 1, column = 3, piece = Pawn(value = 'p')),
                    Position(line = 1, column = 4, piece = Pawn(value = 'p')),
                    Position(line = 1, column = 5, piece = Pawn(value = 'p')),
                    Position(line = 1, column = 6, piece = Pawn(value = 'p')),
                    Position(line = 1, column = 7, piece = Pawn(value = 'p'))
                ),
                mutableListOf(
                    Position(line = 2, column = 0, piece = null),
                    Position(line = 2, column = 1, piece = null),
                    Position(line = 2, column = 2, piece = null),
                    Position(line = 2, column = 3, piece = null),
                    Position(line = 2, column = 4, piece = null),
                    Position(line = 2, column = 5, piece = null),
                    Position(line = 2, column = 6, piece = null),
                    Position(line = 2, column = 7, piece = null)
                ),
                mutableListOf(
                    Position(line = 3, column = 0, piece = null),
                    Position(line = 3, column = 1, piece = null),
                    Position(line = 3, column = 2, piece = null),
                    Position(line = 3, column = 3, piece = null),
                    Position(line = 3, column = 4, piece = null),
                    Position(line = 3, column = 5, piece = null),
                    Position(line = 3, column = 6, piece = null),
                    Position(line = 3, column = 7, piece = null)
                ),
                mutableListOf(
                    Position(line = 4, column = 0, piece = null),
                    Position(line = 4, column = 1, piece = null),
                    Position(line = 4, column = 2, piece = null),
                    Position(line = 4, column = 3, piece = null),
                    Position(line = 4, column = 4, piece = null),
                    Position(line = 4, column = 5, piece = null),
                    Position(line = 4, column = 6, piece = null),
                    Position(line = 4, column = 7, piece = null)
                ),
                mutableListOf(
                    Position(line = 5, column = 0, piece = null),
                    Position(line = 5, column = 1, piece = null),
                    Position(line = 5, column = 2, piece = null),
                    Position(line = 5, column = 3, piece = null),
                    Position(line = 5, column = 4, piece = null),
                    Position(line = 5, column = 5, piece = null),
                    Position(line = 5, column = 6, piece = null),
                    Position(line = 5, column = 7, piece = null)
                ),
                mutableListOf(
                    Position(line = 6, column = 0, piece = Pawn(value = 'P')),
                    Position(line = 6, column = 1, piece = Pawn(value = 'P')),
                    Position(line = 6, column = 2, piece = Pawn(value = 'P')),
                    Position(line = 6, column = 3, piece = Pawn(value = 'P')),
                    Position(line = 6, column = 4, piece = Pawn(value = 'P')),
                    Position(line = 6, column = 5, piece = Pawn(value = 'P')),
                    Position(line = 6, column = 6, piece = Pawn(value = 'P')),
                    Position(line = 6, column = 7, piece = Pawn(value = 'P'))
                ),
                mutableListOf(
                    Position(line = 7, column = 0, piece = Rook(value = 'R')),
                    Position(line = 7, column = 1, piece = Knight(value = 'N')),
                    Position(line = 7, column = 2, piece = Bishop(value = 'B')),
                    Position(line = 7, column = 3, piece = Queen(value = 'Q')),
                    Position(line = 7, column = 4, piece = King(value = 'K')),
                    Position(line = 7, column = 5, piece = Bishop(value = 'B')),
                    Position(line = 7, column = 6, piece = Knight(value = 'N')),
                    Position(line = 7, column = 7, piece = Rook(value = 'R'))
                )
        )
    }

    private val chessRepository = mockk<ChessRepository>()
    private val chessService: ChessService = ChessService(chessRepository)

    init {
        every {
            chessRepository.save(any<GameEntity>())
        } returns mockGameEntity()
    }

    @Test
    fun `should create an initial board`(){

        val gameEntity = chessService.createInitialBoard()

        assertBoard(boardPositions = gameEntity.getBoard().positions,
            expectedBoardPositions = initialBoardPositions)
        assertNull(gameEntity.allMovements)
        assertNull(gameEntity.legalMovements)
        assertNull(gameEntity.winner)
        assertEquals(gameEntity.blackDrawMoves, 0)
        assertEquals(gameEntity.whiteDrawMoves, 0)
    }

    @Test
    fun `should create a new game with player playing first`(){
        val chessResponse = chessService.createNewGame(StartsBy.PLAYER)

        assertNotNull(chessResponse.legalMovements)
        assertNotNull(chessResponse.boardId)
        assertBoardResponse(boardPositions = chessResponse.board.positions,
            expectedBoardPositions = initialBoardPositions.toStringPositions())

    }

    @Test
    fun `should create a new game with AI playing first`(){
        val chessResponse = chessService.createNewGame(StartsBy.AI)
        assertNotNull(chessResponse.legalMovements)
        assertNotNull(chessResponse.boardId)

        // assertNotEquals(initialBoardPositions, chessResponse.board.positions) TODO após implementar movimentação da IA
    }

    private fun assertBoard(boardPositions: List<List<Position>>, expectedBoardPositions: List<List<Position>>) {
        for(line in 0..7) {
            for(column in 0..7) {
                assertEquals(boardPositions[line][column].piece?.value,
                    expectedBoardPositions[line][column].piece?.value
                )
            }
        }
    }

    private fun assertBoardResponse(boardPositions: List<List<String>>,
                                    expectedBoardPositions: List<List<String>>) {
        for(line in 0..7) {
            for(column in 0..7) {
                assertEquals(boardPositions[line][column], expectedBoardPositions[line][column])
            }
        }
    }


    private fun mockGameEntity() = GameEntity(board = Board().toJsonString())

}