package org.test

class Line(private val line: String) {

    fun getLineSum() {
        val modifiedLine = modifyLine(line as CharSequence)

        val plusList = modifiedLine.split("+").map {
            if (it.contains("-")) {
                val ml = it.split("-")
                var first = ml.first().toDouble()
                ml.forEachIndexed() { index, el ->
                    if (index > 0) {
                        first -= el.toDouble()
                    }
                }
                first
            } else {
                it.toDouble()
            }
        }

        println(plusList.sum())
    }

    /**
     * return line with calculated blocks "a*b/c"
     */
    private fun modifyLine(line: CharSequence): String {
        var res = line.toString()
        val matchResult = Regex("""((\d+)*[.]?(\d+))([/,*]((\d+)*[.]?(\d+)))+""").findAll(line)
        for (element in matchResult) {

            val numbers = arrayListOf<Double>()
            Regex("""\d+[.]?\d?""").findAll(element.value).forEach {
                numbers.add(it.value.toDouble())
            }

            val signs = arrayListOf<String>()
            Regex("""(/|[*])""").findAll(element.value).forEach {
                signs.add(it.value)
            }

            var sum = 0.00
            signs.forEachIndexed { index, _ ->
                when (index) {
                    0 -> sum = if (signs[index] == "*") {
                        numbers[index] * numbers[1]
                    } else {
                        numbers[index] / numbers[1]
                    }
                    else -> if (signs[index] == "*") {
                        sum *= numbers[index + 1]
                    } else {
                        if (numbers[index + 1] != 0.0) {
                            sum /= numbers[index + 1]
                        } else throw Exception("Division by zero!")
                    }
                }
            }
            res = res.replaceFirst(element.value, sum.toString())
        }

        return res
    }
}