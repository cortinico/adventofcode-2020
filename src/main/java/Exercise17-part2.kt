fun main() {
    val input: List<CharArray> = object {}.javaClass.getResource("input-17.txt").readText()
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

    var world = create4DWorld(size)
    var copy = create4DWorld(size)
    world[size / 2][size / 2] = startSlice

    repeat(step) {
        for (m in 0 until size) {
            for (k in 0 until size) {
                for (i in 0 until size) {
                    for (j in 0 until size) {
                        val countActive: Int = countActiveNeighbors(world, m, k, i, j)
                        copy[m][k][i][j] = when {
                            world[m][k][i][j] == '#' && countActive in 2..3 -> '#'
                            world[m][k][i][j] == '#' && countActive !in 2..3 -> '.'
                            world[m][k][i][j] == '.' && countActive == 3 -> '#'
                            else -> '.'
                        }
                    }
                }
            }
        }
        world = copy
        copy = create4DWorld(size)
    }

    world.fold(0) { acc, next ->
        acc + next.fold(0) { acc2, next2 ->
            acc2 + next2.fold(0) { acc3, chars ->
                acc3 + chars.count { it == '#' }
            }
        }
    }.also(::println)
}

fun create4DWorld(size : Int) = Array(size) { Array(size) { Array(size) { CharArray(size) { '.' } } } }

data class Coord(
    val m: Int,
    val k: Int,
    val i: Int,
    val j: Int,
)

fun countActiveNeighbors(
    array: Array<Array<Array<CharArray>>>,
    m: Int,
    k: Int,
    i: Int,
    j: Int
) = listOf(
    Coord(m + 1, k + 1, i + 1, j + 1),
    Coord(m + 1, k + 1, i + 1, j - 1),
    Coord(m + 1, k + 1, i + 1, j),
    Coord(m + 1, k + 1, i - 1, j + 1),
    Coord(m + 1, k + 1, i - 1, j - 1),
    Coord(m + 1, k + 1, i - 1, j),
    Coord(m + 1, k + 1, i, j + 1),
    Coord(m + 1, k + 1, i, j - 1),
    Coord(m + 1, k + 1, i, j),
    Coord(m + 1, k - 1, i + 1, j + 1),
    Coord(m + 1, k - 1, i + 1, j - 1),
    Coord(m + 1, k - 1, i + 1, j),
    Coord(m + 1, k - 1, i - 1, j + 1),
    Coord(m + 1, k - 1, i - 1, j - 1),
    Coord(m + 1, k - 1, i - 1, j),
    Coord(m + 1, k - 1, i, j + 1),
    Coord(m + 1, k - 1, i, j - 1),
    Coord(m + 1, k - 1, i, j),
    Coord(m + 1, k, i + 1, j + 1),
    Coord(m + 1, k, i + 1, j - 1),
    Coord(m + 1, k, i + 1, j),
    Coord(m + 1, k, i - 1, j + 1),
    Coord(m + 1, k, i - 1, j - 1),
    Coord(m + 1, k, i - 1, j),
    Coord(m + 1, k, i, j + 1),
    Coord(m + 1, k, i, j - 1),
    Coord(m + 1, k, i, j),
    Coord(m - 1, k + 1, i + 1, j + 1),
    Coord(m - 1, k + 1, i + 1, j - 1),
    Coord(m - 1, k + 1, i + 1, j),
    Coord(m - 1, k + 1, i - 1, j + 1),
    Coord(m - 1, k + 1, i - 1, j - 1),
    Coord(m - 1, k + 1, i - 1, j),
    Coord(m - 1, k + 1, i, j + 1),
    Coord(m - 1, k + 1, i, j - 1),
    Coord(m - 1, k + 1, i, j),
    Coord(m - 1, k - 1, i + 1, j + 1),
    Coord(m - 1, k - 1, i + 1, j - 1),
    Coord(m - 1, k - 1, i + 1, j),
    Coord(m - 1, k - 1, i - 1, j + 1),
    Coord(m - 1, k - 1, i - 1, j - 1),
    Coord(m - 1, k - 1, i - 1, j),
    Coord(m - 1, k - 1, i, j + 1),
    Coord(m - 1, k - 1, i, j - 1),
    Coord(m - 1, k - 1, i, j),
    Coord(m - 1, k, i + 1, j + 1),
    Coord(m - 1, k, i + 1, j - 1),
    Coord(m - 1, k, i + 1, j),
    Coord(m - 1, k, i - 1, j + 1),
    Coord(m - 1, k, i - 1, j - 1),
    Coord(m - 1, k, i - 1, j),
    Coord(m - 1, k, i, j + 1),
    Coord(m - 1, k, i, j - 1),
    Coord(m - 1, k, i, j),
    Coord(m, k + 1, i + 1, j + 1),
    Coord(m, k + 1, i + 1, j - 1),
    Coord(m, k + 1, i + 1, j),
    Coord(m, k + 1, i - 1, j + 1),
    Coord(m, k + 1, i - 1, j - 1),
    Coord(m, k + 1, i - 1, j),
    Coord(m, k + 1, i, j + 1),
    Coord(m, k + 1, i, j - 1),
    Coord(m, k + 1, i, j),
    Coord(m, k - 1, i + 1, j + 1),
    Coord(m, k - 1, i + 1, j - 1),
    Coord(m, k - 1, i + 1, j),
    Coord(m, k - 1, i - 1, j + 1),
    Coord(m, k - 1, i - 1, j - 1),
    Coord(m, k - 1, i - 1, j),
    Coord(m, k - 1, i, j + 1),
    Coord(m, k - 1, i, j - 1),
    Coord(m, k - 1, i, j),
    Coord(m, k, i + 1, j + 1),
    Coord(m, k, i + 1, j - 1),
    Coord(m, k, i + 1, j),
    Coord(m, k, i - 1, j + 1),
    Coord(m, k, i - 1, j - 1),
    Coord(m, k, i - 1, j),
    Coord(m, k, i, j + 1),
    Coord(m, k, i, j - 1),
).asSequence()
    .filter { it.m >= 0 }
    .filter { it.m < array.size }
    .filter { it.k >= 0 }
    .filter { it.k < array[0].size }
    .filter { it.i >= 0 }
    .filter { it.i < array[0][0].size }
    .filter { it.j >= 0 }
    .filter { it.j < array[0][0][0].size }
    .count { array[it.m][it.k][it.i][it.j] == '#' }
