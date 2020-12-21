fun main() {
    val food = object {}.javaClass.getResource("input-21.txt").readText()
        .split("\n")
        .map { it.replace("(", "").replace(")", "").replace(",", "") }
        .map { it.split(" contains ") }
        .map {
            it[0].trim().split(" ").toMutableSet() to it[1].trim().split(" ").toSet()
        }

    val allergenes = food.flatMap { it.second }.distinct()
        .associateWith { allergene ->
            food.filter { allergene in it.second }
                .map { it.first }
                .reduce { acc, next ->
                    (acc intersect next).toMutableSet()
                }
        }
        .toMutableMap()

    while(allergenes.isNotEmpty()) {
        val entryToRemove = allergenes.filter { it.value.size == 1 }.entries.first()
        val keyToRemove = entryToRemove.key
        val valueToRemove = entryToRemove.value.first()
        allergenes.remove(keyToRemove)
        food.forEach { (ingredients, _) -> ingredients.remove(valueToRemove) }
        allergenes.forEach { (_, value) -> value.remove(valueToRemove) }
    }
    food.sumBy { it.first.size }.also(::println)
}
