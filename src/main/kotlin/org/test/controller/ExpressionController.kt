package org.test.controller

import kotlinx.coroutines.flow.map
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.test.dto.toDto
import org.test.form.RequestForm
import org.test.form.RequestListForm
import org.test.service.ExpressionEvaluatorService

@RestController
@RequestMapping("/api")
class ExpressionController(
    val expressionEvaluatorService: ExpressionEvaluatorService
) {
    @PostMapping("/calculate-expression")
    suspend fun evaluate(@RequestBody request: RequestForm) = expressionEvaluatorService.evaluate(request).toDto()

    @PostMapping("/calculate-expressions")
    fun calculate(@RequestBody request: RequestListForm) = expressionEvaluatorService.evaluateList(request)
        .map { it.toDto() }


}