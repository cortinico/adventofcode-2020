import kotlin.math.absoluteValue

fun main() {
    val input = object {}.javaClass.getResource("input-13.txt").readText().split("\n")

    val busses = input[1].split(",")
    var indexes =
        busses
            .mapIndexed { index, code -> index to code }
            .filter { (_, code) -> code != "x" }
            .map { (index, code) -> index to code.toLong() }

    val last = indexes.last()
    indexes = indexes.map { (value, code) -> (value - last.first).absoluteValue to code }

    val product = indexes.map { it.second }.reduce { acc, next -> acc * next }
    val partials = indexes.map { it.second }.map { product / it }

    val inverses =
        indexes.map { it.second }.mapIndexed { i, value -> modInverse(partials[i], value) }

    val sum = inverses.mapIndexed { i, inverse -> partials[i] * inverse * indexes[i].first }.sum()

    println(sum % product - (busses.size - 1))
}

// Find the modular multiplicative inverse between number and m.
fun modInverse(number: Long, m: Long): Long {
    val x = number % m
    for (i in 1 until m) {
        if ((x * i) % m == 1L) return i
    }
    return 1
}
