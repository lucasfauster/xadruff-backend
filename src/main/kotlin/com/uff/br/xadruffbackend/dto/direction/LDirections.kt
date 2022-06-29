package com.uff.br.xadruffbackend.dto.direction

class UpLeftL : Direction() {
    override fun getFutureRow(row: Int, index: Int) = row - 2
    override fun getFutureColumn(column: Int, index: Int) = column - 1
}

class UpRightL : Direction() {
    override fun getFutureRow(row: Int, index: Int) = row - 2
    override fun getFutureColumn(column: Int, index: Int) = column + 1
}

class DownLeftL : Direction() {
    override fun getFutureRow(row: Int, index: Int) = row + 2
    override fun getFutureColumn(column: Int, index: Int) = column - 1
}

class DownRightL : Direction() {
    override fun getFutureRow(row: Int, index: Int) = row + 2
    override fun getFutureColumn(column: Int, index: Int) = column + 1
}

class LeftUpL : Direction() {
    override fun getFutureRow(row: Int, index: Int) = row - 1
    override fun getFutureColumn(column: Int, index: Int) = column - 2
}

class LeftDownL : Direction() {
    override fun getFutureRow(row: Int, index: Int) = row + 1
    override fun getFutureColumn(column: Int, index: Int) = column - 2
}

class RightUpL : Direction() {
    override fun getFutureRow(row: Int, index: Int) = row - 1
    override fun getFutureColumn(column: Int, index: Int) = column + 2
}

class RightDownL : Direction() {
    override fun getFutureRow(row: Int, index: Int) = row + 1
    override fun getFutureColumn(column: Int, index: Int) = column + 2
}
