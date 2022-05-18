package com.uff.br.xadruffbackend.model.piece

import com.uff.br.xadruffbackend.model.enum.Color
import com.uff.br.xadruffbackend.util.buildAllDirections

class King(color: Color): Piece('k', color){
    override val directions = buildAllDirections()
    override val movementRange = 1
}

