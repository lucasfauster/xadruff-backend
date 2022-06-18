package com.uff.br.xadruffbackend.model.enum

enum class Color {
    BLACK, WHITE;

    operator fun not(): Color {
        return when (this) {
            BLACK -> WHITE
            WHITE -> BLACK
        }
    }
}
