package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.model.Position
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.piece.Pawn

fun Position.hasAllyPiece(pieceColor: Color) =
    piece?.color == pieceColor

fun Position.isEmpty() = piece == null

fun Position.hasEnemyPiece(pieceColor: Color) =
    piece?.let{
        it.color != pieceColor
    } ?: false

fun Position.handlePawnFirstMovementRange(): Int {
    val pawn = piece!! as Pawn
    return if(pawn.isBlackFirstMovement(line) || pawn.isWhiteFirstMovement(line)){
        2
    } else {
        1
    }
}

fun List<List<Position>>.toStringPositions(): List<List<String>> {
    return map {
        line -> line.map {
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

private fun Int.toChessLine() = (8 - this).toString()

private fun Int.toChessColumn() = 'a' + this