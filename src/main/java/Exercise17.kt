fun main() {
    val input: List<CharArray> =
        object {}
            .javaClass
            .getResource("input-17.txt")
            .readText()
            .split("\n")
            .map(String::toCharArray)

    val step = 6
    val size = input.size + (step * 2)
    val startSlice = Array(size) { CharArray(size) { '.' } }

    for (i in input.indices) {
        for (j in input[0].indices) {
            startSlice[step + i][step + j] = input[i][j]
        }
    }

    var world = create3DWorld(size)
    var copy = create3DWorld(size)
    world[size / 2] = startSlice

    repeat(step) {
        for (k in 0 until size) {
            for (i in 0 until size) {
                for (j in 0 until size) {
                    val countActive: Int = countActiveNeighbors(world, k, i, j)
                    copy[k][i][j] =
                        when {
                            world[k][i][j] == '#' && countActive in 2..3 -> '#'
                            world[k][i][j] == '#' && countActive !in 2..3 -> '.'
                            world[k][i][j] == '.' && countActive == 3 -> '#'
                            else -> '.'
                        }
                }
            }
        }
        world = copy
        copy = create3DWorld(size)
    }

    world
        .fold(0) { acc, next ->
            acc + next.fold(0) { acc2, chars -> acc2 + chars.count { it == '#' } }
        }
        .also(::println)
}

fun create3DWorld(size: Int) = Array(size) { Array(size) { CharArray(size) { '.' } } }

fun countActiveNeighbors(array: Array<Array<CharArray>>, k: Int, i: Int, j: Int) =
    listOf(
        Triple(k + 1, i + 1, j + 1),
        Triple(k + 1, i + 1, j - 1),
        Triple(k + 1, i + 1, j),
        Triple(k + 1, i - 1, j + 1),
        Triple(k + 1, i - 1, j - 1),
        Triple(k + 1, i - 1, j),
        Triple(k + 1, i, j + 1),
        Triple(k + 1, i, j - 1),
        Triple(k + 1, i, j),
        Triple(k - 1, i + 1, j + 1),
        Triple(k - 1, i + 1, j - 1),
        Triple(k - 1, i + 1, j),
        Triple(k - 1, i - 1, j + 1),
        Triple(k - 1, i - 1, j - 1),
        Triple(k - 1, i - 1, j),
        Triple(k - 1, i, j + 1),
        Triple(k - 1, i, j - 1),
        Triple(k - 1, i, j),
        Triple(k, i + 1, j + 1),
        Triple(k, i + 1, j - 1),
        Triple(k, i + 1, j),
        Triple(k, i - 1, j + 1),
        Triple(k, i - 1, j - 1),
        Triple(k, i - 1, j),
        Triple(k, i, j + 1),
        Triple(k, i, j - 1),
    )
        .asSequence()
        .filter { it.first >= 0 }
        .filter { it.first < array.size }
        .filter { it.second >= 0 }
        .filter { it.second < array[0].size }
        .filter { it.third >= 0 }
        .filter { it.third < array[0][0].size }
        .count { array[it.first][it.second][it.third] == '#' }
