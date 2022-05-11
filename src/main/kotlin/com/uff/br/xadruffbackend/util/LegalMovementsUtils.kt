package com.uff.br.xadruffbackend.util

import com.uff.br.xadruffbackend.calculator.indexToString

private fun getFuturePositionFromMove(index: String, legalMoves: List<String>): List<String>{
    return legalMoves.filter{
        it.slice(0..1) == index}
        .map{
            it.slice(2..3)
        }
}

fun List<String>.legalMovementsToMap(): MutableMap<String, List<String>>{
    val movesMap: MutableMap<String, List<String>> = mutableMapOf()
    for (move in this){
        val index = move.slice(0..1)
        if(!movesMap.containsKey(index)) {
            movesMap[index] = getFuturePositionFromMove(index, this)
        }
    }
    return movesMap
}

fun MutableList<String>.addNewMove(originLine: Int, originCol: Int,
                                   futureLine: Int, futureCol: Int,
                                   action:String = "") {
    add(indexToString(originLine, originCol) + indexToString(futureLine, futureCol) + action)
}