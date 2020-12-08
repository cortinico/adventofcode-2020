fun main() {
    val input = object {}.javaClass.getResource("input-7.txt").readText().split("\n")
    val graph = mutableMapOf<String, MutableList<String>>()

    input.map { it.replace(".", "") }
        .map { it.split("contain") }
        .map { (outside, inside) -> outside.replace("bags", "").trim() to inside.trim() }
        .onEach { (colorOutside, colorInsideList) ->
            colorInsideList.split(",").map(String::trim).forEach {
                if (!it.startsWith("no other")) {
                    val colorInside = it.split(" ").drop(1).dropLast(1).joinToString(" ")
                    (graph.getOrPut(colorInside) { mutableListOf() }).add(colorOutside)
                }
            }
        }
    val visited = mutableSetOf<String>()
    val queue = mutableListOf("shiny gold")

    while (queue.isNotEmpty()) {
        val next = queue.removeAt(0)
        if (next !in visited) {
            visited.add(next)
            queue.addAll(graph[next] ?: emptyList())
        }
    }
    println(visited.size - 1)
}


