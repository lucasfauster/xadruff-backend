package com.uff.br.xadruffbackend.utils

import com.uff.br.xadruffbackend.extension.Position
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.Position
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.piece.Bishop
import com.uff.br.xadruffbackend.model.piece.King
import com.uff.br.xadruffbackend.model.piece.Knight
import com.uff.br.xadruffbackend.model.piece.Pawn
import com.uff.br.xadruffbackend.model.piece.Queen
import com.uff.br.xadruffbackend.model.piece.Rook

fun buildInitialBoard() = Board(
    listOf(
        listOf(
            Position(line = 0, column = 0, piece = Rook(Color.BLACK)),
            Position(line = 0, column = 1, piece = Knight(Color.BLACK)),
            Position(line = 0, column = 2, piece = Bishop(Color.BLACK)),
            Position(line = 0, column = 3, piece = Queen(Color.BLACK)),
            Position(line = 0, column = 4, piece = King(Color.BLACK)),
            Position(line = 0, column = 5, piece = Bishop(Color.BLACK)),
            Position(line = 0, column = 6, piece = Knight(Color.BLACK)),
            Position(line = 0, column = 7, piece = Rook(Color.BLACK))
        ),
        listOf(
            Position(line = 1, column = 0, piece = Pawn(Color.BLACK)),
            Position(line = 1, column = 1, piece = Pawn(Color.BLACK)),
            Position(line = 1, column = 2, piece = Pawn(Color.BLACK)),
            Position(line = 1, column = 3, piece = Pawn(Color.BLACK)),
            Position(line = 1, column = 4, piece = Pawn(Color.BLACK)),
            Position(line = 1, column = 5, piece = Pawn(Color.BLACK)),
            Position(line = 1, column = 6, piece = Pawn(Color.BLACK)),
            Position(line = 1, column = 7, piece = Pawn(Color.BLACK))
        ),
        listOf(
            Position(line = 2, column = 0, piece = null),
            Position(line = 2, column = 1, piece = null),
            Position(line = 2, column = 2, piece = null),
            Position(line = 2, column = 3, piece = null),
            Position(line = 2, column = 4, piece = null),
            Position(line = 2, column = 5, piece = null),
            Position(line = 2, column = 6, piece = null),
            Position(line = 2, column = 7, piece = null)
        ),
        listOf(
            Position(line = 3, column = 0, piece = null),
            Position(line = 3, column = 1, piece = null),
            Position(line = 3, column = 2, piece = null),
            Position(line = 3, column = 3, piece = null),
            Position(line = 3, column = 4, piece = null),
            Position(line = 3, column = 5, piece = null),
            Position(line = 3, column = 6, piece = null),
            Position(line = 3, column = 7, piece = null)
        ),
        listOf(
            Position("a4", piece = null),
            Position("b4", piece = null),
            Position("c4", piece = null),
            Position("d4", piece = null),
            Position("e4", piece = null),
            Position("f4", piece = null),
            Position("g4", piece = null),
            Position("h4", piece = null)
        ),
        listOf(
            Position("a3", piece = null),
            Position("b3", piece = null),
            Position("c3", piece = null),
            Position("d3", piece = null),
            Position("e3", piece = null),
            Position("f3", piece = null),
            Position("g3", piece = null),
            Position("h3", piece = null)
        ),
        listOf(
            Position("a2", piece = Pawn(Color.WHITE)),
            Position("b2", piece = Pawn(Color.WHITE)),
            Position("c2", piece = Pawn(Color.WHITE)),
            Position("d2", piece = Pawn(Color.WHITE)),
            Position("e2", piece = Pawn(Color.WHITE)),
            Position("f2", piece = Pawn(Color.WHITE)),
            Position("g2", piece = Pawn(Color.WHITE)),
            Position("h2", piece = Pawn(Color.WHITE))
        ),
        listOf(
            Position("a1", piece = Rook(Color.WHITE)),
            Position("b1", piece = Knight(Color.WHITE)),
            Position("c1", piece = Bishop(Color.WHITE)),
            Position("d1", piece = Queen(Color.WHITE)),
            Position("e1", piece = King(Color.WHITE)),
            Position("f1", piece = Bishop(Color.WHITE)),
            Position("g1", piece = Knight(Color.WHITE)),
            Position("h1", piece = Rook(Color.WHITE))
        )
    )
)

fun buildEmptyBoard() = Board(
    positions = mutableListOf(
        mutableListOf(
            Position(line = 0, column = 0, piece = null),
            Position(line = 0, column = 1, piece = null),
            Position(line = 0, column = 2, piece = null),
            Position(line = 0, column = 3, piece = null),
            Position(line = 0, column = 4, piece = null),
            Position(line = 0, column = 5, piece = null),
            Position(line = 0, column = 6, piece = null),
            Position(line = 0, column = 7, piece = null)
        ),
        mutableListOf(
            Position(line = 1, column = 0, piece = null),
            Position(line = 1, column = 1, piece = null),
            Position(line = 1, column = 2, piece = null),
            Position(line = 1, column = 3, piece = null),
            Position(line = 1, column = 4, piece = null),
            Position(line = 1, column = 5, piece = null),
            Position(line = 1, column = 6, piece = null),
            Position(line = 1, column = 7, piece = null)
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
        listOf(
            Position("a4", piece = null),
            Position("b4", piece = null),
            Position("c4", piece = null),
            Position("d4", piece = null),
            Position("e4", piece = null),
            Position("f4", piece = null),
            Position("g4", piece = null),
            Position("h4", piece = null)
        ),
        listOf(
            Position("a3", piece = null),
            Position("b3", piece = null),
            Position("c3", piece = null),
            Position("d3", piece = null),
            Position("e3", piece = null),
            Position("f3", piece = null),
            Position("g3", piece = null),
            Position("h3", piece = null)
        ),
        listOf(
            Position("a2", piece = null),
            Position("b2", piece = null),
            Position("c2", piece = null),
            Position("d2", piece = null),
            Position("e2", piece = null),
            Position("f2", piece = null),
            Position("g2", piece = null),
            Position("h2", piece = null)
        ),
        listOf(
            Position("a1", piece = null),
            Position("b1", piece = null),
            Position("c1", piece = null),
            Position("d1", piece = null),
            Position("e1", piece = null),
            Position("f1", piece = null),
            Position("g1", piece = null),
            Position("h1", piece = null)
        )
    ),
    turnColor = Color.WHITE
)
