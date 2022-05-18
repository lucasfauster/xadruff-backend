package com.uff.br.xadruffbackend.model

import com.uff.br.xadruffbackend.helper.buildGson
import com.uff.br.xadruffbackend.model.enum.Color

data class Board(
    val positions: List<List<Position>>,
    var colorTurn: Color = Color.WHITE
){
    fun position(house: String) =
        positions[8 - house.last().digitToInt()][house.first().code - 97]
}

fun Board.toJsonString(): String {
    val gson = buildGson()
    return gson.toJson(this)
}
