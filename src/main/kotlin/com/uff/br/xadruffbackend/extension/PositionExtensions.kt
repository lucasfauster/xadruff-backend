package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.dto.Position
import com.uff.br.xadruffbackend.dto.enum.Color
import com.uff.br.xadruffbackend.dto.piece.Ghost
import com.uff.br.xadruffbackend.dto.piece.Pawn
import com.uff.br.xadruffbackend.dto.piece.Piece

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
    return if (pawn.isBlackFirstMovement(row) || pawn.isWhiteFirstMovement(row)) {
        2
    } else {
        1
    }
}

fun List<List<Position>>.toStringPositions(): List<List<String>> {
    return map { row ->
        row.map {
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
    column.toChessColumn() + row.toChessRow()

fun List<List<Position>>.position(square: String) =
    position(square.last(), square.first())

private fun List<List<Position>>.position(row: Char, column: Char) =
    this[row.toPositionRow()][column.toPositionColumn()]

fun Int.toChessRow() = (RowColumnConstants.ROW_CONSTANT - this).toString()

fun Int.toChessColumn() = RowColumnConstants.COLUMN_INT_CONSTANT + this

fun Char.toPositionRow() = RowColumnConstants.ROW_CONSTANT - digitToInt()

fun Char.toPositionColumn() = code - RowColumnConstants.COLUMN_CODE_CONSTANT

fun position(square: String, piece: Piece? = null, action: String = "") = Position(
    row = square.last().toPositionRow(),
    column = square.first().toPositionColumn(),
    piece = piece,
    action = action
)

fun Position.getGhostCaptureIfExists() =
    if (piece is Ghost) {
        when (piece!!.color) {
            Color.WHITE -> "E" + Position(row - 1, column).toChessPosition()
            Color.BLACK -> "E" + Position(row + 1, column).toChessPosition()
        }
    } else {
        ""
    }

object RowColumnConstants {
    const val ROW_CONSTANT = 8
    const val COLUMN_INT_CONSTANT = 'a'
    const val COLUMN_CODE_CONSTANT = 97
}
