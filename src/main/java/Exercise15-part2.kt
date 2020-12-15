fun main() {
    val input = object {}.javaClass.getResource("input-15.txt").readText().split(",").map(String::toInt)

    val map = mutableMapOf<Int, Pair<Int, Int>>()

    var last = -1
    repeat(30000000) { turn ->
        when {
            turn < input.size -> {
                map[input[turn]] = turn to -1
                last = input[turn]
            }
            last !in map || map[last]?.second == -1 -> {
                map[0] = turn to (map[0]?.first ?: -1)
                last = 0
            }
            else -> {
                val toSpeak = map[last]!!.first - map[last]!!.second
                map[toSpeak] = turn to (map[toSpeak]?.first ?: -1)
                last = toSpeak
            }
        }
    }
    println(last)
}
