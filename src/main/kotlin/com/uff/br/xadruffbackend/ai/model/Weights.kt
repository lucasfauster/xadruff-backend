package com.uff.br.xadruffbackend.ai.model

@Suppress("MagicNumber")
object Weights {
    val pieceWeights = mapOf(
        null to 0,
        'P' to 100,
        'N' to 280,
        'B' to 320,
        'R' to 479,
        'Q' to 929,
        'K' to 60000
    )

    val piecePositionWeights =
        mapOf(
            'P' to listOf(
                mutableListOf(0, 0, 0, 0, 0, 0, 0, 0,),
                mutableListOf(78, 83, 86, 73, 102, 82, 85, 90,),
                mutableListOf(7, 29, 21, 44, 40, 31, 44, 7),
                mutableListOf(-17, 16, -2, 15, 14, 0, 15, -13),
                mutableListOf(-26, 3, 10, 9, 6, 1, 0, -23),
                mutableListOf(-22, 9, 5, -11, -10, -2, 3, -19),
                mutableListOf(-31, 8, -7, -37, -36, -14, 3, -31),
                mutableListOf(0, 0, 0, 0, 0, 0, 0, 0)
            ),
            'N' to listOf(
                mutableListOf(-66, -53, -75, -75, -10, -55, -58, -70),
                mutableListOf(-3, -6, 100, -36, 4, 62, -4, -14),
                mutableListOf(10, 67, 1, 74, 73, 27, 62, -2),
                mutableListOf(24, 24, 45, 37, 33, 41, 25, 17),
                mutableListOf(-1, 5, 31, 21, 22, 35, 2, 0),
                mutableListOf(-18, 10, 13, 22, 18, 15, 11, -14),
                mutableListOf(-23, -15, 2, 0, 2, 0, -23, -20),
                mutableListOf(-74, -23, -26, -24, -19, -35, -22, -69)
            ),
            'B' to listOf(
                mutableListOf(-59, -78, -82, -76, -23, -107, -37, -50),
                mutableListOf(-11, 20, 35, -42, -39, 31, 2, -22),
                mutableListOf(-9, 39, -32, 41, 52, -10, 28, -14),
                mutableListOf(25, 17, 20, 34, 26, 25, 15, 10),
                mutableListOf(13, 10, 17, 23, 17, 16, 0, 7),
                mutableListOf(14, 25, 24, 15, 8, 25, 20, 15),
                mutableListOf(19, 20, 11, 6, 7, 6, 20, 16),
                mutableListOf(-7, 2, -15, -12, -14, -15, -10, -10)
            ),
            'R' to listOf(
                mutableListOf(35, 29, 33, 4, 37, 33, 56, 50),
                mutableListOf(55, 29, 56, 67, 55, 62, 34, 60),
                mutableListOf(19, 35, 28, 33, 45, 27, 25, 15),
                mutableListOf(0, 5, 16, 13, 18, -4, -9, -6),
                mutableListOf(-28, -35, -16, -21, -13, -29, -46, -30),
                mutableListOf(-42, -28, -42, -25, -25, -35, -26, -46),
                mutableListOf(-53, -38, -31, -26, -29, -43, -44, -53),
                mutableListOf(-30, -24, -18, 5, -2, -18, -31, -32)
            ),
            'Q' to listOf(
                mutableListOf(6, 1, -8, -104, 69, 24, 88, 26),
                mutableListOf(14, 32, 60, -10, 20, 76, 57, 24),
                mutableListOf(-2, 43, 32, 60, 72, 63, 43, 2),
                mutableListOf(1, -16, 22, 17, 25, 20, -13, -6),
                mutableListOf(-14, -15, -2, -5, -1, -10, -20, -22),
                mutableListOf(-30, -6, -13, -11, -16, -11, -16, -27),
                mutableListOf(-36, -18, 0, -19, -15, -15, -21, -38),
                mutableListOf(-39, -30, -31, -13, -31, -36, -34, -42)
            ),
            'K' to listOf(
                mutableListOf(4, 54, 47, -99, -99, 60, 83, -62),
                mutableListOf(-32, 10, 55, 56, 56, 55, 10, 3),
                mutableListOf(-62, 12, -57, 44, -67, 28, 37, -31),
                mutableListOf(-55, 50, 11, -4, -19, 13, 0, -49),
                mutableListOf(-55, -43, -52, -28, -51, -47, -8, -50),
                mutableListOf(-47, -42, -43, -79, -64, -32, -29, -32),
                mutableListOf(-4, 3, -14, -50, -57, -18, 13, 4),
                mutableListOf(17, 30, -3, -14, 6, -1, 40, 18)
            )
        )
}
