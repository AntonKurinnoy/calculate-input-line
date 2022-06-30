package org.test

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class ExpressionController(val expressionEvaluator: ExpressionEvaluator) {

    @PostMapping("/calculate-expression")
    suspend fun evaluate(@RequestBody request: Request): Response =
        Response(expressionEvaluator.evaluate(request.expression))

}