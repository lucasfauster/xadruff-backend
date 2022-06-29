package com.uff.br.xadruffbackend.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.uff.br.xadruffbackend.dto.enum.Color

class BoardRequest(
    val positions: List<List<String>>,

    @JsonProperty("turn_color")
    val turnColor: Color
)
