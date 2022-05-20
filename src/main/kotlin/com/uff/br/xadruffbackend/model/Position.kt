package com.uff.br.xadruffbackend.model

import com.uff.br.xadruffbackend.model.piece.Piece

data class Position(
    val row: Int,
    val column: Int,
    var piece: Piece? = null,
    val action: String = ""
)
