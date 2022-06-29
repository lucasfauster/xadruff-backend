package com.uff.br.xadruffbackend.dto

import com.uff.br.xadruffbackend.dto.piece.Piece

data class Position(
    val row: Int,
    val column: Int,
    var piece: Piece? = null,
    val action: String = ""
)
