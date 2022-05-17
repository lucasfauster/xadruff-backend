package com.uff.br.xadruffbackend.model

import buildGson
import com.uff.br.xadruffbackend.model.enum.Color

data class Board(
    val positions: List<List<Position>>,
    var colorTurn: Color = Color.WHITE
)

fun Board.toJsonString(): String {
    val gson = buildGson()
    return gson.toJson(this)
}