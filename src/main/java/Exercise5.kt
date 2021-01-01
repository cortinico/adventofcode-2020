fun main() {
    val input = object {}.javaClass.getResource("input-5.txt").readText().split('\n')

    input
        .map {
            val row = findBinary(128, it.substring(0, 7))
            val column = findBinary(8, it.substring(7))
            (row * 8) + column
        }
        .maxOrNull()
        .also(::println)
}

fun findBinary(max: Int, key: String): Int {
    var l = 0
    var r = max
    var idx = 0
    while (idx < key.length) {
        when (key[idx]) {
            'F', 'L' -> r = (l + r) / 2
            'B', 'R' -> l = (l + r) / 2
        }
        idx++
    }
    return l
}
