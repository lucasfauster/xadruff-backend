package com.uff.br.xadruffbackend.model

import buildGson
import com.uff.br.xadruffbackend.model.enum.Color


class Board(val positions: List<List<Position>>) {
    var colorTurn = Color.WHITE

    //TODO: pensar em como automatizar a atualização dos valores após aplicação de um movimento no board
    // talvez podemos apenas chamar as funções no final da função que aplica o movimento no boar
    var legalMoves: MutableList<String> = mutableListOf("a2a3", "a2a4", "b2b3", "b2b4", "c2c3", "c2c4", "d2d3", "d2d4",
        "e2e3", "e2e4", "f2f3", "f2f4", "g2g3", "g2g4", "h2h3", "h2h4",
        "b1a3", "b1c3", "g1f3", "g1h3")

    var legalMovesMap = mutableMapOf<String,List<String>>()
}

fun Board.toJsonString(): String {
    val gson = buildGson()
    return gson.toJson(this)
}