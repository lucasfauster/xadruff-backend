package com.uff.br.xadruffbackend.model.direction

class UpLeftL: Direction() {
    override fun getFutureLine(line:Int, index: Int) = line - 2
    override fun getFutureColumn(column:Int, index: Int) = column - 1
}

class UpRightL: Direction() {
    override fun getFutureLine(line:Int, index: Int) = line - 2
    override fun getFutureColumn(column:Int, index: Int) = column + 1
}

class DownLeftL: Direction() {
    override fun getFutureLine(line:Int, index: Int) = line + 2
    override fun getFutureColumn(column:Int, index: Int) = column - 1
}

class DownRightL: Direction() {
    override fun getFutureLine(line:Int, index: Int) = line + 2
    override fun getFutureColumn(column:Int, index: Int) = column + 1
}

class LeftUpL: Direction() {
    override fun getFutureLine(line:Int, index: Int) = line - 1
    override fun getFutureColumn(column:Int, index: Int) = column - 2
}

class LeftDownL: Direction() {
    override fun getFutureLine(line:Int, index: Int) = line + 1
    override fun getFutureColumn(column:Int, index: Int) = column - 2
}

class RightUpL: Direction() {
    override fun getFutureLine(line:Int, index: Int) = line - 1
    override fun getFutureColumn(column:Int, index: Int) = column + 2
}

class RightDownL: Direction() {
    override fun getFutureLine(line:Int, index: Int) = line + 1
    override fun getFutureColumn(column:Int, index: Int) = column + 2
}