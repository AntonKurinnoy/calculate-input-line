package org.test

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author Slava Gornostal
 */
@RestController
@RequestMapping("/api")
class ExpressionController {

    @PostMapping("/calculate-expression")
    suspend fun test(request: Request): Response {
        TODO()
    }

}