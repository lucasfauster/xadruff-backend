package com.uff.br.xadruffbackend.model

class BoardResponse(val positions: List<List<String>>)

fun Board.toBoardResponse() : BoardResponse {
    return BoardResponse(positions = positions.toStringPositions()
    )
}

fun List<MutableList<Position>>.toStringPositions(): List<List<String>> {
    return map {
            line -> line.map {
            it.piece?.value?.toString() ?: ""
        }
    }
}