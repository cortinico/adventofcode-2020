fun main() {
    val input: List<CharArray> =
        object {}
            .javaClass
            .getResource("input-11.txt")
            .readText()
            .split("\n")
            .map(String::toCharArray)

    var array1 = Array(input.size) { i -> CharArray(input[i].size) { j -> input[i][j] } }
    var array2 = Array(input.size) { i -> CharArray(input[i].size) { ' ' } }
    var occupiedPlaces: Int

    do {
        var countedChanges = 0
        occupiedPlaces = 0
        for (i in array1.indices) {
            for (j in array1[i].indices) {
                array2[i][j] =
                    when (array1[i][j]) {
                        '.' -> '.'
                        '#' -> {
                            if (countVisibleOccupied(array1, i, j, '#') >= 5) {
                                countedChanges++
                                'L'
                            } else {
                                occupiedPlaces++
                                '#'
                            }
                        }
                        else -> { // L
                            if (countVisibleOccupied(array1, i, j, '#') <= 0) {
                                countedChanges++
                                occupiedPlaces++
                                '#'
                            } else {
                                'L'
                            }
                        }
                    }
            }
        }
        array1 = array2
        array2 = Array(input.size) { i -> CharArray(input[i].size) { ' ' } }
    } while (countedChanges != 0)
    println(occupiedPlaces)
}

fun countVisibleOccupied(array: Array<CharArray>, i: Int, j: Int, target: Char) =
    listOf(
        -1 to 0,
        -1 to -1,
        -1 to +1,
        0 to -1,
        0 to +1,
        +1 to 0,
        +1 to -1,
        +1 to +1,
    )
        .map { (dirX, dirY) -> toVisible(array, i, j, dirX, dirY) }
        .count { it == target }

fun toVisible(array: Array<CharArray>, i: Int, j: Int, dirX: Int, dirY: Int): Char {
    var nexti = i + dirX
    var nextj = j + dirY
    while (nexti >= 0 && nexti < array.size && nextj >= 0 && nextj < array[0].size) {
        array[nexti][nextj].let { if (it != '.') return it }
        nexti += dirX
        nextj += dirY
    }
    return '.'
}
