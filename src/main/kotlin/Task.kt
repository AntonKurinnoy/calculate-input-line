fun main() {
    println("Enter line")
    val line = readLine()
    val regExpRes = line?.matches(Regex("""(^\d+)([-+/*]\d+)+"""))
    if (regExpRes != true) {
        println("Wrong line")
        return
    }

    val lineObj = Line(line)
    lineObj.getLineSum()

//    2+4+222/111*30/5*5-6*86/2*94+2*3+5+8/12*3
//    2+4+60.0-24252.0+6.0+5+2.0

}