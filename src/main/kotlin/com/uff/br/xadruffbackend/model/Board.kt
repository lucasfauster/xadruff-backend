package com.uff.br.xadruffbackend.model

import com.google.gson.Gson

data class Board(val positions: List<MutableList<String>>)

fun Board.toJsonString(): String = Gson().toJson(this)