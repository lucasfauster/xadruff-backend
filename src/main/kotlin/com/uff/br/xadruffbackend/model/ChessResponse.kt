package com.uff.br.xadruffbackend.model

data class ChessResponse(
    val boardId: String,
    val board: BoardResponse,
    val legalMovements: List<String>
)