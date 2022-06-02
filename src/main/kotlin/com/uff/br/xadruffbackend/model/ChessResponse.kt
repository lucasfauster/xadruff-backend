package com.uff.br.xadruffbackend.model

data class ChessResponse(
    val boardId: String,
    val board: BoardResponse,
    val legalMovements: Map<String, List<String>>,
    val aiMovement: String
)
