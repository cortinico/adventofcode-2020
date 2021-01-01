fun main() {
    val input =
        object {}.javaClass.getResource("input-14.txt").readText().split("\n").map { line ->
            if (line.startsWith("mask")) {
                Mask(line.split(" ").last())
            } else {
                line.replace(" = ", "").split('[', ']').let { Mem(it[1].toLong(), it[2].toLong()) }
            }
        }
    val memory = mutableMapOf<Long, Long>()
    var mask = "".toCharArray()
    input.forEach { op ->
        when (op) {
            is Mask -> mask = op.mask.toCharArray()
            is Mem -> getAddresses(op.address, mask).forEach { memory[it] = op.value }
        }
    }
    println(memory.values.sum())
}

fun getAddresses(address: Long, mask: CharArray): List<Long> {
    val results = mutableListOf(0L)
    val floatings = mutableListOf<Int>()

    for (i in mask.indices) {
        when (mask[mask.size - i - 1]) {
            '1' -> results[0] = results[0] or (1L shl i)
            '0' -> results[0] = results[0] or (address and (1L shl i))
            else -> floatings.add(i)
        }
    }
    floatings.forEach { bit -> results.map { it or (1L shl bit) }.apply { results.addAll(this) } }
    return results
}
