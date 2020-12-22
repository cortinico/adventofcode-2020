fun main() {
    val (deck1, deck2) = object {}.javaClass.getResource("input-22.txt").readText()
        .split("\n\n")
        .map {
            it.split("\n")
                .drop(1)
                .map(String::toInt)
                .toMutableList()
        }

    while (deck1.isNotEmpty() && deck2.isNotEmpty()) {
        val top = listOf(deck1.removeAt(0), deck2.removeAt(0))
        if (top[0] > top[1]) { deck1 } else { deck2 }.addAll(top.sortedDescending())
    }
    listOf(deck1, deck2).first { it.isNotEmpty() }
        .reversed()
        .foldIndexed(0L) { index: Int, acc: Long, next: Int ->
            acc + (index.toLong() +1L) * next.toLong()
        }.also(::println)
}
