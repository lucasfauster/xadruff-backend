package com.uff.br.xadruffbackend.dto.enum

enum class EndgameMessage(val message: String) {
    CHECKMATE(message = "Ended by checkmate."),
    STALEMATE(message = "Draw by stalemate."),
    DRAW_RULE(message = "Draw by 50 movements rule."),
    SURRENDER(message = "Ended by surrender.")
}
