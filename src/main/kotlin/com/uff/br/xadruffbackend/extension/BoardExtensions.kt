package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.helper.buildGson
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.BoardResponse

fun Board.toBoardResponse() : BoardResponse {
    return BoardResponse(positions = positions.toStringPositions()
    )
}

fun Board.toJsonString(): String {
    val gson = buildGson()
    return gson.toJson(this)
}

fun Board.position(square: String) =
    positions[8 - square.last().digitToInt()][square.first().code - 97]