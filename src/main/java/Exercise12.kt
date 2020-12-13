import kotlin.math.absoluteValue

fun main() {
    val input = object {}.javaClass.getResource("input-12.txt").readText()
        .split("\n")
        .map { it.first() to it.drop(1).toInt() }
    var dir = Direction.E
    var x = 0
    var y = 0
    input.forEach { (action, value) ->
        when (action) {
            'N' -> y += value
            'S' -> y -= value
            'E' -> x += value
            'W' -> x -= value
            'L', 'R' -> dir = rotate(dir, action, value)
            'F' -> when (dir) {
                Direction.N -> y += value
                Direction.S -> y -= value
                Direction.E -> x += value
                Direction.W -> x -= value
            }
        }
    }
    println(x.absoluteValue + y.absoluteValue)
}

enum class Direction {
    N, E, S, W
}

fun rotate(dir: Direction, action: Char, value: Int): Direction {
    var degrees = value
    var newDir = dir
    while (degrees != 0) {
        val idx = Direction.values().indexOf(newDir)
        newDir = if (action == 'R') {
            Direction.values()[(idx + 1) % 4]
        } else {
            Direction.values()[if (idx - 1 == -1) 3 else idx - 1]
        }
        degrees -= 90
    }
    return newDir
}