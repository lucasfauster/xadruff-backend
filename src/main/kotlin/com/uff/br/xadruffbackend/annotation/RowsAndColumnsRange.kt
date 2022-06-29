package com.uff.br.xadruffbackend.annotation

import com.uff.br.xadruffbackend.dto.BOARD_SIZE
import javax.validation.Constraint
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.FIELD, AnnotationTarget.PROPERTY, AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.VALUE_PARAMETER
)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [RowsAndColumnsRangeValidator::class])
annotation class RowsAndColumnsRange(
    val message: String = "Invalid board size.",
    val value: Int = BOARD_SIZE,
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Any>> = []
)
