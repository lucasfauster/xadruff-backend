package com.uff.br.xadruffbackend.dto.direction

class UpStraight(
    hasCapture: Boolean = true,
    hasMovement: Boolean = true
) : Direction(hasCapture, hasMovement) {
    override fun getFutureRow(row: Int, index: Int) = row - index
    override fun getFutureColumn(column: Int, index: Int) = column
}

class DownStraight(
    hasCapture: Boolean = true,
    hasMovement: Boolean = true
) : Direction(hasCapture, hasMovement) {
    override fun getFutureRow(row: Int, index: Int) = row + index
    override fun getFutureColumn(column: Int, index: Int) = column
}

class RightStraight : Direction() {
    override fun getFutureRow(row: Int, index: Int) = row
    override fun getFutureColumn(column: Int, index: Int) = column + index
}

class LeftStraight : Direction() {
    override fun getFutureRow(row: Int, index: Int) = row
    override fun getFutureColumn(column: Int, index: Int) = column - index
}
