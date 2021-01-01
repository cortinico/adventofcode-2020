fun main() {
    object {}
        .javaClass
        .getResource("input-10.txt")
        .readText()
        .split("\n")
        .map(String::toLong)
        .toMutableList()
        .apply {
            add(0)
            sort()
            add(last() + 3)
            countCombinations(this, size - 1).also(::println)
        }
}

val map = mutableMapOf<Int, Long>()

fun countCombinations(list: List<Long>, index: Int): Long {
    if (index == 0) return 1
    if (index in map) return map[index]!!
    var count = 0L
    (1..3).forEach { step ->
        if (index >= step && list[index - step] in list[index] - 3..list[index] - step) {
            count += countCombinations(list, index - step)
        }
    }
    map[index] = count
    return count
}
