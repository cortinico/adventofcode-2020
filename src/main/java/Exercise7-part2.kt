fun main() {
    val input = object {}.javaClass.getResource("input-7.txt").readText().split("\n")
    val graph = mutableMapOf<String, MutableList<Pair<Int,String>>>()

    input.map { it.replace(".", "") }
        .map { it.split("contain") }
        .map { (outside, inside) -> outside.replace("bags", "").trim() to inside.trim() }
        .onEach { (colorOutside, colorInsideList) ->
            colorInsideList.split(",").map(String::trim).forEach {
                if (!it.startsWith("no other")) {
                    val tokens = it.split(" ")
                    val number = tokens.first().toInt()
                    val colorInside = tokens.drop(1).dropLast(1).joinToString(" ")
                    (graph.getOrPut(colorOutside) { mutableListOf() }).add(number to colorInside)
                }
            }
        }
    val queue = mutableListOf(1 to "shiny gold")
    var total = 0
    while (queue.isNotEmpty()) {
        val (count, current) = queue.removeAt(0)
        graph[current]?.forEach {
            val (nextCount, next) = it
            total += (nextCount * count)
            queue.add(nextCount * count to next)
        }
    }
    println(total)
}


