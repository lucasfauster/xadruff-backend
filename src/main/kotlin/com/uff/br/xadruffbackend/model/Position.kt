package com.uff.br.xadruffbackend.model

import com.uff.br.xadruffbackend.model.piece.Piece

data class Position(
    val line: Int,
    val column: Int,
    var piece: Piece?,
    val action: String = "")