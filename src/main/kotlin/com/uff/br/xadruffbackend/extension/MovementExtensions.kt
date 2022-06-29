package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.dto.Board

fun String.isCaptureMove(): Boolean {
    return this.contains('C')
}

fun String.isPromotionMove(): Boolean {
    return this.contains('P')
}

fun String.isEnpassantMove(board: Board): Boolean {
    return this.contains('C') &&
        board.position(this.futureStringPosition()).piece == null
}
