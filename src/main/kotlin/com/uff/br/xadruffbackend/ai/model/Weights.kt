package com.uff.br.xadruffbackend.ai.model

object Weights {
    val pieceWeights = mapOf(
        null to 0,
        'P' to 10,
        'N' to 30,
        'B' to 30,
        'R' to 50,
        'Q' to 90,
        'K' to 900
    )

    val piecePositionWeights =
        mapOf(
            'P' to listOf(
                mutableListOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                mutableListOf(5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0),
                mutableListOf(1.0, 1.0, 2.0, 3.0, 3.0, 2.0, 1.0, 1.0),
                mutableListOf(0.5, 0.5, 1.0, 2.5, 2.5, 1.0, 0.5, 0.5),
                mutableListOf(0.0, 0.0, 0.0, 2.0, 2.0, 0.0, 0.0, 0.0),
                mutableListOf(0.5, -0.5, -1.0, 0.0, 0.0, -1.0, -0.5, 0.5),
                mutableListOf(0.5, 1.0, 1.0, -2.0, -2.0, 1.0, 1.0, 0.5),
                mutableListOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
            ),
            'N' to listOf(
                mutableListOf(-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0),
                mutableListOf(-4.0, -2.0, 0.0, 0.0, 0.0, 0.0, -2.0, -4.0),
                mutableListOf(-3.0, 0.0, 1.0, 1.5, 1.5, 1.0, 0.0, -3.0),
                mutableListOf(-3.0, 0.5, 1.5, 2.0, 2.0, 1.5, 0.5, -3.0),
                mutableListOf(-3.0, 0.0, 1.5, 2.0, 2.0, 1.5, 0.0, -3.0),
                mutableListOf(-3.0, 0.5, 1.0, 1.5, 1.5, 1.0, 0.5, -3.0),
                mutableListOf(-4.0, -2.0, 0.0, 0.5, 0.5, 0.0, -2.0, -4.0),
                mutableListOf(-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0)
            ),
            'B' to listOf(
                mutableListOf(-2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0),
                mutableListOf(-1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -1.0),
                mutableListOf(-1.0, 0.0, 0.5, 1.0, 1.0, 0.5, 0.0, -1.0),
                mutableListOf(-1.0, 0.5, 0.5, 1.0, 1.0, 0.5, 0.5, -1.0),
                mutableListOf(-1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, -1.0),
                mutableListOf(-1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, -1.0),
                mutableListOf(-1.0, 0.5, 0.0, 0.0, 0.0, 0.0, 0.5, -1.0),
                mutableListOf(-2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0)
            ),
            'R' to listOf(
                mutableListOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                mutableListOf(0.5, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.5),
                mutableListOf(-0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.5),
                mutableListOf(-0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.5),
                mutableListOf(-0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.5),
                mutableListOf(-0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.5),
                mutableListOf(-0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.5),
                mutableListOf(0.0, 0.0, 0.0, 0.5, 0.5, 0.0, 0.0, 0.0)
            ),
            'Q' to listOf(
                mutableListOf(-2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0),
                mutableListOf(-1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -1.0),
                mutableListOf(-1.0, 0.0, 0.5, 0.5, 0.5, 0.5, 0.0, -1.0),
                mutableListOf(-0.5, 0.0, 0.5, 0.5, 0.5, 0.5, 0.0, -0.5),
                mutableListOf(0.0, 0.0, 0.5, 0.5, 0.5, 0.5, 0.0, -0.5),
                mutableListOf(-1.0, 0.5, 0.5, 0.5, 0.5, 0.5, 0.0, -1.0),
                mutableListOf(-1.0, 0.0, 0.5, 0.0, 0.0, 0.0, 0.0, -1.0),
                mutableListOf(-2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0)
            ),
            'K' to listOf(
                mutableListOf(-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0),
                mutableListOf(-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0),
                mutableListOf(-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0),
                mutableListOf(-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0),
                mutableListOf(-2.0, -3.0, -3.0, -4.0, -4.0, -3.0, -3.0, -2.0),
                mutableListOf(-1.0, -2.0, -2.0, -2.0, -2.0, -2.0, -2.0, -1.0),
                mutableListOf(2.0, 2.0, 0.0, 0.0, 0.0, 0.0, 2.0, 2.0),
                mutableListOf(2.0, 3.0, 1.0, 0.0, 0.0, 1.0, 3.0, 2.0)
            )
        )
}
