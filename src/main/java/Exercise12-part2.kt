import kotlin.math.absoluteValue

fun main() {
    val input = object {}.javaClass.getResource("input-12.txt").readText()
        .split("\n")
        .map { it.first() to it.drop(1).toInt() }
    var x = 0
    var y = 0

    var wayX = 10
    var wayY = 1

    input.forEach { (action, value) ->
        when (action) {
            'N' -> wayY += value
            'S' -> wayY -= value
            'E' -> wayX += value
            'W' -> wayX -= value
            'L', 'R' -> {
                val (newX, newY) = rotateWaypoint(wayX, wayY, action, value)
                wayX = newX
                wayY = newY
            }
            'F' -> {
                x += (wayX * value)
                y += (wayY * value)
            }
        }
    }
    println(x.absoluteValue + y.absoluteValue)
}

fun rotateWaypoint(wayX: Int, wayY: Int, action: Char, value: Int): Pair<Int, Int> {
    var degrees = value
    var newWay = wayX to wayY
    while (degrees != 0) {
        newWay = if (action == 'R') {
            newWay.second to -1 * newWay.first
        } else {
            -1 * newWay.second to newWay.first
        }
        degrees -= 90
    }
    return newWay
}