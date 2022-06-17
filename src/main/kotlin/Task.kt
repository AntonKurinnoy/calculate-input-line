fun main() {
    println("Enter line")
    val line = readLine()
    var res = line
    var cs = res as CharSequence
    var matchResult = Regex("""((\d+)*[.]?(\d+))([/,*]((\d+)*[.]?(\d+)))+""").findAll(cs)
    for (element in matchResult){

        val numbers = arrayListOf<Double>()
        Regex("""\d+[.]?\d?""").findAll(element.value).forEach { it ->
            numbers.add(it.value.toDouble())
        }

        val signs = arrayListOf<String>()
        Regex("""(/|[*])""").findAll(element.value).forEach { it ->
            signs.add(it.value)
        }

        var sum = 0.00
        signs.forEachIndexed { index, el ->

            if (index < 1){
                if (signs[index] == "*") {
                    sum = numbers[index] * numbers[1]
                } else {
                    sum = numbers[index] / numbers[1]
                }
            } else {
                if (signs[index] == "*") {
                    sum *= numbers[index + 1].toDouble()
                } else {
                    if(numbers[index + 1].toDouble() > 0){
                        sum /= numbers[index + 1].toDouble()
                    } else throw Exception("Division by zero!")

                }
            }

        }
        res = res?.replaceFirst(element.value, sum.toString())
        cs = res as CharSequence

    }

    matchResult = Regex("""((\d+)*[.]?(\d+))([+,-]((\d+)*[.]?(\d+)))+""").findAll(cs)
    for (element in matchResult){
        val numbers = arrayListOf<Double>()
        Regex("""\d+[.]?\d?""").findAll(element.value).forEach { it ->
            numbers.add(it.value.toDouble())
        }

        val signs = arrayListOf<String>()
        Regex("""([+]|-)""").findAll(element.value).forEach { it ->
            signs.add(it.value)
        }

        var sum = 0.00
        signs.forEachIndexed { index, el ->

            if (index < 1){
                if (signs[index] == "+") {
                    sum = numbers[index] + numbers[1]
                } else {
                    sum = numbers[index] - numbers[1]
                }
            } else {
                if (signs[index] == "+") {
                    sum += numbers[index + 1].toDouble()
                } else {
                    sum -= numbers[index + 1].toDouble()
                }
            }
        }
        res = res?.replaceFirst(element.value, sum.toString())
        cs = res as CharSequence
    }

    println(cs)

}