package com.uff.br.xadruffbackend.model

import com.google.gson.Gson

data class LegalMovements(
    val movements: Map<String, List<String>>
)

fun LegalMovements.toJsonString(): String = Gson().toJson(this)