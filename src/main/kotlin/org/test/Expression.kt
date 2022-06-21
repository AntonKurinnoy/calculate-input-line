package org.test

/**
 * @author Slava Gornostal
 */
sealed class Expression(val text: String) {
    class Number(val value: Double) : Expression(value.toString())
    class Operation(val sign: Sign) : Expression(sign.text)
}

enum class Sign(val text: String) { DIV("/"), MULT("*"), ADD("+"), SUB("-") }


interface ExpressionEvaluator {
    fun evaluate(exp: String): Double
}

class SimpleExpressionEvaluator : ExpressionEvaluator {

    override fun evaluate(exp: String): Double {
        var expressions = parseExpression(exp)
        expressions = calculateMultAndDiv(expressions)
        expressions = calculateAddAndSub(expressions)
        return when (val result = expressions.first()) {
            is Expression.Number -> result.value
            else -> throw IllegalStateException("Invalid expression $exp")
        }
    }

    private fun calculateMultAndDiv(expressions: List<Expression>): List<Expression> {
        TODO()
    }

    private fun calculateAddAndSub(expressions: List<Expression>): List<Expression> {
        TODO()
    }

    private fun calculate(
        number1: Expression.Number,
        number2: Expression.Number,
        operation: Expression.Operation
    ): Expression.Number = when (operation.sign) {
        Sign.DIV -> TODO()
        Sign.MULT -> TODO()
        Sign.ADD -> TODO()
        Sign.SUB -> TODO()
    }

    private fun parseExpression(exp: String): List<Expression> {
        TODO()
    }

}