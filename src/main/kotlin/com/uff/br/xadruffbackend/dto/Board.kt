package com.uff.br.xadruffbackend.dto

import com.uff.br.xadruffbackend.dto.enum.Color

class Board(
    val positions: List<List<Position>>,
    var turnColor: Color = Color.WHITE
)

const val BOARD_SIZE = 8
