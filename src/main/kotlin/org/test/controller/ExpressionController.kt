package org.test.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.test.dto.ResponseDto
import org.test.form.RequestForm
import org.test.service.ExpressionEvaluatorService

@RestController
@RequestMapping("/api")
class ExpressionController(
    val expressionEvaluatorService: ExpressionEvaluatorService
) {
    @PostMapping("/calculate-expression")
    suspend fun evaluate(@RequestBody request: RequestForm) = ResponseDto(expressionEvaluatorService.evaluate(request))

}