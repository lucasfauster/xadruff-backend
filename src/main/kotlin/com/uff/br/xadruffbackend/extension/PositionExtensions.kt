package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.model.Position
import com.uff.br.xadruffbackend.model.piece.Ghost
import com.uff.br.xadruffbackend.model.piece.Pawn
import com.uff.br.xadruffbackend.model.piece.Piece

fun Position.hasAllyPiece(originPiece: Piece?) =
    piece?.color == originPiece?.color && handleGhostPiece(originPiece)

fun Position.isEmpty(originPiece: Piece?) = piece == null && handleGhostPiece(originPiece)

fun Position.hasEnemyPiece(originPiece: Piece?) =
    piece?.let{
        it.color != originPiece?.color && handleGhostPiece(originPiece)
    } ?: false

fun Position.handleGhostPiece(originPiece: Piece?) =
    piece !is Ghost || originPiece is Pawn

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