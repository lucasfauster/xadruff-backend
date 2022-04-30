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

    var legalMovesMap = mutableMapOf<String,List<String>>()

    private fun legalMovesToMap() {
        fun getFuturePosition(index: String): List<String>{
            return legalMoves.filter {it.slice(0..1) == index}.map{it.slice(2..3)}
        }

        val movesMap: MutableMap<String,List<String>> = mutableMapOf()
        for (move in legalMoves){
            val index = move.slice(0..1)
            if(!movesMap.containsKey(index)) movesMap[index] = getFuturePosition(index)
        }

        legalMovesMap = movesMap
    }

    private fun calculateLegalMoves() {
        fun indexToString(line: Int, col: Int): String{
            return 'a' + col + (8 - line).toString()
        }

        val newLegalMoves = mutableListOf<String>()

        fun addNewMove(originLine: Int, originCol: Int, futureLine: Int, futureCol: Int) {
            newLegalMoves.add(indexToString(originLine, originCol) + indexToString(futureLine, futureCol))
        }

        fun calculateBlackPawnMoves(line: Int, col: Int){
            // falta implementar o en passant

            // movimento pra frente
            if(state.getOrNull(line-1)?.getOrNull(col) == "") addNewMove(line, col, line-1, col)
            // pode andar duas casas se for o primeiro movimento ( esta na linha 7 ) e não tem ninguem nas duas casas a frente
            if(state.getOrNull(line-2)?.getOrNull(col) == "" && state.getOrNull(line-1)?.getOrNull(col) == "" && line == 7) addNewMove(line, col,line-2, col)

            //captura
            if(state.getOrNull(line-1)?.getOrNull(col-1)?.isWhite() == true) addNewMove(line, col,line-1, col-1)
            if(state.getOrNull(line-1)?.getOrNull(col+1)?.isWhite() == true) addNewMove(line, col,line-1, col+1)

        }

        fun calculateWhitePawnMoves(line: Int, col: Int){
            // falta implementar o en passant

            // movimento pra frente
            if(state.getOrNull(line+1)?.getOrNull(col) == "") addNewMove(line, col,line+1, col)
            // pode andar duas casas se for o primeiro movimento ( esta na linha 2 ) e não tem ninguem nas duas casas a frente
            if(state.getOrNull(line+2)?.getOrNull(col) == "" && line == 2 && state.getOrNull(line+1)?.getOrNull(col) == "") addNewMove(line, col,line+2, col)
            
            //captura
            if(state.getOrNull(line+1)?.getOrNull(col-1)?.isBlack() == true) addNewMove(line, col,line+1, col-1)
            if(state.getOrNull(line+1)?.getOrNull(col+1)?.isBlack() == true) addNewMove(line, col,line+1, col+1)
        }

        fun calculateWhiteHorseMoves(line: Int, col: Int){
            for(i in 0..1){
                if(state.getOrNull(line+2-i)?.getOrNull(col+1+i) != "" || state.getOrNull(line+2-i)?.getOrNull(col+1+i)?.isBlack() == true) addNewMove(line, col,line+2-i, col+1+i)
                if(state.getOrNull(line+2-i)?.getOrNull(col-1+i) != "" || state.getOrNull(line+2-i)?.getOrNull(col+1+i)?.isBlack() == true) addNewMove(line, col,line+2-i, col-1+i)
                if(state.getOrNull(line-2-i)?.getOrNull(col+1+i) != "" || state.getOrNull(line+2-i)?.getOrNull(col+1+i)?.isBlack() == true) addNewMove(line, col,line-2-i, col+1+i)
                if(state.getOrNull(line-2-i)?.getOrNull(col-1+i) != "" || state.getOrNull(line+2-i)?.getOrNull(col+1+i)?.isBlack() == true) addNewMove(line, col,line-2-i, col-1+i)
            }
        }

        fun calculateBlackHorseMoves(line: Int, col: Int){
            for(i in 0..1){
                if(state.getOrNull(line+2-i)?.getOrNull(col+1+i) != "" || state.getOrNull(line+2-i)?.getOrNull(col+1+i)?.isWhite() == true) addNewMove(line, col,line+2-i, col+1+i)
                if(state.getOrNull(line+2-i)?.getOrNull(col-1+i) != "" || state.getOrNull(line+2-i)?.getOrNull(col+1+i)?.isWhite() == true) addNewMove(line, col,line+2-i, col-1+i)
                if(state.getOrNull(line-2-i)?.getOrNull(col+1+i) != "" || state.getOrNull(line+2-i)?.getOrNull(col+1+i)?.isWhite() == true) addNewMove(line, col,line-2-i, col+1+i)
                if(state.getOrNull(line-2-i)?.getOrNull(col-1+i) != "" || state.getOrNull(line+2-i)?.getOrNull(col+1+i)?.isWhite() == true) addNewMove(line, col,line-2-i, col-1+i)
            }
        }

        for (line in 0..7){
            for (col in 0..7) {
                when (state[line][col]) {
                    "p" -> {
                        calculateBlackPawnMoves(line, col)
                    }
                    "P" -> {
                        calculateWhitePawnMoves(line, col)
                    }
                    "n" -> {
                        calculateBlackHorseMoves(line, col)
                    }
                    "N" -> {
                        calculateWhiteHorseMoves(line, col)
                    }
                }
            }
        }

        legalMoves = newLegalMoves
    }

    fun String.isWhite(): Boolean {
        forEach { if(!it.isUpperCase()) return false }
        return true
    }

    fun String.isBlack(): Boolean {
        forEach { if(it.isUpperCase()) return false }
        return true
    }
}

fun Board.toJsonString(): String = Gson().toJson(this)