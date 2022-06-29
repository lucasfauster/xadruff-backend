package com.uff.br.xadruffbackend.model.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.uff.br.xadruffbackend.dto.response.ChessResponse

class GameResponse(
    @JsonProperty(value = "chess_response")
    val chessResponse: ChessResponse,
    @JsonProperty(value = "white_draw_moves")
    val whiteDrawMoves: Int,
    @JsonProperty(value = "black_draw_moves")
    val blackDrawMoves: Int,
    @JsonProperty(value = "all_moves")
    val allMoves: String
)
