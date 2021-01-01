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
                            if (countOccupied(array1, i, j, '#') >= 4) {
                                countedChanges++
                                'L'
                            } else {
                                occupiedPlaces++
                                '#'
                            }
                        }
                        // L
                        else -> {
                            if (countOccupied(array1, i, j, '#') <= 0) {
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

fun countOccupied(array: Array<CharArray>, i: Int, j: Int, target: Char) =
    listOf(
        i - 1 to j,
        i - 1 to j - 1,
        i - 1 to j + 1,
        i to j - 1,
        i to j + 1,
        i + 1 to j,
        i + 1 to j - 1,
        i + 1 to j + 1,
    )
        .asSequence()
        .filter { it.first >= 0 }
        .filter { it.first < array.size }
        .filter { it.second >= 0 }
        .filter { it.second < array[0].size }
        .count { array[it.first][it.second] == target }
