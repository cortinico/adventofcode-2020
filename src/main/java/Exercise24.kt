fun main() {
    val input =
        object {}.javaClass.getResource("input-24.txt").readText().split("\n").map {
            var idx = 0
            val result = mutableListOf<Orient>()
            while (idx < it.length) {
                val token =
                    when (it[idx]) {
                        'e' -> Orient.E
                        's' -> {
                            idx++
                            if (it[idx] == 'e') Orient.SE else Orient.SW
                        }
                        'n' -> {
                            idx++
                            if (it[idx] == 'w') Orient.NW else Orient.NE
                        }
                        else -> Orient.W
                    }
                idx++
                result.add(token)
            }
            result
        }

    val tiles = mutableMapOf<Pair<Int, Int>, Int>()
    input.onEach {
        var (x, y) = 0 to 0
        it.forEach { orient ->
            when (orient) {
                Orient.E -> x += 2
                Orient.SE -> {
                    y--
                    x++
                }
                Orient.SW -> {
                    y--
                    x--
                }
                Orient.W -> x -= 2
                Orient.NE -> {
                    y++
                    x++
                }
                Orient.NW -> {
                    y++
                    x--
                }
            }
        }
        val lastValue = tiles.getOrPut(x to y) { 0 }
        tiles[x to y] = (lastValue + 1) % 2
    }
    tiles.values.count { it == 1 }.also(::println)
}

enum class Orient {
    E,
    SE,
    SW,
    W,
    NW,
    NE
}
