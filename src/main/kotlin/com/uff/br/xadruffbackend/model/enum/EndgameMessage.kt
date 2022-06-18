package com.uff.br.xadruffbackend.model.enum

enum class EndgameMessage(val message: String) {
    CHECKMATE(message = "Ended by checkmate."),
    STALEMATE(message = "Draw by stalemate."),
    DRAW_RULE(message = "Draw by 50 movements rule.")
}
