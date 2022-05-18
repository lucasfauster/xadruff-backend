package com.uff.br.xadruffbackend.model

import com.uff.br.xadruffbackend.model.enum.Color

class Board(
    val positions: List<List<Position>>,
    var turnColor: Color = Color.WHITE
)
