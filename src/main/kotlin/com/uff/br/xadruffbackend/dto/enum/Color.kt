package com.uff.br.xadruffbackend.dto.enum

enum class Color {
    BLACK, WHITE;

    operator fun not(): Color {
        return when (this) {
            BLACK -> WHITE
            WHITE -> BLACK
        }
    }
}
