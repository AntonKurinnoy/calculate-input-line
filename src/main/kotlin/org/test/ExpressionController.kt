package org.test

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class ExpressionController {

    @PostMapping("/calculate-expression")
    suspend fun evaluate(@RequestBody request: Request): Response {
        return Response(SimpleExpressionEvaluator().evaluate(request.expression))
    }

}