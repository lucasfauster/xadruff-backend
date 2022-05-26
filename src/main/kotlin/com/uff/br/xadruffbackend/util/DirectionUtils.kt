package com.uff.br.xadruffbackend.util

import com.uff.br.xadruffbackend.model.direction.Direction
import com.uff.br.xadruffbackend.model.direction.DownLeftDiagonal
import com.uff.br.xadruffbackend.model.direction.DownLeftL
import com.uff.br.xadruffbackend.model.direction.DownRightDiagonal
import com.uff.br.xadruffbackend.model.direction.DownRightL
import com.uff.br.xadruffbackend.model.direction.DownStraight
import com.uff.br.xadruffbackend.model.direction.LeftDownL
import com.uff.br.xadruffbackend.model.direction.LeftStraight
import com.uff.br.xadruffbackend.model.direction.LeftUpL
import com.uff.br.xadruffbackend.model.direction.RightDownL
import com.uff.br.xadruffbackend.model.direction.RightStraight
import com.uff.br.xadruffbackend.model.direction.RightUpL
import com.uff.br.xadruffbackend.model.direction.UpLeftDiagonal
import com.uff.br.xadruffbackend.model.direction.UpLeftL
import com.uff.br.xadruffbackend.model.direction.UpRightDiagonal
import com.uff.br.xadruffbackend.model.direction.UpRightL
import com.uff.br.xadruffbackend.model.direction.UpStraight

fun buildDiagonalDirections() = listOf(
    UpRightDiagonal(),
    UpLeftDiagonal(),
    DownLeftDiagonal(),
    DownRightDiagonal()
)

fun buildAllDirections(): List<Direction> {
    val directions = mutableListOf<Direction>()
    directions.addAll(buildDiagonalDirections())
    directions.addAll(buildStraightDirections())
    return directions
}

fun buildLDirections() = listOf(
    UpLeftL(),
    UpRightL(),
    DownLeftL(),
    DownRightL(),
    LeftUpL(),
    LeftDownL(),
    RightUpL(),
    RightDownL()
)

fun buildStraightDirections() = listOf(
    UpStraight(),
    DownStraight(),
    LeftStraight(),
    RightStraight()
)

fun buildWhitePawnDirections() = listOf(
    UpStraight(hasCapture = false),
    UpRightDiagonal(hasMovement = false),
    UpLeftDiagonal(hasMovement = false)
)

fun buildBlackPawnDirections() = listOf(
    DownStraight(hasCapture = false),
    DownRightDiagonal(hasMovement = false),
    DownLeftDiagonal(hasMovement = false)
)
