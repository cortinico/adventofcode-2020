fun main() {
    val input = object {}.javaClass.getResource("input-13.txt").readText().split("\n")

    val earliestDeparture: Int = input[0].toInt()
    val busses = input[1].split(",").filter { it != "x" }.map(String::toInt)

    var time = earliestDeparture
    while (true) {
        val departingBus = busses.firstOrNull { time % it == 0 }
        if (departingBus != null) {
            println("Depart at $time with $departingBus")
            println("${(time-earliestDeparture)*departingBus}")
            break
        }
        time++
    }
}
