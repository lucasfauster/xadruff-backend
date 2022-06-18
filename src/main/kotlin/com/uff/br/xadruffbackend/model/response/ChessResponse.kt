package com.uff.br.xadruffbackend.model.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(Include.NON_NULL)
data class ChessResponse(

    @JsonProperty("board_id")
    val boardId: String,

    val board: BoardResponse,

    @JsonProperty("legal_movements")
    val legalMovements: Map<String, List<String>>,

    @JsonProperty("ai_movement")
    val aiMovement: String?,

    @JsonProperty("king_in_check")
    val kingInCheck: String?,

    val endgame: EndgameResponse?
)
