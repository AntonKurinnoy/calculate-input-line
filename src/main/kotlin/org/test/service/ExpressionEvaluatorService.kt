package org.test.service

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
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
    fun evaluateList(request: RequestListForm): Flow<ExpressionCalculation>
}

@Service
class ExpressionEvaluatorServiceImpl(
    val expressionCalculationRepository: ExpressionCalculationRepository,
    val simpleExpressionEvaluator: ExpressionEvaluator,
    val mathJsExpressionEvaluator: ExpressionEvaluator,
) : ExpressionEvaluatorService {

    override suspend fun evaluate(request: RequestForm): ExpressionCalculation = calculate(request.expression)

    override fun evaluateList(request: RequestListForm): Flow<ExpressionCalculation> = request
        .expressions
        .asFlow()
        .map { calculate(it) }

    private suspend fun calculate(expression: String): ExpressionCalculation = coroutineScope {
        val simpleResult = async { simpleExpressionEvaluator.evaluate(expression) }
        val mathJsResult = async { mathJsExpressionEvaluator.evaluate(expression) }
        expressionCalculationRepository.save(
            ExpressionCalculation(
                expression = expression,
                result = ExpressionResult(simpleResult.await(), mathJsResult.await())
            )
        )
    }

}