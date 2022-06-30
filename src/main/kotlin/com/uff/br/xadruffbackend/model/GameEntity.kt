package com.uff.br.xadruffbackend.model

import com.uff.br.xadruffbackend.dto.Board
import com.uff.br.xadruffbackend.dto.LegalMovements
import com.uff.br.xadruffbackend.dto.enum.Level
import com.uff.br.xadruffbackend.helper.buildGson
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "Game")
@Suppress("LongParameterList")
class GameEntity(
    @Id
    val boardId: String = UUID.randomUUID().toString().uppercase(),

    @Column(columnDefinition = "TEXT")
    var board: String,

    @Column(columnDefinition = "TEXT")
    var legalMovements: String? = null,

    @Column(columnDefinition = "TEXT")
    var allMovements: String = "",

    val level: Level = Level.BEGINNER,
    var winner: String? = null,
    var endgameMessage: String? = null,
    var whiteDrawMoves: Int = 0,
    var blackDrawMoves: Int = 0,
) {

    fun getBoard(): Board = buildGson().fromJson(board, Board::class.java)

    fun getLegalMovements(): LegalMovements = buildGson().fromJson(legalMovements, LegalMovements::class.java)
}
