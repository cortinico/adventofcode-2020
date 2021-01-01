fun main() {
    val input = object {}.javaClass.getResource("input-19.txt").readText().split("\n\n")

    val rules =
        input[0].split("\n").map { it.split(": ") }.associateBy({ it[0] }, { it[1] }).toMutableMap()

    rules["8"] = "42 | 42 8"
    rules["11"] = "42 31 | 42 11 31"

    input[1].split("\n").count { matches(it.toCharArray(), 0, rules, listOf("0")) }.also(::println)
}
