fun main() {
    val input = object {}.javaClass.getResource("input-9.txt").readText().split("\n").map { it.toLong() }
    val total = 20874512L
    var start = 0
    var end = 1
    var sum = (input[0] + input[1])

    while (true) {
        when {
            sum == total -> break
            sum < total -> sum += input[++end]
            sum > total -> sum -= input[start++]
        }
    }
    input.subList(start, end + 1).apply {
        println(min()!! + max()!!)
    }
}


