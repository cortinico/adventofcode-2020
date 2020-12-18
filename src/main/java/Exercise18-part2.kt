fun main() {
    object {}.javaClass.getResource("input-18.txt").readText()
        .split("\n")
        .map { it.replace(" ", "") }
        .sumOf { evaluateImproved(it) }
        .also(::println)
}

fun evaluateImproved(expression: String): Long {
    var input = expression
    while ('(' in input) {
        val start = input.indexOf('(')
        val end = findClosingParenthesis(input, start)

        val before = if (start == 0) "" else input.substring(0, start)
        val center = evaluateImproved(input.substring(start + 1, end))
        val after = if (end == input.length - 1) "" else input.substring(end + 1)
        input = "$before$center$after"
    }
    while ('+' in input) input = resolveOperatorByIndex(input, input.indexOf('+'))
    while ('*' in input) input = resolveOperatorByIndex(input, input.indexOf('*'))
    return input.toLong()
}

fun resolveOperatorByIndex(input: String, idx: Int): String {
    var start = idx - 1
    var end = idx + 1
    while (start > 0 && input[start].isDigit()) start--
    while (end < input.length - 1 && input[end].isDigit()) end++

    val numBefore: Long = if (start == 0) {
        input.substring(0, idx)
    } else {
        input.substring(start + 1, idx)
    }.toLong()

    val numAfter: Long = if (end == input.length - 1) {
        input.substring(idx + 1)
    } else {
        input.substring(idx + 1, end)
    }.toLong()

    val before = if (start == 0) "" else input.substring(0, start + 1)
    val center = if (input[idx] == '+') numBefore + numAfter else numBefore * numAfter
    val after = if (end == input.length - 1) "" else input.substring(end)
    return "$before$center$after"
}


