fun main() {
    val input =
        object {}.javaClass.getResource("input-8.txt").readText().split("\n").toMutableList()

    fun flip(i: Int) {
        if (input[i].startsWith("jmp")) {
            input[i] = input[i].replace("jmp", "nop")
        } else {
            input[i] = input[i].replace("nop", "jmp")
        }
    }

    for (i in input.indices) {
        if (input[i].startsWith("acc")) {
            continue
        }
        flip(i)

        val visited = mutableSetOf<Int>()
        var acc = 0
        var curr = 0
        var hasLoop = false
        var hasFinished = false
        while (!hasLoop && !hasFinished) {
            val (op, num) = input[curr].split(" ")
            when (op) {
                "nop" -> curr++
                "acc" -> {
                    if (curr + 1 in visited) {
                        hasLoop = true
                    }
                    visited.add(curr)
                    curr++
                    acc += num.toInt()
                }
                "jmp" -> {
                    if (curr + num.toInt() in visited) {
                        hasLoop = true
                    }
                    visited.add(curr)
                    curr += num.toInt()
                }
            }
            if (curr == input.size) {
                hasFinished = true
            }
        }
        if (hasFinished) {
            println(acc)
            break
        }
        flip(i)
    }
}
