package org.test.service

import org.springframework.stereotype.Service
import org.test.entity.ExpressionCalculation
import org.test.form.RequestForm
import org.test.repository.ExpressionCalculationRepository
import org.test.service.evaluator.ExpressionEvaluator

/**
 * @author Anton Kurinnoy
 */
interface ExpressionEvaluatorService {
    suspend fun evaluate(request: RequestForm): Double
}

@Service
class ExpressionEvaluatorServiceImpl(
    val expressionCalculationRepository: ExpressionCalculationRepository,
    val expressionEvaluator: ExpressionEvaluator
) :
    ExpressionEvaluatorService {
    override suspend fun evaluate(request: RequestForm): Double {
        val result = expressionEvaluator.evaluate(request.expression)
        expressionCalculationRepository.save(ExpressionCalculation(expression = request.expression, result = result))
        return result
    }

}