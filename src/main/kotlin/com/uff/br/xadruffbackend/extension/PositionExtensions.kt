package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.model.Position
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.piece.Ghost
import com.uff.br.xadruffbackend.model.piece.Pawn
import com.uff.br.xadruffbackend.model.piece.Piece

fun Position.hasAllyPiece(color: Color) =
    piece?.color == color && piece !is Ghost

fun Position.isEmpty() =
    piece == null || piece is Ghost

fun Position.hasEnemyPiece(originPiece: Piece?) =
    piece?.let {
        it.color != originPiece?.color && handleGhostPiece(originPiece)
    } ?: false

fun Position.handleGhostPiece(originPiece: Piece?) =
    piece !is Ghost || originPiece is Pawn

fun Position.handlePawnFirstMovementRange(): Int {
    val pawn = piece!! as Pawn
    return if (pawn.isBlackFirstMovement(line) || pawn.isWhiteFirstMovement(line)) {
        2
    } else {
        1
    }
}

fun List<List<Position>>.toStringPositions(): List<List<String>> {
    return map { line ->
        line.map {
            it.piece?.toString() ?: ""
        }
    }
}

fun Position.getMovementRange(): Int {
    return if (piece is Pawn) {
        handlePawnFirstMovementRange()
    } else {
        piece!!.movementRange
    }
}

fun Position.toChessPosition(): String =
    column.toChessColumn() + line.toChessLine()

fun List<List<Position>>.position(square: String) =
    position(square.last(), square.first())

private fun List<List<Position>>.position(row: Char, column: Char) =
    this[row.toPositionRow()][column.toPositionColumn()]

private fun Int.toChessLine() = (8 - this).toString()

private fun Int.toChessColumn() = 'a' + this

private fun Char.toPositionRow() = 8 - digitToInt()

private fun Char.toPositionColumn() = code - 97

fun Position(square: String, piece: Piece? = null, action: String = "") = Position(
    line = square.last().toPositionRow(),
    column = square.first().toPositionColumn(),
    piece = piece,
    action = action
)
