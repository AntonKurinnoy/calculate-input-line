package org.test.service.evaluator

/**
 * @author Anton Kurinnoy
 */
interface ExpressionEvaluator {
    fun evaluate(exp: String): Double
}

sealed class Expression(val text: String) {
    class Number(val value: Double) : Expression(value.toString())
    class Operation(val sign: Sign) : Expression(sign.text)
}

enum class Sign(val text: String) { DIV("/"), MULT("*"), ADD("+"), SUB("-") }