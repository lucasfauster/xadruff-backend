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

    private val colorTurn = true

    //TODO: pensar em como automatizar a atualização dos valores após aplicação de um movimento no board
    // talvez podemos apenas chamar as funções no final da função que aplica o movimento no boar
    var legalMoves: MutableList<String> = mutableListOf("a2a3", "a2a4", "b2b3", "b2b4", "c2c3", "c2c4", "d2d3", "d2d4",
        "e2e3", "e2e4", "f2f3", "f2f4", "g2g3", "g2g4", "h2h3", "h2h4",
        "b1a3", "b1c3", "g1f3", "g1h3")

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
        val newLegalMoves = mutableListOf<String>()

        fun indexToString(line: Int, col: Int): String =
            'a' + col + (8 - line).toString()

        fun addNewMove(originLine: Int, originCol: Int, futureLine: Int, futureCol: Int, action:String = "") {
            newLegalMoves.add(indexToString(originLine, originCol) + indexToString(futureLine, futureCol) + action)
        }

        fun isOutOfIndex(line: Int, col: Int) =
            line < 0 || line > 7 || col > 7 || col < 0

        fun hasAllie(line: Int, col: Int) =
            state.getOrNull(line)?.getOrNull(col)?.getColor() == colorTurn

        fun isEmpty(line: Int, col: Int) =
            state.getOrNull(line)?.getOrNull(col) != ""

        fun hasEnemy(line: Int, col: Int) =
            state.getOrNull(line)?.getOrNull(col)?.getColor() != colorTurn

        fun calculatePawnMoves(line: Int, col: Int){
            //TODO: implementar en passant
            var direction = 1
            if(colorTurn) direction = -1 // se for branco ele move apenas para linhas de index menores

            // movimento pra frente
            if(isEmpty(line+(1*direction), col)) addNewMove(line, col, line+(1*direction), col)
            if(isEmpty(line+(2*direction), col) && isEmpty(line-1,col) && line == 7)
                addNewMove(line, col,line+(2*direction), col)

            //captura
            if(hasEnemy(line+(1*direction), col-1)) addNewMove(line, col,line+(1*direction), col-1, "C")
            if(hasEnemy(line+(1*direction), col+1)) addNewMove(line, col,line+(1*direction), col+1, "C")

        }

        fun calculateHorseMoves(line: Int, col: Int){
            for(i in 0..1){
                if(hasEnemy(line+2-i, col+1+i)) addNewMove(line, col,line+2-i, col+1+i, "C")
                if(hasEnemy(line+2-i, col-1+i)) addNewMove(line, col,line+2-i, col-1+i, "C")
                if(hasEnemy(line-2-i, col+1+i)) addNewMove(line, col,line-2-i, col+1+i, "C")
                if(hasEnemy(line-2-i, col-1+i)) addNewMove(line, col,line-2-i, col-1+i, "C")

                if(isEmpty(line+2-i, col+1+i)) addNewMove(line, col,line+2-i, col+1+i)
                if(isEmpty(line+2-i, col-1+i)) addNewMove(line, col,line+2-i, col-1+i)
                if(isEmpty(line-2-i, col+1+i)) addNewMove(line, col,line-2-i, col+1+i)
                if(isEmpty(line-2-i, col-1+i)) addNewMove(line, col,line-2-i, col-1+i)
            }
        }

        fun calculateKingMoves(line: Int, col: Int){
            //TODO: implementar o rook
            for(i in (-1..1)){
                if(hasEnemy(line+i,col)) addNewMove(line, col, line+i, col, "C")
                if(hasEnemy(line+i,col-1)) addNewMove(line, col, line+i, col-1, "C")
                if(hasEnemy(line+i,col+1)) addNewMove(line, col, line+i, col+1, "C")

                if(isEmpty(line+i,col)) addNewMove(line, col, line+i, col)
                if(isEmpty(line+i,col-1)) addNewMove(line, col, line+i, col-1)
                if(isEmpty(line+i,col+1)) addNewMove(line, col, line+i, col+1)
            }
       }

        fun calculateRookMoves(line: Int, col: Int){
            var upColumnFree= true; var downColumnFree = true; var rightLineFree = true; var leftLineFree = true
            for( i in (1..7)){
                if(hasAllie(line+i, col) || isOutOfIndex(line+i, col)) downColumnFree = false
                if(isEmpty(line+i, col) && downColumnFree) addNewMove(line, col, line+i, col)
                if(hasEnemy(line+i, col) && downColumnFree) {
                    addNewMove(line, col, line + i, col, "C")
                    downColumnFree = false
                }

                if(hasAllie(line-i, col) || isOutOfIndex(line-i, col)) upColumnFree = false
                if(isEmpty(line-i, col) && upColumnFree) addNewMove(line, col, line-i, col)
                if(hasEnemy(line-i, col) && upColumnFree) {
                    addNewMove(line, col, line - i, col, "C")
                    upColumnFree = false
                }

                if(hasAllie(line, col-i) || isOutOfIndex(line, col-i)) leftLineFree = false
                if(isEmpty(line, col-i) && leftLineFree) addNewMove(line, col, line, col-i)
                if(hasEnemy(line, col-i) && leftLineFree) {
                    addNewMove(line, col, line, col - i, "C")
                    leftLineFree = false
                }

                if(hasAllie(line, col+i) || isOutOfIndex(line, col+i)) rightLineFree = false
                if(isEmpty(line, col+i) && rightLineFree) addNewMove(line, col, line, col+i)
                if(hasEnemy(line, col+i) && rightLineFree) {
                    addNewMove(line, col, line, col + i, "C")
                    rightLineFree = false
                }
            }
        }

        for (line in 0..7){
            for (col in 0..7) {
                if(state[line][col].getColor() == colorTurn)
                    when (state[line][col].uppercase()) {
                        "P" -> {
                            calculatePawnMoves(line, col)
                        }
                        "N" -> {
                            calculateHorseMoves(line, col)
                        }
                        "K" -> {
                            calculateKingMoves(line, col)
                        }
                        "R" -> {
                            calculateRookMoves(line, col)
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