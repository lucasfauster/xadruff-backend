package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.dto.Board
import com.uff.br.xadruffbackend.dto.enum.Color
import com.uff.br.xadruffbackend.dto.response.BoardResponse
import com.uff.br.xadruffbackend.helper.buildGson

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

// fun Board.toFile() {
//    this.positions.forEach { row ->
//        row.forEach {
//            when (it.piece) {
//                null -> File("ia_fight.txt").appendText("_ ")
//                else -> File("ia_fight.txt").appendText("${it.piece?.value} ")
//            }
//        }
//        File("ia_fight.txt").appendText("\n")
//    }
//    File("ia_fight.txt").appendText("\n")
// }

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
