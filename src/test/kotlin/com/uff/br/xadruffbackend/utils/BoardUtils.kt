package com.uff.br.xadruffbackend.utils

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
            Position(line = 0, column = 3, piece = King(Color.BLACK)),
            Position(line = 0, column = 4, piece = Queen(Color.BLACK)),
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
            Position(line = 4, column = 0, piece = null),
            Position(line = 4, column = 1, piece = null),
            Position(line = 4, column = 2, piece = null),
            Position(line = 4, column = 3, piece = null),
            Position(line = 4, column = 4, piece = null),
            Position(line = 4, column = 5, piece = null),
            Position(line = 4, column = 6, piece = null),
            Position(line = 4, column = 7, piece = null)
        ),
        listOf(
            Position(line = 5, column = 0, piece = null),
            Position(line = 5, column = 1, piece = null),
            Position(line = 5, column = 2, piece = null),
            Position(line = 5, column = 3, piece = null),
            Position(line = 5, column = 4, piece = null),
            Position(line = 5, column = 5, piece = null),
            Position(line = 5, column = 6, piece = null),
            Position(line = 5, column = 7, piece = null)
        ),
        listOf(
            Position(line = 6, column = 0, piece = Pawn(Color.WHITE)),
            Position(line = 6, column = 1, piece = Pawn(Color.WHITE)),
            Position(line = 6, column = 2, piece = Pawn(Color.WHITE)),
            Position(line = 6, column = 3, piece = Pawn(Color.WHITE)),
            Position(line = 6, column = 4, piece = Pawn(Color.WHITE)),
            Position(line = 6, column = 5, piece = Pawn(Color.WHITE)),
            Position(line = 6, column = 6, piece = Pawn(Color.WHITE)),
            Position(line = 6, column = 7, piece = Pawn(Color.WHITE))
        ),
        listOf(
            Position(line = 7, column = 0, piece = Rook(Color.WHITE)),
            Position(line = 7, column = 1, piece = Knight(Color.WHITE)),
            Position(line = 7, column = 2, piece = Bishop(Color.WHITE)),
            Position(line = 7, column = 3, piece = Queen(Color.WHITE)),
            Position(line = 7, column = 4, piece = King(Color.WHITE)),
            Position(line = 7, column = 5, piece = Bishop(Color.WHITE)),
            Position(line = 7, column = 6, piece = Knight(Color.WHITE)),
            Position(line = 7, column = 7, piece = Rook(Color.WHITE))
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
            Position(line = 6, column = 0, piece = null),
            Position(line = 6, column = 1, piece = null),
            Position(line = 6, column = 2, piece = null),
            Position(line = 6, column = 3, piece = null),
            Position(line = 6, column = 4, piece = null),
            Position(line = 6, column = 5, piece = null),
            Position(line = 6, column = 6, piece = null),
            Position(line = 6, column = 7, piece = null)
        ),
        mutableListOf(
            Position(line = 7, column = 0, piece = null),
            Position(line = 7, column = 1, piece = null),
            Position(line = 7, column = 2, piece = null),
            Position(line = 7, column = 3, piece = null),
            Position(line = 7, column = 4, piece = null),
            Position(line = 7, column = 5, piece = null),
            Position(line = 7, column = 6, piece = null),
            Position(line = 7, column = 7, piece = null)
        )
    ),
    turnColor = Color.WHITE
)