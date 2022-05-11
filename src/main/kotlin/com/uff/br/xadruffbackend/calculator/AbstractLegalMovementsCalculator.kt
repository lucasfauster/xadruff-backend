package com.uff.br.xadruffbackend.calculator

import com.uff.br.xadruffbackend.enum.Color

abstract class AbstractLegalMovementsCalculator(protected val colorTurn: Color,
                                                private val boardPositions: List<List<String>>) {
    fun hasAlly(line: Int, col: Int) =
        boardPositions.getOrNull(line)?.getOrNull(col)?.getColor() == colorTurn

    fun isEmpty(line: Int, col: Int) =
        boardPositions.getOrNull(line)?.getOrNull(col) != ""

    fun hasEnemy(line: Int, col: Int) =
        boardPositions.getOrNull(line)?.getOrNull(col)?.getColor() != colorTurn
}