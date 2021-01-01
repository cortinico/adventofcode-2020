fun main() {
    object {}
        .javaClass
        .getResource("input-22.txt")
        .readText()
        .split("\n\n")
        .map { it.split("\n").drop(1).map(String::toInt).toMutableList() }
        .let { playRecursiveCombat(it[0] to it[1]) }
        .toList()
        .first { it.isNotEmpty() }
        .reversed()
        .foldIndexed(0L) { index: Int, acc: Long, next: Int ->
            acc + (index.toLong() + 1L) * next.toLong()
        }
        .also(::println)
}

fun playRecursiveCombat(
    input: Pair<MutableList<Int>, MutableList<Int>>
): Pair<MutableList<Int>, MutableList<Int>> {
    val (deck1, deck2) = input
    val seenConfigurations = hashSetOf<String>()
    while (deck1.isNotEmpty() && deck2.isNotEmpty()) {
        val top: List<Int>
        val winner: MutableList<Int>

        val configuration = deck1.joinToString(",")
        if (configuration in seenConfigurations) {
            // Prevent recursion.
            top = listOf(deck1.removeAt(0), deck2.removeAt(0))
            winner = deck1
            deck1.addAll(listOf(deck1.removeAt(0), deck2.removeAt(0)))
        } else {
            seenConfigurations.add(configuration)

            if (deck1[0] <= deck1.size - 1 && deck2[0] <= deck2.size - 1) {
                // Recursive Case
                val subGameInput =
                    deck1.drop(1).take(deck1[0]).toMutableList() to
                        deck2.drop(1).take(deck2[0]).toMutableList()
                val (subDeck1, _) = playRecursiveCombat(subGameInput)

                if (subDeck1.isEmpty()) {
                    winner = deck2
                    top = listOf(deck2.removeAt(0), deck1.removeAt(0))
                } else {
                    winner = deck1
                    top = listOf(deck1.removeAt(0), deck2.removeAt(0))
                }
            } else {
                // Non-Recursive Case
                if (deck1[0] > deck2[0]) {
                    winner = deck1
                    top = listOf(deck1.removeAt(0), deck2.removeAt(0))
                } else {
                    winner = deck2
                    top = listOf(deck2.removeAt(0), deck1.removeAt(0))
                }
            }
        }
        winner.add(top[0])
        winner.add(top[1])
    }
    return input
}
