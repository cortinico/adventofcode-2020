fun main() {
    val input = object {}.javaClass.getResource("input-9.txt").readText().split("\n").map { it.toLong() }
    input.windowed(26) {
        val toCheck = it.last()
        for (i in 0 until it.size - 1) {
            for (j in i + 1 until (it.size - 1)) {
                if (it[i] + it[j] == toCheck) return@windowed
            }
        }
        println(toCheck)
    }
}


