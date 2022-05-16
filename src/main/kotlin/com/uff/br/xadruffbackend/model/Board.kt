package com.uff.br.xadruffbackend.model

import com.google.gson.Gson

class Board {
    var positions: List<MutableList<Piece?>> = listOf(
        mutableListOf(
            Piece(value = 'r', position = Position(0, 0)),
            Piece(value = 'n', position = Position(0, 1)),
            Piece(value = 'b', position = Position(0, 2)),
            Piece(value = 'k', position = Position(0, 3)),
            Piece(value = 'q', position = Position(0, 4)),
            Piece(value = 'b', position = Position(0, 5)),
            Piece(value = 'n', position = Position(0, 6)),
            Piece(value = 'r', position = Position(0, 7))
        ),
        mutableListOf(
            Piece(value = 'p', position = Position(1, 0)),
            Piece(value = 'p', position = Position(1, 1)),
            Piece(value = 'p', position = Position(1, 2)),
            Piece(value = 'p', position = Position(1, 3)),
            Piece(value = 'p', position = Position(1, 4)),
            Piece(value = 'p', position = Position(1, 5)),
            Piece(value = 'p', position = Position(1, 6)),
            Piece(value = 'p', position = Position(1, 7))
        ),
        mutableListOf(null, null, null, null, null, null, null, null),
        mutableListOf(null, null, null, null, null, null, null, null),
        mutableListOf(null, null, null, null, null, null, null, null),
        mutableListOf(null, null, null, null, null, null, null, null),
        mutableListOf(
            Piece(value = 'P', position = Position(6, 0)),
            Piece(value = 'P', position = Position(6, 1)),
            Piece(value = 'P', position = Position(6, 2)),
            Piece(value = 'P', position = Position(6, 3)),
            Piece(value = 'P', position = Position(6, 4)),
            Piece(value = 'P', position = Position(6, 5)),
            Piece(value = 'P', position = Position(6, 6)),
            Piece(value = 'P', position = Position(6, 7))
        ),
        mutableListOf(
            Piece(value = 'R', position = Position(7, 0)),
            Piece(value = 'N', position = Position(7, 1)),
            Piece(value = 'B', position = Position(7, 2)),
            Piece(value = 'Q', position = Position(7, 3)),
            Piece(value = 'K', position = Position(7, 4)),
            Piece(value = 'B', position = Position(7, 5)),
            Piece(value = 'N', position = Position(7, 6)),
            Piece(value = 'R', position = Position(7, 7))
        ))

    private val colorTurn = true

    //TODO: pensar em como automatizar a atualização dos valores após aplicação de um movimento no board
    // talvez podemos apenas chamar as funções no final da função que aplica o movimento no boar
    var legalMoves: MutableList<String> = mutableListOf("a2a3", "a2a4", "b2b3", "b2b4", "c2c3", "c2c4", "d2d3", "d2d4",
        "e2e3", "e2e4", "f2f3", "f2f4", "g2g3", "g2g4", "h2h3", "h2h4",
        "b1a3", "b1c3", "g1f3", "g1h3")

    var legalMovesMap = mutableMapOf<String,List<String>>()
}

fun Board.toJsonString(): String = Gson().toJson(this)