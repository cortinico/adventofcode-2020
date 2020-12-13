fun main() {
    val input = object {}.javaClass.getResource("input-10.txt").readText().split("\n")
        .map(String::toLong)
        .toMutableList().apply {
            add(0)
            sort()
            add(last() + 3)
        }
    var count1 = 0
    var count3 = 0

    for (i in 0 until input.size - 1) {
        when (input[i]) {
            input[i + 1] - 1 -> {
                count1++
            }
            input[i + 1] - 3 -> {
                count3++
            }
        }
    }
    println(count1 * count3)
}


