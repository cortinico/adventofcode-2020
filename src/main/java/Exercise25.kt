fun main() {
    val (pub1, pub2) = object {}
        .javaClass
        .getResource("input-25.txt")
        .readText()
        .split("\n")
        .map(String::toLong)

    println(encrypt(pub2, findLoopSize(pub1)))
}

fun findLoopSize(target: Long): Long {
    var value = 1L
    val subject = 7L
    var count = 0L
    while (value != target) {
        value *= subject
        value %= 20201227
        count++
    }
    return count
}

fun encrypt(subject: Long, loopSize: Long): Long {
    var value = 1L
    repeat(loopSize.toInt()) {
        value *= subject
        value %= 20201227
    }
    return value
}
