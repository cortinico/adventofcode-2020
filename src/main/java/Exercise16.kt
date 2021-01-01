fun main() {
    val input = object {}.javaClass.getResource("input-16.txt").readText().split("\n\n")

    val ranges =
        input[0].split("\n").flatMap { line ->
            line.substring(line.indexOf(":") + 1).replace(" or", "").trim().split(" ").map { range
                ->
                range.split("-").let { bounds -> bounds[0].toInt()..bounds[1].toInt() }
            }
        }

    input[2]
        .split("\n")
        .drop(1)
        .flatMap { it.split(",") }
        .map { it.toInt() }
        .filter { value -> ranges.all { range -> value !in range } }
        .sum()
        .also(::println)
}
