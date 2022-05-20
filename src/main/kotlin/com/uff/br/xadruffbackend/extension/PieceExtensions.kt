package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.model.Position
import com.uff.br.xadruffbackend.model.piece.Piece

fun Piece.canMove(position: Position, hasMovement: Boolean = true, hasCapture: Boolean = true) =
    (hasMovement && position.isEmpty(this)) || (hasCapture && position.hasEnemyPiece(this))
