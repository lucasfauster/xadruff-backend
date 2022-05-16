package com.uff.br.xadruffbackend.model

import com.uff.br.xadruffbackend.enum.Color

class Piece(
    val position: Position,
    val value: Char
) {
    fun getColor() = if(value.isUpperCase()) {
        Color.WHITE
    } else {
        Color.BLACK
    }

    override fun toString(): String {
        return value.toString()
    }
}