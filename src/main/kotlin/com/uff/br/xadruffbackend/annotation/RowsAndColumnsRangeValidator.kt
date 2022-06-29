package com.uff.br.xadruffbackend.annotation

import com.uff.br.xadruffbackend.dto.BOARD_SIZE
import com.uff.br.xadruffbackend.dto.request.BoardRequest
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class RowsAndColumnsRangeValidator : ConstraintValidator<RowsAndColumnsRange, BoardRequest?> {

    private var validatorValue: Int = BOARD_SIZE

    override fun initialize(constraintAnnotation: RowsAndColumnsRange) {
        validatorValue = constraintAnnotation.value
    }

    override fun isValid(boardRequest: BoardRequest?, context: ConstraintValidatorContext): Boolean {
        return boardRequest?.let {
            boardRequest.positions.count() == validatorValue && isAllRowsValid(boardRequest)
        } ?: true
    }

    private fun isAllRowsValid(boardRequest: BoardRequest): Boolean {
        boardRequest.positions.forEach {
            if (it.count() != validatorValue)
                return false
        }
        return true
    }
}
