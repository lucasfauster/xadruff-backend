package com.uff.br.xadruffbackend

import com.uff.br.xadruffbackend.model.ChessResponse
import com.uff.br.xadruffbackend.model.enum.StartsBy
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/chess")
class ChessController(private val chessService: ChessService) {

    @GetMapping("/new-game")
    fun createNewGame(@RequestParam(name = "start-by") startBy: StartsBy): ChessResponse =
        chessService.createNewGame(startBy)
}
