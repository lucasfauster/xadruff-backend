package com.uff.br.xadruffbackend.model.response

data class ChessResponse(
    val boardId: String,
    val board: BoardResponse,
    val legalMovements: Map<String, List<String>>,
    val aiMovement: String?,
    val kingInCheck: String?,
    val endgame: EndgameResponse?
)
