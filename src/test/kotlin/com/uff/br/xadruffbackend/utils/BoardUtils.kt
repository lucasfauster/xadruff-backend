package com.uff.br.xadruffbackend.utils

import com.uff.br.xadruffbackend.extension.position
import com.uff.br.xadruffbackend.model.Board
import com.uff.br.xadruffbackend.model.Position
import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.model.piece.Bishop
import com.uff.br.xadruffbackend.model.piece.King
import com.uff.br.xadruffbackend.model.piece.Knight
import com.uff.br.xadruffbackend.model.piece.Pawn
import com.uff.br.xadruffbackend.model.piece.Queen
import com.uff.br.xadruffbackend.model.piece.Rook

@Suppress("LongMethod")
fun buildInitialBoard() = Board(
    listOf(
        listOf(
            Position(row = 0, column = 0, piece = Rook(Color.BLACK)),
            Position(row = 0, column = 1, piece = Knight(Color.BLACK)),
            Position(row = 0, column = 2, piece = Bishop(Color.BLACK)),
            Position(row = 0, column = 3, piece = Queen(Color.BLACK)),
            Position(row = 0, column = 4, piece = King(Color.BLACK)),
            Position(row = 0, column = 5, piece = Bishop(Color.BLACK)),
            Position(row = 0, column = 6, piece = Knight(Color.BLACK)),
            Position(row = 0, column = 7, piece = Rook(Color.BLACK))
        ),
        listOf(
            Position(row = 1, column = 0, piece = Pawn(Color.BLACK)),
            Position(row = 1, column = 1, piece = Pawn(Color.BLACK)),
            Position(row = 1, column = 2, piece = Pawn(Color.BLACK)),
            Position(row = 1, column = 3, piece = Pawn(Color.BLACK)),
            Position(row = 1, column = 4, piece = Pawn(Color.BLACK)),
            Position(row = 1, column = 5, piece = Pawn(Color.BLACK)),
            Position(row = 1, column = 6, piece = Pawn(Color.BLACK)),
            Position(row = 1, column = 7, piece = Pawn(Color.BLACK))
        ),
        listOf(
            Position(row = 2, column = 0, piece = null),
            Position(row = 2, column = 1, piece = null),
            Position(row = 2, column = 2, piece = null),
            Position(row = 2, column = 3, piece = null),
            Position(row = 2, column = 4, piece = null),
            Position(row = 2, column = 5, piece = null),
            Position(row = 2, column = 6, piece = null),
            Position(row = 2, column = 7, piece = null)
        ),
        listOf(
            Position(row = 3, column = 0, piece = null),
            Position(row = 3, column = 1, piece = null),
            Position(row = 3, column = 2, piece = null),
            Position(row = 3, column = 3, piece = null),
            Position(row = 3, column = 4, piece = null),
            Position(row = 3, column = 5, piece = null),
            Position(row = 3, column = 6, piece = null),
            Position(row = 3, column = 7, piece = null)
        ),
        listOf(
            position("a4", piece = null),
            position("b4", piece = null),
            position("c4", piece = null),
            position("d4", piece = null),
            position("e4", piece = null),
            position("f4", piece = null),
            position("g4", piece = null),
            position("h4", piece = null)
        ),
        listOf(
            position("a3", piece = null),
            position("b3", piece = null),
            position("c3", piece = null),
            position("d3", piece = null),
            position("e3", piece = null),
            position("f3", piece = null),
            position("g3", piece = null),
            position("h3", piece = null)
        ),
        listOf(
            position("a2", piece = Pawn(Color.WHITE)),
            position("b2", piece = Pawn(Color.WHITE)),
            position("c2", piece = Pawn(Color.WHITE)),
            position("d2", piece = Pawn(Color.WHITE)),
            position("e2", piece = Pawn(Color.WHITE)),
            position("f2", piece = Pawn(Color.WHITE)),
            position("g2", piece = Pawn(Color.WHITE)),
            position("h2", piece = Pawn(Color.WHITE))
        ),
        listOf(
            position("a1", piece = Rook(Color.WHITE)),
            position("b1", piece = Knight(Color.WHITE)),
            position("c1", piece = Bishop(Color.WHITE)),
            position("d1", piece = Queen(Color.WHITE)),
            position("e1", piece = King(Color.WHITE)),
            position("f1", piece = Bishop(Color.WHITE)),
            position("g1", piece = Knight(Color.WHITE)),
            position("h1", piece = Rook(Color.WHITE))
        )
    )
)

@Suppress("LongMethod")
fun buildEmptyBoard() = Board(
    positions = mutableListOf(
        mutableListOf(
            Position(row = 0, column = 0, piece = null),
            Position(row = 0, column = 1, piece = null),
            Position(row = 0, column = 2, piece = null),
            Position(row = 0, column = 3, piece = null),
            Position(row = 0, column = 4, piece = null),
            Position(row = 0, column = 5, piece = null),
            Position(row = 0, column = 6, piece = null),
            Position(row = 0, column = 7, piece = null)
        ),
        mutableListOf(
            Position(row = 1, column = 0, piece = null),
            Position(row = 1, column = 1, piece = null),
            Position(row = 1, column = 2, piece = null),
            Position(row = 1, column = 3, piece = null),
            Position(row = 1, column = 4, piece = null),
            Position(row = 1, column = 5, piece = null),
            Position(row = 1, column = 6, piece = null),
            Position(row = 1, column = 7, piece = null)
        ),
        mutableListOf(
            Position(row = 2, column = 0, piece = null),
            Position(row = 2, column = 1, piece = null),
            Position(row = 2, column = 2, piece = null),
            Position(row = 2, column = 3, piece = null),
            Position(row = 2, column = 4, piece = null),
            Position(row = 2, column = 5, piece = null),
            Position(row = 2, column = 6, piece = null),
            Position(row = 2, column = 7, piece = null)
        ),
        mutableListOf(
            Position(row = 3, column = 0, piece = null),
            Position(row = 3, column = 1, piece = null),
            Position(row = 3, column = 2, piece = null),
            Position(row = 3, column = 3, piece = null),
            Position(row = 3, column = 4, piece = null),
            Position(row = 3, column = 5, piece = null),
            Position(row = 3, column = 6, piece = null),
            Position(row = 3, column = 7, piece = null)
        ),
        listOf(
            position("a4", piece = null),
            position("b4", piece = null),
            position("c4", piece = null),
            position("d4", piece = null),
            position("e4", piece = null),
            position("f4", piece = null),
            position("g4", piece = null),
            position("h4", piece = null)
        ),
        listOf(
            position("a3", piece = null),
            position("b3", piece = null),
            position("c3", piece = null),
            position("d3", piece = null),
            position("e3", piece = null),
            position("f3", piece = null),
            position("g3", piece = null),
            position("h3", piece = null)
        ),
        listOf(
            position("a2", piece = null),
            position("b2", piece = null),
            position("c2", piece = null),
            position("d2", piece = null),
            position("e2", piece = null),
            position("f2", piece = null),
            position("g2", piece = null),
            position("h2", piece = null)
        ),
        listOf(
            position("a1", piece = null),
            position("b1", piece = null),
            position("c1", piece = null),
            position("d1", piece = null),
            position("e1", piece = null),
            position("f1", piece = null),
            position("g1", piece = null),
            position("h1", piece = null)
        )
    ),
    turnColor = Color.WHITE
)
