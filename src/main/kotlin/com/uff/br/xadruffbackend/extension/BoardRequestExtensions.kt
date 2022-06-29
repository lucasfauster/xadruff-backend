package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.dto.Board
import com.uff.br.xadruffbackend.dto.request.BoardRequest

fun BoardRequest.toBoard(): Board {
    return Board(
        positions = positions.toPositions(),
        turnColor = turnColor
    )
}
