package com.uff.br.xadruffbackend.model.piece

import com.uff.br.xadruffbackend.calculator.AbstractLegalMovementsCalculator
import com.uff.br.xadruffbackend.model.enum.Color

abstract class Piece(
    val value: Char,
): AbstractLegalMovementsCalculator() {

    fun getColor() = if(value.isUpperCase()) {
        Color.WHITE
    } else {
        Color.BLACK
    }

    override fun toString(): String {
        return value.toString()
    }
}