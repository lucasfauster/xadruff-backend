package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.helper.buildGson
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.response.BoardResponse

fun Board.toBoardResponse(): BoardResponse {
    return BoardResponse(
        positions = positions.toStringPositions()
    )
}

fun Board.toJsonString(): String {
    val gson = buildGson()
    return gson.toJson(this)
}

fun Board.position(square: String) =
    positions.position(square)

fun Board.changeTurn() {
    turnColor = if (turnColor == Color.WHITE) {
        Color.BLACK
    } else {
        Color.WHITE
    }
}

fun Board.deepCopy(): Board {
    val gson = buildGson()
    return gson.fromJson(gson.toJson(this), this::class.java)
}

fun Board.filterOnlyCaptureDirections() {
    positions.parallelStream().forEach {
        it.parallelStream().forEach { position ->
            position.piece?.directions?.forEach { direction ->
                direction.hasMovement = false
            }
        }
    }
}
