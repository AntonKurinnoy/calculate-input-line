package org.test.service

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import org.test.entity.ExpressionCalculation
import org.test.form.RequestForm
import org.test.form.RequestListForm
import org.test.repository.ExpressionCalculationRepository
import org.test.service.evaluator.ExpressionEvaluator

/**
 * @author Anton Kurinnoy
 */
interface ExpressionEvaluatorService {
    suspend fun evaluate(request: RequestForm): ExpressionCalculation
    fun evaluateList(request: RequestListForm): Flow<ExpressionCalculation>
}

@Service
class ExpressionEvaluatorServiceImpl(
    val expressionCalculationRepository: ExpressionCalculationRepository,
    val simpleExpressionEvaluator: ExpressionEvaluator,
    val mathJsExpressionEvaluator: ExpressionEvaluator,
) : ExpressionEvaluatorService {
    override suspend fun evaluate(request: RequestForm): ExpressionCalculation {
        /*val result = simpleExpressionEvaluator.evaluate(request.expression)
        return expressionCalculationRepository.save(
            ExpressionCalculation(
                expression = request.expression,
                result = result
            )
        )*/
        TODO("Not yet implemented")
    }

    override fun evaluateList(request: RequestListForm): Flow<ExpressionCalculation> {
        TODO("Not yet implemented")
    }

}

// TODO: паралельно посчитать своим ев и с помощью АПИ и записать в бд оба результата, добавить ендпоинт для расчета списка из expression
// почитать про WebClient, Kotlin Coroutines