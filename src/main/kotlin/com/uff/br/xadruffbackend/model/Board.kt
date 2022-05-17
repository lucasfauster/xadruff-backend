package com.uff.br.xadruffbackend.model

import InterfaceAdapter
import buildGson
import com.google.gson.GsonBuilder
import com.uff.br.xadruffbackend.enum.Color
import com.uff.br.xadruffbackend.model.piece.Bishop
import com.uff.br.xadruffbackend.model.piece.King
import com.uff.br.xadruffbackend.model.piece.Knight
import com.uff.br.xadruffbackend.model.piece.Pawn
import com.uff.br.xadruffbackend.model.piece.Queen
import com.uff.br.xadruffbackend.model.piece.Rook


class Board {
    var positions: List<MutableList<Position>> = listOf(
        mutableListOf(
            Position(line = 0, column = 0, piece = Rook(value = 'r')),
            Position(line = 0, column = 1, piece = Knight(value = 'n')),
            Position(line = 0, column = 2, piece = Bishop(value = 'b')),
            Position(line = 0, column = 3, piece = King(value = 'k')),
            Position(line = 0, column = 4, piece = Queen(value = 'q')),
            Position(line = 0, column = 5, piece = Bishop(value = 'b')),
            Position(line = 0, column = 6, piece = Knight(value = 'n')),
            Position(line = 0, column = 7, piece = Rook(value = 'r'))
        ),
        mutableListOf(
            Position(line = 1, column = 0, piece = Pawn(value = 'p')),
            Position(line = 1, column = 1, piece = Pawn(value = 'p')),
            Position(line = 1, column = 2, piece = Pawn(value = 'p')),
            Position(line = 1, column = 3, piece = Pawn(value = 'p')),
            Position(line = 1, column = 4, piece = Pawn(value = 'p')),
            Position(line = 1, column = 5, piece = Pawn(value = 'p')),
            Position(line = 1, column = 6, piece = Pawn(value = 'p')),
            Position(line = 1, column = 7, piece = Pawn(value = 'p'))
        ),
        mutableListOf(
            Position(line = 2, column = 0, piece = null),
            Position(line = 2, column = 1, piece = null),
            Position(line = 2, column = 2, piece = null),
            Position(line = 2, column = 3, piece = null),
            Position(line = 2, column = 4, piece = null),
            Position(line = 2, column = 5, piece = null),
            Position(line = 2, column = 6, piece = null),
            Position(line = 2, column = 7, piece = null)
        ),
        mutableListOf(
            Position(line = 3, column = 0, piece = null),
            Position(line = 3, column = 1, piece = null),
            Position(line = 3, column = 2, piece = null),
            Position(line = 3, column = 3, piece = null),
            Position(line = 3, column = 4, piece = null),
            Position(line = 3, column = 5, piece = null),
            Position(line = 3, column = 6, piece = null),
            Position(line = 3, column = 7, piece = null)
        ),
        mutableListOf(
            Position(line = 4, column = 0, piece = null),
            Position(line = 4, column = 1, piece = null),
            Position(line = 4, column = 2, piece = null),
            Position(line = 4, column = 3, piece = null),
            Position(line = 4, column = 4, piece = null),
            Position(line = 4, column = 5, piece = null),
            Position(line = 4, column = 6, piece = null),
            Position(line = 4, column = 7, piece = null)
        ),
        mutableListOf(
            Position(line = 5, column = 0, piece = null),
            Position(line = 5, column = 1, piece = null),
            Position(line = 5, column = 2, piece = null),
            Position(line = 5, column = 3, piece = null),
            Position(line = 5, column = 4, piece = null),
            Position(line = 5, column = 5, piece = null),
            Position(line = 5, column = 6, piece = null),
            Position(line = 5, column = 7, piece = null)
        ),
        mutableListOf(
            Position(line = 6, column = 0, piece = Pawn(value = 'P')),
            Position(line = 6, column = 1, piece = Pawn(value = 'P')),
            Position(line = 6, column = 2, piece = Pawn(value = 'P')),
            Position(line = 6, column = 3, piece = Pawn(value = 'P')),
            Position(line = 6, column = 4, piece = Pawn(value = 'P')),
            Position(line = 6, column = 5, piece = Pawn(value = 'P')),
            Position(line = 6, column = 6, piece = Pawn(value = 'P')),
            Position(line = 6, column = 7, piece = Pawn(value = 'P'))
        ),
        mutableListOf(
            Position(line = 7, column = 0, piece = Rook(value = 'R')),
            Position(line = 7, column = 1, piece = Knight(value = 'N')),
            Position(line = 7, column = 2, piece = Bishop(value = 'B')),
            Position(line = 7, column = 3, piece = Queen(value = 'Q')),
            Position(line = 7, column = 4, piece = King(value = 'K')),
            Position(line = 7, column = 5, piece = Bishop(value = 'B')),
            Position(line = 7, column = 6, piece = Knight(value = 'N')),
            Position(line = 7, column = 7, piece = Rook(value = 'R'))
        ))

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