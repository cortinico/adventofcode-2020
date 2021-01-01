fun main() {
    val input = object {}.javaClass.getResource("input-2.txt").readText().split("\n")

    input
        .count {
            it.replace("-", " ").replace(":", "").split(" ").let { tokens ->
                tokens[3].count { char -> char == tokens[2].toCharArray()[0] } in
                    tokens[0].toInt()..tokens[1].toInt()
            }
        }
        .also(::println)
}
