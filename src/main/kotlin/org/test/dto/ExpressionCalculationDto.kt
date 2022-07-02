package org.test.dto

import org.bson.types.ObjectId
import org.test.entity.ExpressionCalculation
import org.test.entity.ExpressionResult

/**
 * @author Slava Gornostal
 */
data class ExpressionCalculationDto(val id: ObjectId, val expression: String, val result: ExpressionResult)

fun ExpressionCalculation.toDto() = ExpressionCalculationDto(id = id!!, expression = expression, result = result)