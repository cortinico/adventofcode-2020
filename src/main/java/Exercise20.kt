fun main() {
    val tiles = object {}.javaClass.getResource("input-20.txt").readText()
        .split("\n\n")
        .map {
            val number = it.split("\n")[0]
                .replace(":", "")
                .replace("Tile ", "")
                .toLong()
            val top = it.split("\n")[1]
            val bottom = it.split("\n").last()
            val (left, right) = it.split("\n").drop(1)
                .map {
                    it.toCharArray().let { it.first() to it.last() }
                }
                .fold("" to "") { acc, pair ->
                    (acc.first + pair.first) to (acc.second + pair.second)
                }
            val body = it.split("\n").drop(1)
                .map { it.replace("\n", "") }
                .map { it.toCharArray() }
                .toTypedArray()
            Tile(number, top, bottom, left, right, body)
        }

    tiles.filter {
        tiles.countNeighbor(it) == 2
    }.fold(1L) { acc, tile ->
        acc * tile.number
    }.also(::println)

}

fun List<Tile>.countNeighbor(
    input: Tile,
    sides: List<String> = listOf(input.left, input.top, input.right, input.bottom)
): Int {
    return sides.filter { regularSide ->
        val reversedSide = regularSide.reversed()
        this.any {
            it.number != input.number && (
                    it.top == regularSide ||
                            it.top == reversedSide ||
                            it.bottom == regularSide ||
                            it.bottom == reversedSide ||
                            it.left == regularSide ||
                            it.left == reversedSide ||
                            it.right == regularSide ||
                            it.right == reversedSide)
        }
    }.count()
}

class Tile(
    val number: Long,
    var top: String,
    var bottom: String,
    var left: String,
    var right: String,
    var body: Array<CharArray>
) {
    private val n: Int get() = body.size

    fun rotateRight() {
        body.rotatedRight()
        recomputeStrings()
    }

    fun rotateLeft() {
        body.rotatedLeft()
        recomputeStrings()
    }

    fun flipHorizontally() {
        body.flipHorizontally()
        recomputeStrings()
    }

    fun flipVertically() {
        body.flipVertically()
        recomputeStrings()
    }

    private fun recomputeStrings() {
        top = body[0].joinToString("")
        bottom = body.last().joinToString("")
        left = body.map { it.first() }.joinToString("")
        right = body.map { it.last() }.joinToString("")
    }
}

fun Array<CharArray>.flipHorizontally() {
    val n = this.size
    for (i in 0 until n / 2) {
        for (j in 0 until n) {
            val t = this[i][j]
            this[i][j] = this[n - i - 1][j]
            this[n - i - 1][j] = t
        }
    }
}

fun Array<CharArray>.flipVertically() {
    val n = this.size
    for (i in 0 until n / 2) {
        for (j in 0 until n) {
            val t = this[j][i]
            this[j][i] = this[j][n - i - 1]
            this[j][n - i - 1] = t
        }
    }
}

fun Array<CharArray>.rotatedRight() {
    val n = this.size
    val rotated = Array(n) { CharArray(n) }
    for (i in 0 until n) {
        for (j in 0 until n) {
            rotated[i][j] = this[n - j - 1][i]
        }
    }
    for (i in 0 until n) {
        for (j in 0 until n) {
            this[i][j] = rotated[i][j]
        }
    }
}

fun Array<CharArray>.rotatedLeft() {
    val n = this.size
    val rotated = Array(n) { CharArray(n) }
    for (i in 0 until n) {
        for (j in 0 until n) {
            rotated[i][j] = this[j][n - i - 1]
        }
    }
    for (i in 0 until n) {
        for (j in 0 until n) {
            this[i][j] = rotated[i][j]
        }
    }
}