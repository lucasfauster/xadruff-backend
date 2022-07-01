package com.uff.br.xadruffbackend.controller

import com.uff.br.xadruffbackend.annotation.RowsAndColumnsRange
import com.uff.br.xadruffbackend.dto.enum.Level
import com.uff.br.xadruffbackend.dto.enum.StartsBy
import com.uff.br.xadruffbackend.dto.request.BoardRequest
import com.uff.br.xadruffbackend.dto.request.MoveRequest
import com.uff.br.xadruffbackend.dto.response.ChessResponse
import com.uff.br.xadruffbackend.service.ChessService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/chess")
@Validated
class ChessController(private val chessService: ChessService) {

    @PostMapping("/new-game")
    @CrossOrigin
    fun createNewGame(
        @RequestParam(name = "start-by")
        startBy: StartsBy,

        @RequestParam(name = "level")
        level: Level,

        @RequestBody
        @RowsAndColumnsRange
        boardRequest: BoardRequest? = null

    ): ChessResponse = chessService.createNewGame(startBy, level, boardRequest)

    @PostMapping("/move")
    @CrossOrigin
    fun movePiece(
        @RequestParam(name = "board-id")
        boardId: String,

        @RequestBody
        moveRequest: MoveRequest
    ): ChessResponse = chessService.movePiece(boardId, moveRequest.move)

    @GetMapping("/surrender")
    @CrossOrigin
    fun surrender(
        @RequestParam(name = "board-id")
        boardId: String
    ): ChessResponse = chessService.surrender(boardId)
}
