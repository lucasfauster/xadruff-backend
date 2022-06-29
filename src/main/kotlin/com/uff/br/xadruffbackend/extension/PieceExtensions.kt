package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.dto.Position
import com.uff.br.xadruffbackend.dto.piece.Piece

fun Piece.canMove(position: Position, hasMovement: Boolean = true, hasCapture: Boolean = true) =
    (hasMovement && position.isEmpty()) || (hasCapture && position.hasEnemyPiece(this))
