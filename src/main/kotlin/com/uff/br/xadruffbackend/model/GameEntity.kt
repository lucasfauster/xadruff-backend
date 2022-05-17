package com.uff.br.xadruffbackend.model

import InterfaceAdapter
import buildGson
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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
    var allMovements: String? = null,
    var winner: String? = null,
    var whiteDrawMoves: Int = 0,
    var blackDrawMoves: Int = 0,
) {
    @Transient
    private val gson = buildGson()

    fun getBoard(): Board = gson.fromJson(board, Board::class.java)

    fun getLegalMovements(): LegalMovements = gson.fromJson(legalMovements, LegalMovements::class.java)

}

