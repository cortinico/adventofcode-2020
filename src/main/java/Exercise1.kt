fun main() {
    val input = object {}.javaClass.getResource("input-1.txt").readText().split("\n")

    val numberSet = input.toSet().map { it.toInt() }
    numberSet.first { (2020 - it) in numberSet }.let { it * (2020 - it) }.also(::println)
}
