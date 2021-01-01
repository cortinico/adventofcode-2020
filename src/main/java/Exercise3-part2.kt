fun main() {
    val input = object {}.javaClass.getResource("input-3.txt").readText().split("\n")

    (computeSlope(input, 1, 1) *
            computeSlope(input, 3, 1) *
            computeSlope(input, 5, 1) *
            computeSlope(input, 7, 1) *
            computeSlope(input, 1, 2))
        .also(::println)
}

fun computeSlope(input: List<String>, left: Int, down: Int): Int {
    val size = input[0].length - 1
    var currentLeft = 0
    var downCounter = 1
    return input.fold(0) { acc, line ->
        downCounter--
        if (downCounter != 0) {
            return@fold acc
        } else {
            downCounter = down
        }
        val next =
            if (line[currentLeft] == '#') {
                acc + 1
            } else {
                acc
            }
        currentLeft = (currentLeft + left) % (size + 1)
        next
    }
}
