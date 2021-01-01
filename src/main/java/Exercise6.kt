fun main() {
    val input = object {}.javaClass.getResource("input-6.txt").readText().split("\n\n")

    input.map { it.toCharArray().filter(Char::isLetter).distinct().count() }.sum().also(::println)
}
