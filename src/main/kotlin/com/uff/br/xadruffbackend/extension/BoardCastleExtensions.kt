package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.extension.BoardMovementsCalculatorExtensions.calculatePseudoLegalMoves
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.LegalMovements
import com.uff.br.xadruffbackend.model.Position
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.piece.King
import com.uff.br.xadruffbackend.model.piece.Pawn
import com.uff.br.xadruffbackend.model.piece.Rook
import kotlin.math.absoluteValue

object BoardCastleExtensions {

    fun Board.handleCastleMovements(position: Position, legalMovements: LegalMovements) {
        val piece = position.piece
        if (piece is King && !piece.hasMoved) {
            val row = position.row.toChessRow()
            val movements = listOfNotNull(
                getCastleMovement("e$row", "a$row"),
                getCastleMovement("e$row", "h$row")
            )
            if (movements.isNotEmpty()) {
                legalMovements.movements.addAll(movements)
            }
        }
    }

    fun Board.isPossibleToCastle(piece: Rook, kingSquare: String, rookSquare: String) =
        !piece.hasMoved && isEmptyBetween(
            position(kingSquare),
            position(rookSquare)
        ) && !hasThreatInTheWay(rookSquare)

    fun Board.getCastleMovement(kingSquare: String, rookSquare: String): String? {
        val piece = position(rookSquare).piece
        return if (piece is Rook && isPossibleToCastle(piece, kingSquare, rookSquare)
        ) {
            "$kingSquare${getFutureCastleKingPosition(rookSquare)}"
        } else {
            null
        }
    }

    fun getFutureCastleKingPosition(rookSquare: String): String =
        when (rookSquare.first()) {
            'a' -> "c${rookSquare.last()}"
            else -> "g${rookSquare.last()}"
        }

    fun Board.isEmptyBetween(kingPosition: Position, rookPosition: Position): Boolean {
        val leftColumn = getLeftPosition(kingPosition, rookPosition).column
        val range = (kingPosition.column - rookPosition.column).absoluteValue - 1
        return (1..range).takeWhile {
            positions[rookPosition.row][leftColumn + it].isEmpty()
        }.count() == range
    }

    fun Board.hasThreatInTheWay(rookSquare: String): Boolean {
        val fakeBoard = this.deepCopy()
        fakeBoard.changeTurn()
        val legalMovements = fakeBoard.calculatePseudoLegalMoves(withCastle = false)
        val rookColumn = rookSquare.first()
        val rookRow = rookSquare.last()
        val rookWay = when (rookColumn) {
            'a' -> "d$rookRow"
            else -> "f$rookRow"
        }

        return legalMovements.movements.any { legalMovement ->
            legalMovement.futureStringPosition() == rookWay
        } || fakeBoard.hasPawnThreat(rookColumn)
    }

    fun Board.hasPawnThreat(rookColumn: Char): Boolean {
        val columnRange = when (rookColumn) {
            'a' -> 'c'..'e'
            else -> 'e'..'g'
        }
        val line = when (turnColor) {
            Color.WHITE -> '7'
            Color.BLACK -> '2'
        }

        return columnRange.any { column ->
            val piece = position("$column$line").piece
            piece is Pawn && piece.color == turnColor
        }
    }

    private fun getLeftPosition(
        kingPosition: Position,
        rookPosition: Position
    ) = if (kingPosition.column < rookPosition.column) {
        kingPosition
    } else {
        rookPosition
    }
}
