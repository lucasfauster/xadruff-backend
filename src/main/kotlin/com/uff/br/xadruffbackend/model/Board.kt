package com.uff.br.xadruffbackend.model

import com.google.gson.Gson

class Board {
    var state: List<MutableList<String>> = listOf(
        mutableListOf("r", "n", "b", "q", "k", "b", "n", "r"),
        mutableListOf("p", "p", "p", "p", "p", "p", "p", "p"),
        mutableListOf("", "", "", "", "", "", "", ""),
        mutableListOf("", "", "", "", "", "", "", ""),
        mutableListOf("", "", "", "", "", "", "", ""),
        mutableListOf("", "", "", "", "", "", "", ""),
        mutableListOf("P", "P", "P", "P", "P", "P", "P", "P"),
        mutableListOf("R", "N", "B", "Q", "K", "B", "N", "R"))

    //TODO: pensar em como automatizar a atualização dos valores após aplicação de um movimento no board
    // talvez podemos apenas chamar as funções no final da função que aplica o movimento no boar
    var legalMoves: MutableList<String> = mutableListOf("a2a3", "a2a4", "b2b3", "b2b4", "c2c3", "c2c4", "d2d3", "d2d4",
        "e2e3", "e2e4", "f2f3", "f2f4", "g2g3", "g2g4", "h2h3", "h2h4",
        "b1a3", "b1c3", "g1f3", "g1h3", "a7a6", "a7a5", "b7b6", "b7b5",
        "c7c6", "c7c5", "d7d6", "d7d5", "e7e6", "e7e5", "f7f6", "f7f5",
        "g7g6", "g7g5", "h7h6", "h7h5", "c8b6", "c8d6", "g8f6", "g8h6")

    var legalMovesMap = mutableMapOf<String,List<String>>()

    private fun legalMovesToMap() {
        fun getFuturePositionFromMove(index: String): List<String>{
            return legalMoves.filter {it.slice(0..1) == index}.map{it.slice(2..3)}
        }

        val movesMap: MutableMap<String,List<String>> = mutableMapOf()
        for (move in legalMoves){
            val index = move.slice(0..1)
            if(!movesMap.containsKey(index)) movesMap[index] = getFuturePositionFromMove(index)
        }

        legalMovesMap = movesMap
    }

    private fun calculateLegalMoves() {
        //TODO: Acrescentar indicação de promoção, rook e captura
        fun indexToString(line: Int, col: Int): String{
            return 'a' + col + (8 - line).toString()
        }

        val newLegalMoves = mutableListOf<String>()

        fun addNewMove(originLine: Int, originCol: Int, futureLine: Int, futureCol: Int) {
            newLegalMoves.add(indexToString(originLine, originCol) + indexToString(futureLine, futureCol))
        }

        fun calculatePawnMoves(line: Int, col: Int, color: Boolean){
            //TODO: implementar en passant
            var direction = -1
            if(color) direction = 1 // se for branco ele move apenas para linhas de index maiores

            // movimento pra frente
            if(state.getOrNull(line+(1*direction))?.getOrNull(col) == "") addNewMove(line, col, line-1, col)
            // pode andar duas casas se for o primeiro movimento ( esta na linha 7 ) e não tem ninguem nas duas casas a frente
            if(state.getOrNull(line+(2*direction))?.getOrNull(col) == "" && state.getOrNull(line-1)?.getOrNull(col) == "" && line == 7) addNewMove(line, col,line-2, col)

            //captura
            if(state.getOrNull(line+(1*direction))?.getOrNull(col-1)?.getColor() == color) addNewMove(line, col,line-1, col-1)
            if(state.getOrNull(line+(1*direction))?.getOrNull(col+1)?.getColor() == color) addNewMove(line, col,line-1, col+1)

        }

        fun calculateHorseMoves(line: Int, col: Int, color: Boolean){
            for(i in 0..1){
                if(state.getOrNull(line+2-i)?.getOrNull(col+1+i) != "" || state.getOrNull(line+2-i)?.getOrNull(col+1+i)?.getColor() == color) addNewMove(line, col,line+2-i, col+1+i)
                if(state.getOrNull(line+2-i)?.getOrNull(col-1+i) != "" || state.getOrNull(line+2-i)?.getOrNull(col-1+i)?.getColor() == color) addNewMove(line, col,line+2-i, col-1+i)
                if(state.getOrNull(line-2-i)?.getOrNull(col+1+i) != "" || state.getOrNull(line-2-i)?.getOrNull(col+1+i)?.getColor() == color) addNewMove(line, col,line-2-i, col+1+i)
                if(state.getOrNull(line-2-i)?.getOrNull(col-1+i) != "" || state.getOrNull(line-2-i)?.getOrNull(col-1+i)?.getColor() == color) addNewMove(line, col,line-2-i, col-1+i)
            }
        }

        fun calculateKingMoves(line: Int, col: Int, color: Boolean){
            //TODO: implementar o rook
            for(i in (-1..1) step 2){
                if(state.getOrNull(line+i)?.getOrNull(col) == "" || state.getOrNull(line-1)?.getOrNull(col)?.getColor() == color) addNewMove(line, col, line+i, col)
                if(state.getOrNull(line+i)?.getOrNull(col-1) == "" || state.getOrNull(line-1)?.getOrNull(col-1)?.getColor() == color) addNewMove(line, col,line+i, col-1)
                if(state.getOrNull(line+i)?.getOrNull(col+1) == "" || state.getOrNull(line-1)?.getOrNull(col+1)?.getColor() == color) addNewMove(line, col,line+i, col+1)
            }
            if(state.getOrNull(line)?.getOrNull(col-1) == "" || state.getOrNull(line-1)?.getOrNull(col-1)?.getColor() == color) addNewMove(line, col,line, col-1)
            if(state.getOrNull(line)?.getOrNull(col+1) == "" || state.getOrNull(line-1)?.getOrNull(col+1)?.getColor() == color) addNewMove(line, col,line, col+1)
        }

        for (line in 0..7){
            for (col in 0..7) {
                when (state[line][col].uppercase()) {
                    "P" -> {
                        calculatePawnMoves(line, col, state[line][col].getColor())
                    }
                    "N" -> {
                        calculateHorseMoves(line, col, state[line][col].getColor())
                    }
                    "K" -> {
                        calculateKingMoves(line, col, state[line][col].getColor())
                    }
                }
            }
        }

        legalMoves = newLegalMoves
    }

    fun String.getColor(): Boolean {
        forEach { if(!it.isUpperCase()) return false }
        return true
    }
}

fun Board.toJsonString(): String = Gson().toJson(this)