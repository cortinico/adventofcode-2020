fun main() {
    val input = object {}.javaClass.getResource("input-19.txt").readText().split("\n\n")

    val rules = input[0].split("\n").map { it.split(": ") }.associateBy({ it[0] }, { it[1] })

    input[1].split("\n").count { matches(it.toCharArray(), 0, rules, listOf("0")) }.also(::println)
}

fun matches(
    input: CharArray,
    idx: Int,
    map: Map<String, String>,
    remainingRules: List<String>
): Boolean {
    return when {
        idx >= input.size && remainingRules.isEmpty() -> true
        idx >= input.size && remainingRules.isNotEmpty() -> false
        remainingRules.isEmpty() -> false
        else -> {
            val nextRule = map[remainingRules.first()]!!
            if (nextRule[0] == '"') {
                if (nextRule[1] == input[idx]) {
                    matches(input, idx + 1, map, remainingRules.drop(1))
                } else {
                    false
                }
            } else {
                nextRule.split(" | ").any {
                    val ruleSequence = it.split(" ") + remainingRules.drop(1)
                    matches(input, idx, map, ruleSequence)
                }
            }
        }
    }
}
