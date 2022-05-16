package com.uff.br.xadruffbackend.calculator.piece

import com.uff.br.xadruffbackend.calculator.generic.AbstractLegalMovementsCalculator
import com.uff.br.xadruffbackend.enum.Color
import com.uff.br.xadruffbackend.model.Piece
import com.uff.br.xadruffbackend.model.direction.buildLDirections


class HorseMovementsCalculator(colorTurn: Color, boardPositions: List<List<Piece?>>):
    AbstractLegalMovementsCalculator(colorTurn, boardPositions) {

    override fun calculate(legalMovements: MutableList<String>, line: Int, col: Int) =
        legalMovements.calculate(buildLDirections(line, col), indexRange = 1)

}





