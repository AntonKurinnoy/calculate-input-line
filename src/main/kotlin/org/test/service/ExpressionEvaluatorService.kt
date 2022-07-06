package org.test.service

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import org.springframework.stereotype.Service
import org.test.entity.ExpressionCalculation
import org.test.entity.ExpressionResult
import org.test.form.RequestForm
import org.test.form.RequestListForm
import org.test.repository.ExpressionCalculationRepository
import org.test.service.evaluator.ExpressionEvaluator

/**
 * @author Anton Kurinnoy
 */
interface ExpressionEvaluatorService {
    suspend fun evaluate(request: RequestForm): ExpressionCalculation
    suspend fun evaluateList(request: RequestListForm): Flow<ExpressionCalculation>
}

@Service
class ExpressionEvaluatorServiceImpl(
    val expressionCalculationRepository: ExpressionCalculationRepository,
    val simpleExpressionEvaluator: ExpressionEvaluator,
    val mathJsExpressionEvaluator: ExpressionEvaluator,
) : ExpressionEvaluatorService {
    override suspend fun evaluate(request: RequestForm): ExpressionCalculation = coroutineScope {
        val simpleResult = simpleExpressionEvaluator.evaluate(request.expression)
        val mathJsResult = mathJsExpressionEvaluator.evaluate(request.expression)
        expressionCalculationRepository.save(
            ExpressionCalculation(
                expression = request.expression,
                result = ExpressionResult(simpleResult, mathJsResult)
            )
        )
    }

    override suspend fun evaluateList(request: RequestListForm): Flow<ExpressionCalculation> = flow {
        request.expressions.asFlow().collect {
            evaluate(RequestForm(expression = it))
        }
    }

}