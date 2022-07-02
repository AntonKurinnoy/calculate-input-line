package org.test.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * @author Anton Kurinnoy
 */
@Document("expression_calculation")
data class ExpressionCalculation(@Id val id: ObjectId? = null, val expression: String, val result: ExpressionResult)

data class ExpressionResult(val simple: Double, val mathJs: Double)