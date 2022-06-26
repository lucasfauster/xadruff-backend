package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.extension.BoardPromotionExtensions.PROMOTION_CHAR

fun String.futureStringPosition() = slice(StringUtils.SECOND_POSITION)

fun String.futureStringPositionAndAction() = drop(StringUtils.ORIGINAL_POSITION_LENGTH)

fun String.originalStringPosition() = slice(StringUtils.FIRST_POSITION)

fun String.actions() = drop(StringUtils.MOVEMENT_TOTAL_LENGTH)

fun String.promotionPiece() = substringAfter(PROMOTION_CHAR).first()

@Suppress("MagicNumber")
object StringUtils {
    const val ORIGINAL_POSITION_LENGTH = 2
    val FIRST_POSITION = 0..1
    val SECOND_POSITION = 2..3
    const val MOVEMENT_TOTAL_LENGTH = 4
}
