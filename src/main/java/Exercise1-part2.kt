fun main() {
    val input = object {}.javaClass.getResource("input-1.txt").readText().split("\n")

    val numberSet = input.map { it.toInt() }.toSet()

    numberSet.forEachIndexed { index1, num1 ->
        numberSet.forEachIndexed { index2, num2 ->
            if (index1 != index2 && 2020 - num1 - num2 in numberSet) {
                println(num1 * num2 * (2020 - num1 - num2))
                return
            }
        }
    }
}
