package com.uff.br.xadruffbackend

import com.uff.br.xadruffbackend.calculator.LegalMovementsCalculator
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.enum.StartsBy
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.ChessResponse
import com.uff.br.xadruffbackend.model.GameEntity
import com.uff.br.xadruffbackend.model.LegalMovements
import com.uff.br.xadruffbackend.model.Position
import com.uff.br.xadruffbackend.model.piece.Bishop
import com.uff.br.xadruffbackend.model.piece.King
import com.uff.br.xadruffbackend.model.piece.Knight
import com.uff.br.xadruffbackend.model.piece.Pawn
import com.uff.br.xadruffbackend.model.piece.Queen
import com.uff.br.xadruffbackend.model.piece.Rook
import com.uff.br.xadruffbackend.model.toBoardResponse
import com.uff.br.xadruffbackend.model.toJsonString
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ChessService(
    private val chessRepository: ChessRepository,

    @Autowired
    private val legalMovementsCalculator: LegalMovementsCalculator
) {

    fun createNewGame(startBy: StartsBy): ChessResponse {
        val game = createInitialBoard()

        if(startBy == StartsBy.AI){
            playAITurn(game)
        }

        val playerLegalMovements = calculateLegalMovements(game.getBoard())
        game.legalMovements = playerLegalMovements.toJsonString()
        chessRepository.save(game)
        return ChessResponse(boardId = game.boardId,
            legalMovements = playerLegalMovements.movements,
            board = game.getBoard().toBoardResponse())
    }

    fun playAITurn(game: GameEntity){ // TODO chamar módulo de movimentação da IA
        // val legalMovements = calculateLegalMovements(game.boardPositions, color)
        // iaService.movePieces(game.boardPositions, blackLegalMovements)
        // game.allMovements = add movimento da IA
        // game.whiteDrawMoves = add movimento se n for peão
    }

    fun calculateLegalMovements(board: Board): LegalMovements {
        val legalMovementsList = legalMovementsCalculator.calculatePseudoLegalMoves(board)
        return LegalMovements(legalMovementsList)
    }

    fun createInitialBoard(): GameEntity {
        val positions = createInitialPositions()
        val board = Board(positions = positions)
        board.colorTurn = Color.WHITE
        val gameEntity = GameEntity(board = board.toJsonString())
        chessRepository.save(gameEntity)
        return gameEntity
    }

    fun createInitialPositions() =
        listOf(
            listOf(
                Position(line = 0, column = 0, piece = Rook(value = 'r')),
                Position(line = 0, column = 1, piece = Knight(value = 'n')),
                Position(line = 0, column = 2, piece = Bishop(value = 'b')),
                Position(line = 0, column = 3, piece = King(value = 'k')),
                Position(line = 0, column = 4, piece = Queen(value = 'q')),
                Position(line = 0, column = 5, piece = Bishop(value = 'b')),
                Position(line = 0, column = 6, piece = Knight(value = 'n')),
                Position(line = 0, column = 7, piece = Rook(value = 'r'))
            ),
            listOf(
                Position(line = 1, column = 0, piece = Pawn(value = 'p')),
                Position(line = 1, column = 1, piece = Pawn(value = 'p')),
                Position(line = 1, column = 2, piece = Pawn(value = 'p')),
                Position(line = 1, column = 3, piece = Pawn(value = 'p')),
                Position(line = 1, column = 4, piece = Pawn(value = 'p')),
                Position(line = 1, column = 5, piece = Pawn(value = 'p')),
                Position(line = 1, column = 6, piece = Pawn(value = 'p')),
                Position(line = 1, column = 7, piece = Pawn(value = 'p'))
            ),
            listOf(
                Position(line = 2, column = 0, piece = null),
                Position(line = 2, column = 1, piece = null),
                Position(line = 2, column = 2, piece = null),
                Position(line = 2, column = 3, piece = null),
                Position(line = 2, column = 4, piece = null),
                Position(line = 2, column = 5, piece = null),
                Position(line = 2, column = 6, piece = null),
                Position(line = 2, column = 7, piece = null)
            ),
            listOf(
                Position(line = 3, column = 0, piece = null),
                Position(line = 3, column = 1, piece = null),
                Position(line = 3, column = 2, piece = null),
                Position(line = 3, column = 3, piece = null),
                Position(line = 3, column = 4, piece = null),
                Position(line = 3, column = 5, piece = null),
                Position(line = 3, column = 6, piece = null),
                Position(line = 3, column = 7, piece = null)
            ),
            listOf(
                Position(line = 4, column = 0, piece = null),
                Position(line = 4, column = 1, piece = null),
                Position(line = 4, column = 2, piece = null),
                Position(line = 4, column = 3, piece = null),
                Position(line = 4, column = 4, piece = null),
                Position(line = 4, column = 5, piece = null),
                Position(line = 4, column = 6, piece = null),
                Position(line = 4, column = 7, piece = null)
            ),
            listOf(
                Position(line = 5, column = 0, piece = null),
                Position(line = 5, column = 1, piece = null),
                Position(line = 5, column = 2, piece = null),
                Position(line = 5, column = 3, piece = null),
                Position(line = 5, column = 4, piece = null),
                Position(line = 5, column = 5, piece = null),
                Position(line = 5, column = 6, piece = null),
                Position(line = 5, column = 7, piece = null)
            ),
            listOf(
                Position(line = 6, column = 0, piece = Pawn(value = 'P')),
                Position(line = 6, column = 1, piece = Pawn(value = 'P')),
                Position(line = 6, column = 2, piece = Pawn(value = 'P')),
                Position(line = 6, column = 3, piece = Pawn(value = 'P')),
                Position(line = 6, column = 4, piece = Pawn(value = 'P')),
                Position(line = 6, column = 5, piece = Pawn(value = 'P')),
                Position(line = 6, column = 6, piece = Pawn(value = 'P')),
                Position(line = 6, column = 7, piece = Pawn(value = 'P'))
            ),
            listOf(
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
