package com.uff.br.xadruffbackend.model.direction

class DownLeftDiagonal(
    hasCapture: Boolean = true,
    hasMovement: Boolean = true
) : Direction(hasCapture, hasMovement) {
    override fun getFutureRow(row: Int, index: Int) = row + index
    override fun getFutureColumn(column: Int, index: Int) = column - index
}

class UpLeftDiagonal(
    hasCapture: Boolean = true,
    hasMovement: Boolean = true
) : Direction(hasCapture, hasMovement) {
    override fun getFutureRow(row: Int, index: Int) = row - index
    override fun getFutureColumn(column: Int, index: Int) = column - index
}

class DownRightDiagonal(
    hasCapture: Boolean = true,
    hasMovement: Boolean = true
) : Direction(hasCapture, hasMovement) {
    override fun getFutureRow(row: Int, index: Int) = row + index
    override fun getFutureColumn(column: Int, index: Int) = column + index
}

class UpRightDiagonal(
    hasCapture: Boolean = true,
    hasMovement: Boolean = true
) : Direction(hasCapture, hasMovement) {
    override fun getFutureRow(row: Int, index: Int) = row - index
    override fun getFutureColumn(column: Int, index: Int) = column + index
}
