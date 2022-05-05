package com.uff.br.xadruffbackend.AI.model

class BoardMock {
    var state = listOf(
            mutableListOf("r", "n", "b", "q", "k", "b", "n", "r"),
            mutableListOf("p", "p", "p", "p", "p", "p", "p", "p"),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("P", "P", "P", "P", "P", "P", "P", "P"),
            mutableListOf("R", "N", "B", "Q", "K", "B", "N", "R")
        )
    var legalMoves = mutableListOf(
        "a2a3", "a2a4", "b2b3", "b2b4", "c2c3", "c2c4", "d2d3", "d2d4",
        "e2e3", "e2e4", "f2f3", "f2f4", "g2g3", "g2g4", "h2h3", "h2h4",
        "b1a3", "b1c3", "g1f3", "g1h3", "a7a6", "a7a5", "b7b6", "b7b5",
        "c7c6", "c7c5", "d7d6", "d7d5", "e7e6", "e7e5", "f7f6", "f7f5",
        "g7g6", "g7g5", "h7h6", "h7h5", "c8b6", "c8d6", "g8f6", "g8h6"
    )

    fun applyMove(movement: String) {
        state = listOf(
            mutableListOf("r", "n", "b", "q", "k", "b", "n", "r"),
            mutableListOf("p", "p", "p", "p", "p", "p", "p", "p"),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("", "", "", "", "P", "", "", ""),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("P", "P", "P", "", "P", "P", "P", "P"),
            mutableListOf("R", "N", "B", "Q", "K", "B", "N", "R")
        )
    }

    fun revertLastMove() {
        state = listOf(
            mutableListOf("r", "n", "b", "q", "k", "b", "n", "r"),
            mutableListOf("p", "p", "p", "p", "p", "p", "p", "p"),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("", "", "", "", "", "", "", ""),
            mutableListOf("P", "P", "P", "P", "P", "P", "P", "P"),
            mutableListOf("R", "N", "B", "Q", "K", "B", "N", "R")
        )
    }
}
