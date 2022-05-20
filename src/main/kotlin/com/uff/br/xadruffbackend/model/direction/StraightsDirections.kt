package com.uff.br.xadruffbackend.model.direction

class UpStraight(hasCapture: Boolean = true,
                 hasMovement: Boolean = true
): Direction(hasCapture, hasMovement) {
    override fun getFutureLine(line:Int, index: Int) = line - index
    override fun getFutureColumn(column: Int, index: Int) = column
}

class DownStraight(hasCapture: Boolean = true,
                   hasMovement: Boolean = true
): Direction(hasCapture, hasMovement) {
    override fun getFutureLine(line:Int, index: Int) = line + index
    override fun getFutureColumn(column: Int, index: Int) = column
}

class RightStraight: Direction() {
    override fun getFutureLine(line:Int, index: Int) = line
    override fun getFutureColumn(column: Int, index: Int) = column + index
}

class LeftStraight: Direction() {
    override fun getFutureLine(line:Int, index: Int) = line
    override fun getFutureColumn(column: Int, index: Int) = column  - index
}
