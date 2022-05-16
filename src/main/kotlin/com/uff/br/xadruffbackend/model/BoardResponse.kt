package com.uff.br.xadruffbackend.model

class BoardResponse(val positions: List<List<String>>)

fun Board.toBoardResponse() : BoardResponse {
    return BoardResponse(positions = positions.toStringPositions()
    )
}

fun List<MutableList<Piece?>>.toStringPositions(): List<List<String>> {
    return map {
            line -> line.map {
            it?.value?.toString() ?: ""
        }
    }
}