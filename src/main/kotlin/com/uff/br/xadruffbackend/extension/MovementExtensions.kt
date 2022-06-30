package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.dto.Board
import com.uff.br.xadruffbackend.dto.piece.Ghost

fun String.isCaptureMove(): Boolean {
    return this.contains('C')
}

fun String.isPromotionMove(): Boolean {
    return this.contains('P')
}

fun String.isEnPassantMove(board: Board): Boolean {
    return this.contains('C') &&
        board.position(this.futureStringPosition()).piece is Ghost

}
