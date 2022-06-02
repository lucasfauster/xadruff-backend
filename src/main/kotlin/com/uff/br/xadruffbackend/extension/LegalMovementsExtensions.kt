package com.uff.br.xadruffbackend.extension

import com.uff.br.xadruffbackend.helper.buildGson
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

fun List<LegalMovements>.flattenToLegalMovements(): LegalMovements {
    return map {
        it.movements
    }.flatten().toLegalMovements()
}

@JvmName("flattenToLegalMovementsLegalMovements")
fun List<List<LegalMovements>>.flattenToLegalMovements(): LegalMovements =
    flatten().flattenToLegalMovements()

fun createMovement(originPosition: Position, futurePosition: Position, action: String = "") =
    originPosition.toChessPosition() + futurePosition.toChessPosition() + action

fun LegalMovements.addAll(legalMovements: LegalMovements) = movements.addAll(legalMovements.movements)

fun LegalMovements.toJsonString(): String {
    val gson = buildGson()
    return gson.toJson(this)
}

fun MutableList<String>.toMap(): Map<String, List<String>> {

    fun getFuturePositionFromMove(index: String, legalMoves: List<String>): List<String> =
        legalMoves.filter {
            it.slice(ChessSliceIndex.FIRST_POSITION) == index
        }.map {
            it.slice(ChessSliceIndex.SECOND_POSITION)
        }

    val movesMap: MutableMap<String, List<String>> = mutableMapOf()
    for (move in this) {
        val index = move.slice(ChessSliceIndex.FIRST_POSITION)
        if (!movesMap.containsKey(index)) {
            movesMap[index] = getFuturePositionFromMove(index, this)
        }
    }
    return movesMap
}

@Suppress("MagicNumber")
object ChessSliceIndex {
    val FIRST_POSITION = 0..1
    val SECOND_POSITION = 2..3
}
