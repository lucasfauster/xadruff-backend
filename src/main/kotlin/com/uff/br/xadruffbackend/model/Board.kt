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

    var legalMoves: MutableList<String> = mutableListOf("a2a3", "a2a4", "b2b3", "b2b4", "c2c3", "c2c4", "d2d3", "d2d4",
        "e2e3", "e2e4", "f2f3", "f2f4", "g2g3", "g2g4", "h2h3", "h2h4",
        "b1a3", "b1c3", "g1f3", "g1h3", "a7a6", "a7a5", "b7b6", "b7b5",
        "c7c6", "c7c5", "d7d6", "d7d5", "e7e6", "e7e5", "f7f6", "f7f5",
        "g7g6", "g7g5", "h7h6", "h7h5", "c8b6", "c8d6", "g8f6", "g8h6")

    fun legalMovesToMap(): MutableMap<String,List<String>> {
        fun getFuturePosition(index: String): List<String>{
            return legalMoves.filter {it.slice(0..1) == index}.map{it.slice(2..3)}
        }

        val movesMap: MutableMap<String,List<String>> = mutableMapOf()
        for (move in legalMoves){
            val index = move.slice(0..1)
            if(!movesMap.containsKey(index)) movesMap[index] = getFuturePosition(index)
        }

        return movesMap
    }

    fun calculateLegalMoves() {
        fun indexToString(line: Int, col: Int): String{
            return 'a' + col + (8 - line).toString()
        }

        val newLegalMoves = mutableListOf<String>()
        for (line in 0..7){
            for (col in 0..7) {
                // peao fora do when por ter diferenca entre as cores
                if(state[line][col] == "p"){
                    // falta implementar o en passant

                    // movimento pra frente
                    if(state.getOrNull(line-1)?.getOrNull(col) == "") newLegalMoves.add(indexToString(line, col) + indexToString(line-1, col))
                    if(state.getOrNull(line-2)?.getOrNull(col) == "" && line == 7 && state.getOrNull(line-1)?.getOrNull(col) == "") newLegalMoves.add(indexToString(line, col) + indexToString(line-2, col))

                    //captura
                    if(state.getOrNull(line-1)?.getOrNull(col-1) != "") newLegalMoves.add(indexToString(line, col) + indexToString(line-1, col-1))
                    if(state.getOrNull(line-1)?.getOrNull(col+1) != "") newLegalMoves.add(indexToString(line, col) + indexToString(line-1, col+1))
                }
                else if(state[line][col] == "P"){
                    // falta implementar o en passant

                    // movimento pra frente
                    if(state.getOrNull(line+1)?.getOrNull(col) == "") newLegalMoves.add(indexToString(line, col) + indexToString(line+1, col))
                    if(state.getOrNull(line+2)?.getOrNull(col) == "" && line == 2 && state.getOrNull(line+1)?.getOrNull(col) == "") newLegalMoves.add(indexToString(line, col) + indexToString(line+2, col))
                    //captura ( precisa checar se a peça a ser capturada é de cor oposta)
                    if(state.getOrNull(line+1)?.getOrNull(col-1) != "") newLegalMoves.add(indexToString(line, col) + indexToString(line+1, col-1))
                    if(state.getOrNull(line+1)?.getOrNull(col+1) != "") newLegalMoves.add(indexToString(line, col) + indexToString(line+1, col+1))
                }
                else {
                    when (state[line][col].uppercase()) {
                        ("N") -> {
                            for(i in 0..1){
                                //precisa verificar se não tem uma peça da mesma cor no lugar
                                if(state.getOrNull(line+2-i)?.getOrNull(col+1+i) != "") newLegalMoves.add(indexToString(line, col) + indexToString(line+1, col-1))
                                if(state.getOrNull(line+2-i)?.getOrNull(col-1+i) != "") newLegalMoves.add(indexToString(line, col) + indexToString(line+1, col-1))
                                if(state.getOrNull(line-2-i)?.getOrNull(col+1+i) != "") newLegalMoves.add(indexToString(line, col) + indexToString(line+1, col-1))
                                if(state.getOrNull(line-2-i)?.getOrNull(col-1+i) != "") newLegalMoves.add(indexToString(line, col) + indexToString(line+1, col-1))
                            }
                        }
                    }
                }
            }
        }
    }
}

fun Board.toJsonString(): String = Gson().toJson(this)