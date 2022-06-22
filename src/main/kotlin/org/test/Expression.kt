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
        val newList = mutableListOf<Expression>()

        expressions.forEachIndexed { index, el ->
            if ((el is Expression.Operation) && (el.sign in listOf(Sign.DIV, Sign.MULT))) {
                val newItem =
                    calculate(newList.last() as Expression.Number, expressions.get(index + 1) as Expression.Number, el)
                newList.removeLast()
                newList.add(newItem)
            } else {
                if (index > 0) {
                    val prevItem = expressions.get(index - 1)
                    if ((prevItem is Expression.Operation) && (prevItem.sign in listOf(Sign.ADD, Sign.SUB))) {
                        newList.add(prevItem)
                        newList.add(el)
                    }
                } else newList.add(el)
            }
        }

        return newList
    }

    private fun calculateAddAndSub(expressions: List<Expression>): List<Expression> {
        val newList = mutableListOf<Expression>()
        newList.add(expressions.first())

        expressions.forEachIndexed { index, el ->
            if ((el is Expression.Operation) && (el.sign in listOf(Sign.ADD, Sign.SUB))) {
                val newItem =
                    calculate(newList.last() as Expression.Number, expressions.get(index + 1) as Expression.Number, el)
                newList.removeLast()
                newList.add(newItem)
            }
        }

        return newList
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

    private fun parseExpression(exp: String): List<Expression> {
        val matchResult = Regex("""((\d+)*[.]?(\d+))|([/,*,+,-])""").findAll(exp)
        val res = mutableListOf<Expression>()
        matchResult.forEach {
            when (it.value) {
                Sign.DIV.text -> res.add(Expression.Operation(Sign.DIV))
                Sign.MULT.text -> res.add(Expression.Operation(Sign.MULT))
                Sign.ADD.text -> res.add(Expression.Operation(Sign.ADD))
                Sign.SUB.text -> res.add(Expression.Operation(Sign.SUB))
                else -> res.add(Expression.Number(it.value.toDouble()))
            }
        }

        return res
    }

}