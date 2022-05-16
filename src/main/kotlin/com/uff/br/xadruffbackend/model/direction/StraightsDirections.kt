package com.uff.br.xadruffbackend.model.direction

fun buildStraightDirections(line: Int, col: Int) = listOf(
    UpColumnStraight(line, col),
    DownColumnStraight(line, col),
    LeftLineStraight(line, col),
    RightLineStraight(line, col)
)

class UpColumnStraight(override val line: Int, override val column: Int): Direction {
    override fun getFutureLine(index: Int) = line
    override fun getFutureColumn(index: Int) = column - index
}

class DownColumnStraight(override val line: Int, override val column: Int): Direction {
    override fun getFutureLine(index: Int) = line
    override fun getFutureColumn(index: Int) = column + index
}

class RightLineStraight(override val line: Int, override val column: Int): Direction {
    override fun getFutureLine(index: Int) = line + index
    override fun getFutureColumn(index: Int) = column
}

class LeftLineStraight(override val line: Int, override val column: Int): Direction {
    override fun getFutureLine(index: Int) = line - index
    override fun getFutureColumn(index: Int) = column
}
