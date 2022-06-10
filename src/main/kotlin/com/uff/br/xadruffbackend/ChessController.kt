package com.uff.br.xadruffbackend

import com.uff.br.xadruffbackend.model.ChessResponse
import com.uff.br.xadruffbackend.model.MoveRequest
import com.uff.br.xadruffbackend.model.enum.StartsBy
import com.uff.br.xadruffbackend.service.ChessService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/chess")
class ChessController(private val chessService: ChessService) {

    @GetMapping("/new-game")
    fun createNewGame(@RequestParam(name = "start-by") startBy: StartsBy): ChessResponse =
        chessService.createNewGame(startBy)

    @PostMapping("/move")
    fun movePiece(
        @RequestParam(name = "board-id")
        boardId: String,

        @RequestBody
        moveRequest: MoveRequest
    ): ChessResponse = chessService.movePiece(boardId, moveRequest.move)
}
