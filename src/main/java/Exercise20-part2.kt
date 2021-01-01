import kotlin.math.sqrt
import kotlin.random.Random

fun main() {
    val tiles =
        object {}.javaClass.getResource("input-20.txt").readText().split("\n\n").map {
            val number = it.split("\n")[0].replace(":", "").replace("Tile ", "").toLong()
            val top = it.split("\n")[1]
            val bottom = it.split("\n").last()
            val (left, right) = it
                .split("\n")
                .drop(1)
                .map { it.toCharArray().let { it.first() to it.last() } }
                .fold("" to "") { acc, pair ->
                    (acc.first + pair.first) to (acc.second + pair.second)
                }
            val body =
                it.split("\n")
                    .drop(1)
                    .map { it.replace("\n", "") }
                    .map { it.toCharArray() }
                    .toTypedArray()
            Tile(number, top, bottom, left, right, body)
        }

    val size = sqrt(tiles.size.toDouble()).toInt()
    val world = Array(size) { Array(size) { Tile(0, "", "", "", "", arrayOf()) } }

    // Let's start to create the world from 0,0.
    // We pick a tile that we know having a neighbor on the right
    world[0][0] =
        tiles.filter { tiles.countNeighbor(it) == 2 }.first {
            tiles.countNeighbor(it, listOf(it.right)) == 1
        }

    var current = 0 to 0

    while (world.hasEmptySpots()) {
        // We either move to the tile right or bottom and we scan
        // line the entire world till is completed.
        val next = findNextEmptySpot(current, size)
        val neighbor = tiles.findNeighbor(world, current, next)
        world[next.first][next.second] = neighbor
        current = next
    }

    val tileSize = world[0][0].body.size - 2
    val merged = Array(world.size * tileSize) { CharArray(world.size * tileSize) }

    for (i in world.indices) {
        for (j in world.indices) {
            // Copy tile i,j in the merged world
            val toCopy = world[i][j].body
            for (k in 1 until toCopy.size - 1) {
                for (l in 1 until toCopy.size - 1) {
                    merged[(k - 1) + (tileSize * i)][(l - 1) + (tileSize * j)] = toCopy[k][l]
                }
            }
        }
    }

    //                  #
    // #    ##    ##    ###
    // #  #  #  #  #  #
    val dragonPattern =
        listOf(
            0 to 0,
            +1 to +1,
            +4 to +1,
            +7 to +1,
            +10 to +1,
            +13 to +1,
            +16 to +1,
            +5 to 0,
            +6 to 0,
            +11 to 0,
            +12 to 0,
            +17 to 0,
            +18 to 0,
            +18 to -1,
            +19 to 0,
        )

    var found = false
    while (!found) {
        for (i in merged.indices) {
            for (j in merged.indices) {
                if (merged[i][j] == '#') {
                    val matches =
                        dragonPattern.count {
                            runCatching { merged[i + it.first][j + it.second] == '#' }
                                .getOrDefault(false)
                        }

                    // Dragon found! Let's stop the cycle once we found all of them
                    if (matches == dragonPattern.size) {
                        found = true
                        dragonPattern.onEach { merged[i + it.first][j + it.second] = '0' }
                    }
                }
            }
        }
        if (!found) {
            // Dragon not found! Let's try to rotate/flip the world
            when (Random.nextInt(3)) {
                0 -> merged.flipHorizontally()
                1 -> merged.flipVertically()
                else -> merged.rotatedRight()
            }
        }
    }

    merged.sumBy { line -> line.count { it == '#' } }.also(::println)
}

private fun List<Tile>.findNeighbor(
    world: Array<Array<Tile>>,
    current: Pair<Int, Int>,
    next: Pair<Int, Int>
): Tile {
    val input =
        if (next.second == current.second + 1) {
            world[current.first][current.second]
        } else {
            world[next.first - 1][next.second]
        }
    val side = if (next.second == current.second + 1) input.right else input.bottom

    if (next.second == current.second + 1) {
        // Regular sides
        this.firstOrNull { it.number != input.number && it.left == side }?.let {
            return it
        }
        this.firstOrNull { it.number != input.number && it.right == side }?.let {
            it.flipVertically()
            return it
        }
        this.firstOrNull { it.number != input.number && it.top == side }?.let {
            it.rotateLeft()
            it.flipHorizontally()
            return it
        }
        this.firstOrNull { it.number != input.number && it.bottom == side }?.let {
            it.rotateRight()
            return it
        }
        // Reversed
        this.firstOrNull { it.number != input.number && it.left.reversed() == side }?.let {
            it.flipHorizontally()
            return it
        }
        this.firstOrNull { it.number != input.number && it.right.reversed() == side }?.let {
            it.rotateLeft()
            it.rotateLeft()
            return it
        }
        this.firstOrNull { it.number != input.number && it.top.reversed() == side }?.let {
            it.rotateLeft()
            return it
        }
        this.firstOrNull { it.number != input.number && it.bottom.reversed() == side }?.let {
            it.rotateRight()
            it.flipHorizontally()
            return it
        }
    } else {
        // Regular sides
        this.firstOrNull { it.number != input.number && it.top == side }?.let {
            return it
        }
        this.firstOrNull { it.number != input.number && it.bottom == side }?.let {
            it.flipHorizontally()
            return it
        }
        this.firstOrNull { it.number != input.number && it.left == side }?.let {
            it.rotateRight()
            it.flipVertically()
            return it
        }
        this.firstOrNull { it.number != input.number && it.right == side }?.let {
            it.rotateLeft()
            return it
        }
        // Reversed
        this.firstOrNull { it.number != input.number && it.top.reversed() == side }?.let {
            it.flipVertically()
            return it
        }
        this.firstOrNull { it.number != input.number && it.bottom.reversed() == side }?.let {
            it.rotateRight()
            it.rotateRight()
            return it
        }
        this.firstOrNull { it.number != input.number && it.left.reversed() == side }?.let {
            it.rotateRight()
            return it
        }
        this.firstOrNull { it.number != input.number && it.right.reversed() == side }?.let {
            it.rotateLeft()
            it.flipVertically()
            return it
        }
    }
    error("Neighbor not found!")
}

private fun findNextEmptySpot(current: Pair<Int, Int>, size: Int): Pair<Int, Int> {
    return if (current.second < size - 1) {
        current.first to current.second + 1
    } else {
        current.first + 1 to 0
    }
}

private fun Array<Array<Tile>>.hasEmptySpots() = this.any { line -> line.any { it.number == 0L } }
