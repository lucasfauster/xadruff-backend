package com.uff.br.xadruffbackend.service

import com.uff.br.xadruffbackend.dto.Board
import com.uff.br.xadruffbackend.dto.enum.Color
import com.uff.br.xadruffbackend.dto.piece.Ghost
import com.uff.br.xadruffbackend.dto.piece.Pawn
import com.uff.br.xadruffbackend.dto.piece.Piece
import com.uff.br.xadruffbackend.extension.futureStringPosition
import com.uff.br.xadruffbackend.extension.originalStringPosition
import com.uff.br.xadruffbackend.extension.position
import org.springframework.stereotype.Service
import kotlin.math.abs

@Service
class EnPassantService {

    fun handleEnPassant(board: Board, move: String, piece: Piece) {
        applyEnPassant(board, move)
        eraseGhost(board)
        handleGhostCreation(board, move, piece)
    }

    fun eraseGhost(board: Board) {
        for (i in GHOST_ROWS) {
            board.positions[i].forEach {
                if (it.piece is Ghost) {
                    it.piece = null
                }
            }
        }
    }

    fun applyEnPassant(board: Board, move: String) {
        val attackPiece = board.position(move.originalStringPosition()).piece
        val ghost = board.position(move.futureStringPosition()).piece
        if (ghost is Ghost && attackPiece is Pawn) {
            var capturePosition = move.futureStringPosition().last().digitToInt()
            val col = move.futureStringPosition().first()
            if (ghost.color == Color.WHITE) {
                capturePosition += 1
            } else {
                capturePosition -= 1
            }
            val pawnPosition = col.toString() + capturePosition.toString()
            board.position(pawnPosition).piece = null
        }
    }

    fun handleGhostCreation(board: Board, move: String, piece: Piece) {
        val actualPosition = move.originalStringPosition().last().digitToInt()
        var futurePosition = move.futureStringPosition().last().digitToInt()
        val dif = actualPosition - futurePosition
        if (piece is Pawn && abs(dif) == 2) {
            val ghostPiece = Ghost(piece.color)
            val col = move.futureStringPosition().first()
            if (ghostPiece.color == Color.WHITE) {
                futurePosition -= 1
            } else {
                futurePosition += 1
            }
            val pos = col.toString() + futurePosition.toString()
            board.position(pos).piece = ghostPiece
        }
    }

    companion object {
        val GHOST_ROWS = listOf(2, 5)
    }
}
