fun main() {
    val input = object {}.javaClass.getResource("input-16.txt").readText().split("\n\n")

    val ranges = input[0].split("\n")
        .associateBy({
            it.substring(0, it.indexOf(":"))
        }, {
            it.substring(it.indexOf(":") + 1)
                .replace(" or", "")
                .trim()
                .split(" ")
                .map { range ->
                    range.split("-").let { it[0].toInt()..it[1].toInt() }
                }
        })

    val myTicket = input[1].split("\n")[1]
        .split(",")
        .map(String::toLong)

    val validTickets = input[2].split("\n")
        .drop(1)
        .map { it.split(",").map(String::toInt) }
        .filter {
            it.all { value ->
                ranges.any { entry ->
                    entry.value.any { range -> value in range }
                }
            }
        }

    val possibleRules = (myTicket.indices).map { index ->
        ranges.values
            .mapIndexed { rangeIndex, ranges ->
                rangeIndex to ranges
            }
            .filter { (_, ranges) ->
                validTickets.all { ticket -> ranges.any { range -> ticket[index] in range } }
            }
            .map { (rangeIndex, _) -> rangeIndex }
            .toMutableList()
    }.toMutableList()

    val ordering = IntArray(possibleRules.size)

    while (possibleRules.any { it.isNotEmpty() }) {
        val indexToRemove = possibleRules.indexOfFirst { it.size == 1 }
        val valueToRemove = possibleRules[indexToRemove][0]
        possibleRules[indexToRemove].clear()
        ordering[indexToRemove] = valueToRemove
        possibleRules.onEach { it.remove(valueToRemove) }
    }

    ranges.entries
        .asSequence()
        .filter { it.key.startsWith("departure") }
        .mapIndexed { index, entry -> index to entry.key }
        .map { (index, _) -> myTicket[ordering.indexOf(index)] }
        .fold(1L) { acc, next -> acc * next }
        .also(::println)
}
