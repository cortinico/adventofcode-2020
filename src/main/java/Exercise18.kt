fun main() {
    object {}
        .javaClass
        .getResource("input-18.txt")
        .readText()
        .split("\n")
        .map { it.replace(" ", "") }
        .sumOf { evaluate(it, 0) }
        .also(::println)
}

fun evaluate(input: String, partial: Long): Long =
    when {
        input.isEmpty() -> partial
        input[0] in '0'..'9' ->
            evaluate(input.drop(1), Character.getNumericValue(input[0]).toLong())
        input[0] == '(' -> {
            val end = findClosingParenthesis(input, 0)
            val parenthesis = evaluate(input.substring(1, end), 0)
            evaluate(input.substring(end + 1), parenthesis)
        }
        input[0] == '+' || input[0] == '*' -> {
            val op: Long.(Long) -> Long = if (input[0] == '+') Long::plus else Long::times

            if (input[1].isDigit()) {
                evaluate(input.drop(2), partial.op(Character.getNumericValue(input[1]).toLong()))
            } else {
                val end = findClosingParenthesis(input, 1)
                val parenthesis = evaluate(input.substring(2, end), 0)
                evaluate(input.substring(end + 1), partial.op(parenthesis))
            }
        }
        else -> partial
    }

fun findClosingParenthesis(input: String, start: Int): Int {
    var count = 1
    var i = start + 1
    while (i < input.length) {
        when (input[i]) {
            '(' -> count++
            ')' -> count--
        }
        if (count == 0) break
        i++
    }
    return i
}
