fun main() {
    val input = object {}.javaClass.getResource("input-8.txt").readText().split("\n")
    val visited = mutableSetOf<Int>()
    var acc = 0
    var curr = 0

    while (true) {
        val (op, num) = input[curr].split(" ")
        when (op) {
            "nop" -> { curr++ }
            "acc" -> {
                if (curr + 1 in visited) {
                    break
                }
                visited.add(curr)
                curr++
                acc += num.toInt()
            }
            "jmp" -> {
                if (curr + num.toInt() in visited) {
                    break
                }
                visited.add(curr)
                curr+= num.toInt()
            }
        }
    }
    println(acc)
}


