package com.uff.br.xadruffbackend.model

import com.google.gson.Gson

data class LegalMovements(
    val movements: MutableList<String>
) {
    fun addNewMove(
        originPosition: Position,
        futurePosition: Position,
        action: String = ""
    ) {
        movements.add(
            originPosition.indexToString() + futurePosition.indexToString() + action
        )
    }

    fun toJsonString(): String = Gson().toJson(this)
}



//fun getFuturePositionFromMove(index: String, legalMoves: List<String>): List<String> =
//    legalMoves.filter {
//        it.slice(0..1) == index
//    }.map {
//        it.slice(2..3)
//    }
//
//fun List<String>.legalMovementsToMap(): MutableMap<String, List<String>> {
//    val movesMap: MutableMap<String, List<String>> = mutableMapOf()
//    for (move in this) {
//        val index = move.slice(0..1)
//        if (!movesMap.containsKey(index)) {
//            movesMap[index] = getFuturePositionFromMove(index, this)
//        }
//    }
//    return movesMap
//}