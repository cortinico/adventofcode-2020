fun main() {
    val input = object {}.javaClass.getResource("input-5.txt").readText().split('\n')

    val ids =
        input
            .map {
                val row = findBinary(128, it.substring(0, 7))
                val column = findBinary(8, it.substring(7))
                (row * 8) + column
            }
            .sorted()

    ids.forEachIndexed { index, id ->
        if (index != 0 && index != ids.size - 1 && ids[index] == ids[index + 1] - 2) {
            println(id + 1)
            return
        }
    }
}
