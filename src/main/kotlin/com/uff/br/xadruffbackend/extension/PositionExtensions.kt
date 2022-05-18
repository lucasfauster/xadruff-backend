package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.model.Position
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.piece.Pawn

fun Position.hasAlly(turnColor: Color) =
    piece?.color == turnColor

fun Position.isEmpty() = piece == null

fun Position.hasEnemy(turnColor: Color) =
    piece?.let{
        it.color != turnColor
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

fun Position.toChessPosition(): String =
    column.toChessColumn() + line.toChessLine()

private fun Int.toChessLine() = (8 - this).toString()

private fun Int.toChessColumn() = 'a' + this