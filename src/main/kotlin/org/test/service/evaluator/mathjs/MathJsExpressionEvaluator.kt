package org.test.service.evaluator.mathjs

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.test.service.evaluator.ExpressionEvaluator

/**
 * @author Slava Gornostal
 */
@Service
class MathJsExpressionEvaluator(mathJsProperties: MathJsProperties) : ExpressionEvaluator {

    private val webClient = WebClient.builder()
        .baseUrl(mathJsProperties.apiUrl)
        .build()

    override suspend fun evaluate(exp: String): Double =
        webClient.get()
            .uri("?expr={exp}", exp)
            .retrieve()
            .awaitBody<String>()
            .toDouble()
}

@ConstructorBinding
@ConfigurationProperties(prefix = "mathjs")
data class MathJsProperties(val apiUrl: String)