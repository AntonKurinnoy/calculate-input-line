package org.test.repository

import org.bson.types.ObjectId
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.test.entity.ExpressionCalculation

/**
 * @author Anton Kurinnoy
 */
interface ExpressionCalculationRepository: CoroutineCrudRepository<ExpressionCalculation, ObjectId>