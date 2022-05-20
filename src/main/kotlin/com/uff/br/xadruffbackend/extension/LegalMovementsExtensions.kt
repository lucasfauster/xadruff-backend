package com.uff.br.xadruffbackend.extension

import com.google.gson.Gson
import com.uff.br.xadruffbackend.model.LegalMovements
import com.uff.br.xadruffbackend.model.Position

fun LegalMovements.addNewMove(originPosition: Position, futurePosition: Position, action: String = "") {
    movements.add(
        originPosition.toChessPosition() + futurePosition.toChessPosition() + action
    )
}

fun List<String>.toLegalMovements() = LegalMovements(
    this.toMutableList()
)

@JvmName("flattenToLegalMovementsLegalMovements")
fun List<LegalMovements>.flattenToLegalMovements(): LegalMovements {
    return map {
        it.movements
    }.flatten().toLegalMovements()
}

fun List<List<LegalMovements>>.flattenToLegalMovements(): LegalMovements =
    flatten().flattenToLegalMovements()


fun createMovement(originPosition: Position, futurePosition: Position, action: String = "") =
    originPosition.toChessPosition() + futurePosition.toChessPosition() + action

fun LegalMovements.addAll(legalMovements: LegalMovements) = movements.addAll(legalMovements.movements)

fun LegalMovements.toJsonString(): String = Gson().toJson(this)



fun MutableList<String>.toMap(): Map<String, List<String>> {

    fun getFuturePositionFromMove(index: String, legalMoves: List<String>): List<String> =
        legalMoves.filter {
            it.slice(0..1) == index
        }.map {
            it.slice(2..3)
        }

    val movesMap: MutableMap<String, List<String>> = mutableMapOf()
    for (move in this) {
        val index = move.slice(0..1)
        if (!movesMap.containsKey(index)) {
            movesMap[index] = getFuturePositionFromMove(index, this)
        }
    }
    return movesMap
}