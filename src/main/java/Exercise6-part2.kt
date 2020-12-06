fun main() {
    val input = object {}.javaClass.getResource("input-6.txt").readText().split("\n\n")

    input.map {
        val votes = it.split('\n')
        var count = 0
        for(chr in 'a'..'z') {
            if (votes.all { vote -> chr in vote}) {
                count++
            }
        }
        count
    }.sum().also(::println)
}


