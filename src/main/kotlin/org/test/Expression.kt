package org.test

sealed class Expression(val text: String) {
    class Number(val value: Double) : Expression(value.toString())
    class Operation(val sign: Sign) : Expression(sign.text)
}

enum class Sign(val text: String) { DIV("/"), MULT("*"), ADD("+"), SUB("-") }


interface ExpressionEvaluator {
    fun evaluate(exp: String): Double
}

class SimpleExpressionEvaluator : ExpressionEvaluator {
    companion object {
        private val EXPRESSION_MATCH_REGEX = Regex("""((\d+)*[.]?(\d+))|([/,*,+,-])""")
    }

    override fun evaluate(exp: String): Double {
        val expressions = parseExpression(exp)
        val expressionWithoutMultAndDiv = calculateMultAndDiv(expressions)
        val result = calculateAddAndSub(expressionWithoutMultAndDiv)
        return when (result) {
            is Expression.Number -> result.value
            else -> throw IllegalStateException("Invalid expression $exp")
        }
    }

    private fun calculateMultAndDiv(expressions: List<Expression>): List<Expression> =
        expressions.foldIndexed(emptyList()) { index, acc, expression ->
            when (index) {
                0 -> acc + expression
                else -> {
                    when (expression) {
                        is Expression.Operation -> if (expression.sign in listOf(Sign.DIV, Sign.MULT)) {
                            val result = acc.dropLast(1)
                            result + calculate(
                                acc.last() as Expression.Number,
                                expressions.elementAt(index + 1) as Expression.Number,
                                expression
                            )
                        } else {
                            acc + expression
                        }
                        is Expression.Number -> {
                            val prevItem = expressions.elementAt(index - 1)
                            when (prevItem) {
                                is Expression.Operation -> if (prevItem.sign in listOf(Sign.ADD, Sign.SUB)) {
                                    acc + expression
                                } else {
                                    acc
                                }
                                else -> {
                                    acc
                                }
                            }
                        }
                    }
                }
            }
        }

    private fun calculateAddAndSub(expressions: List<Expression>): Expression =
        expressions.reduceIndexed { index, sum, el ->
            when (el) {
                is Expression.Number -> sum
                is Expression.Operation ->
                    calculate(
                        sum as Expression.Number,
                        expressions.elementAt(index + 1) as Expression.Number,
                        el
                    )
            }
        }

    private fun calculate(
        number1: Expression.Number,
        number2: Expression.Number,
        operation: Expression.Operation
    ): Expression.Number = when (operation.sign) {
        Sign.DIV -> if (number2.value != 0.0) Expression.Number(number1.value / number2.value) else throw Exception("Division by zero!")
        Sign.MULT -> Expression.Number(number1.value * number2.value)
        Sign.ADD -> Expression.Number(number1.value + number2.value)
        Sign.SUB -> Expression.Number(number1.value - number2.value)
    }

    private fun parseExpression(exp: String): List<Expression> =
        EXPRESSION_MATCH_REGEX.findAll(exp)
            .map {
                when (it.value) {
                    Sign.DIV.text -> Expression.Operation(Sign.DIV)
                    Sign.MULT.text -> Expression.Operation(Sign.MULT)
                    Sign.ADD.text -> Expression.Operation(Sign.ADD)
                    Sign.SUB.text -> Expression.Operation(Sign.SUB)
                    else -> Expression.Number(it.value.toDouble())
                }
            }
            .toList()

}