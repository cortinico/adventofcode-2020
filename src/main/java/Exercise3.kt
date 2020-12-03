fun main() {
    val input = object {}.javaClass.getResource("input-3.txt").readText().split("\n")

    val size = input[0].length - 1
    var current = 0

    input.fold(0) { acc , line ->
        val next = if (line[current] == '#') {
            acc + 1
        } else {
            acc
        }
        current = (current+3) % (size+1)
        next
    }.also(::println)
}