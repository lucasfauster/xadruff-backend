package com.uff.br.xadruffbackend.util

import com.uff.br.xadruffbackend.dto.direction.Direction
import com.uff.br.xadruffbackend.dto.direction.DownLeftDiagonal
import com.uff.br.xadruffbackend.dto.direction.DownLeftL
import com.uff.br.xadruffbackend.dto.direction.DownRightDiagonal
import com.uff.br.xadruffbackend.dto.direction.DownRightL
import com.uff.br.xadruffbackend.dto.direction.DownStraight
import com.uff.br.xadruffbackend.dto.direction.LeftDownL
import com.uff.br.xadruffbackend.dto.direction.LeftStraight
import com.uff.br.xadruffbackend.dto.direction.LeftUpL
import com.uff.br.xadruffbackend.dto.direction.RightDownL
import com.uff.br.xadruffbackend.dto.direction.RightStraight
import com.uff.br.xadruffbackend.dto.direction.RightUpL
import com.uff.br.xadruffbackend.dto.direction.UpLeftDiagonal
import com.uff.br.xadruffbackend.dto.direction.UpLeftL
import com.uff.br.xadruffbackend.dto.direction.UpRightDiagonal
import com.uff.br.xadruffbackend.dto.direction.UpRightL
import com.uff.br.xadruffbackend.dto.direction.UpStraight

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
