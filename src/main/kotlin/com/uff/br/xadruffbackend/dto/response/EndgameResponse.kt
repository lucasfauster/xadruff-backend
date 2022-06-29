package com.uff.br.xadruffbackend.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

class EndgameResponse(
    val winner: String,

    @JsonProperty("endgame_message")
    val endgameMessage: String
)
