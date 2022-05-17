package com.uff.br.xadruffbackend.model.direction

fun buildStraightDirections(line: Int, col: Int) = listOf(
    UpStraight(line, col),
    DownStraight(line, col),
    LeftStraight(line, col),
    RightStraight(line, col)
)

class UpStraight(override val line: Int, override val column: Int): Direction {
    override fun getFutureLine(index: Int) = line - index
    override fun getFutureColumn(index: Int) = column
}

class DownStraight(override val line: Int, override val column: Int): Direction {
    override fun getFutureLine(index: Int) = line + index
    override fun getFutureColumn(index: Int) = column
}

class RightStraight(override val line: Int, override val column: Int): Direction {
    override fun getFutureLine(index: Int) = line
    override fun getFutureColumn(index: Int) = column + index
}

class LeftStraight(override val line: Int, override val column: Int): Direction {
    override fun getFutureLine(index: Int) = line
    override fun getFutureColumn(index: Int) = column  - index
}
