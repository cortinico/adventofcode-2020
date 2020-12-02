fun main() {
    val input = object {}.javaClass.getResource("input-2.txt").readText().split("\n")

    input.count {
        it.replace("-", " ")
            .replace(":", "")
            .split(" ")
            .let { tokens ->
                val char = tokens[2].toCharArray()[0]
                val string = tokens[3].toCharArray()
                (string[tokens[0].toInt() - 1] == char) xor (string[tokens[1].toInt() - 1] == char)
            }
    }.also(::println)
}