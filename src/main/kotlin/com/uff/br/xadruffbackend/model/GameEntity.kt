package com.uff.br.xadruffbackend.model

import com.uff.br.xadruffbackend.helper.buildGson
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "Game")
data class GameEntity(
    @Id val boardId: String = UUID.randomUUID().toString().uppercase(),
    var board: String,
    var legalMovements: String? = null,
    var allMovements: String = "",
    var winner: String? = null,
    var whiteDrawMoves: Int = 0,
    var blackDrawMoves: Int = 0,
) {

    fun getBoard(): Board = buildGson().fromJson(board, Board::class.java)

    fun getLegalMovements(): LegalMovements = buildGson().fromJson(legalMovements, LegalMovements::class.java)
}
