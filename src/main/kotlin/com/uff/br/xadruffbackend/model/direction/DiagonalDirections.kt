package com.uff.br.xadruffbackend.model.direction

fun buildDiagonalDirections(line: Int, col: Int) = listOf(
    UpRightDiagonal(line, col),
    UpLeftDiagonal(line, col),
    DownLeftDiagonal(line, col),
    DownRightDiagonal(line, col)
)

class DownLeftDiagonal(override val line: Int, override val column: Int): Direction {
    override fun getFutureLine(index: Int) = line + index
    override fun getFutureColumn(index: Int) = column - index
}

class UpLeftDiagonal(override val line: Int, override val column: Int): Direction {
    override fun getFutureLine(index: Int) = line - index
    override fun getFutureColumn(index: Int) = column - index
}

class DownRightDiagonal(override val line: Int, override val column: Int): Direction {
    override fun getFutureLine(index: Int) = line + index
    override fun getFutureColumn(index: Int) = column + index
}

class UpRightDiagonal(override val line: Int, override val column: Int): Direction {
    override fun getFutureLine(index: Int) = line - index
    override fun getFutureColumn(index: Int) = column + index
}