package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.dto.Position
import com.uff.br.xadruffbackend.dto.enum.Color
import com.uff.br.xadruffbackend.dto.piece.Bishop
import com.uff.br.xadruffbackend.dto.piece.Ghost
import com.uff.br.xadruffbackend.dto.piece.King
import com.uff.br.xadruffbackend.dto.piece.Knight
import com.uff.br.xadruffbackend.dto.piece.Pawn
import com.uff.br.xadruffbackend.dto.piece.Piece
import com.uff.br.xadruffbackend.dto.piece.Queen
import com.uff.br.xadruffbackend.dto.piece.Rook
import com.uff.br.xadruffbackend.extension.BoardPromotionExtensions.PROMOTION_CHAR
import java.security.InvalidParameterException

fun String.futureStringPosition() = slice(StringUtils.SECOND_POSITION)

fun String.futureStringPositionAndAction() = drop(StringUtils.ORIGINAL_POSITION_LENGTH)

fun String.originalStringPosition() = slice(StringUtils.FIRST_POSITION)

fun String.actions() = drop(StringUtils.MOVEMENT_TOTAL_LENGTH)

fun String.promotionPiece() = substringAfter(PROMOTION_CHAR).first()

fun List<List<String>>.toPositions(): List<List<Position>> {
    val boardPositions = mutableListOf<MutableList<Position>>()
    for (rowIndex in this.indices) {
        val rowPositions = mutableListOf<Position>()
        for (columnIndex in this[rowIndex].indices) {
            rowPositions.add(
                Position(
                    row = rowIndex,
                    column = columnIndex,
                    piece = this[rowIndex][columnIndex].getPiece()
                )
            )
        }
        boardPositions.add(rowPositions)
    }
    return boardPositions
}

fun String.getPiece(): Piece? {

    if (isBlank()) {
        return null
    }

    val color = if (first().isUpperCase()) {
        Color.WHITE
    } else {
        Color.BLACK
    }
    return when (lowercase()) {
        "r" -> Rook(color)
        "b" -> Bishop(color)
        "q" -> Queen(color)
        "p" -> Pawn(color)
        "n" -> Knight(color)
        "k" -> King(color)
        "g" -> Ghost(color)
        else -> throw InvalidParameterException("Piece $this is not a valid piece.")
    }
}

@Suppress("MagicNumber")
object StringUtils {
    const val ORIGINAL_POSITION_LENGTH = 2
    val FIRST_POSITION = 0..1
    val SECOND_POSITION = 2..3
    const val MOVEMENT_TOTAL_LENGTH = 4
}
