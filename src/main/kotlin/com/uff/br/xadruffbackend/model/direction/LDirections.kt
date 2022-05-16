package com.uff.br.xadruffbackend.model.direction

fun buildLDirections(line: Int, col: Int) = listOf(
    UpLeftL(line, col),
    UpRightL(line, col),
    DownLeftL(line, col),
    DownRightL(line, col),
    LeftUpL(line, col),
    LeftDownL(line, col),
    RightUpL(line, col),
    RightDownL(line, col)
)

class UpLeftL(override val line: Int, override val column: Int): Direction {
    override fun getFutureLine(index: Int) = line - 2
    override fun getFutureColumn(index: Int) = column - 1
}

class UpRightL(override val line: Int, override val column: Int): Direction {
    override fun getFutureLine(index: Int) = line - 2
    override fun getFutureColumn(index: Int) = column + 1
}

class DownLeftL(override val line: Int, override val column: Int): Direction {
    override fun getFutureLine(index: Int) = line + 2
    override fun getFutureColumn(index: Int) = column - 1
}

class DownRightL(override val line: Int, override val column: Int): Direction {
    override fun getFutureLine(index: Int) = line + 2
    override fun getFutureColumn(index: Int) = column + 1
}

class LeftUpL(override val line: Int, override val column: Int): Direction {
    override fun getFutureLine(index: Int) = line - 1
    override fun getFutureColumn(index: Int) = column - 2
}

class LeftDownL(override val line: Int, override val column: Int): Direction {
    override fun getFutureLine(index: Int) = line + 1
    override fun getFutureColumn(index: Int) = column - 2
}

class RightUpL(override val line: Int, override val column: Int): Direction {
    override fun getFutureLine(index: Int) = line - 1
    override fun getFutureColumn(index: Int) = column + 2
}

class RightDownL(override val line: Int, override val column: Int): Direction {
    override fun getFutureLine(index: Int) = line + 1
    override fun getFutureColumn(index: Int) = column + 2
}