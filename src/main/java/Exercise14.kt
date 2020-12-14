fun main() {
    val input = object {}.javaClass.getResource("input-14.txt").readText()
        .split("\n")
        .map { line ->
            if (line.startsWith("mask")) {
                Mask(line.split(" ").last())
            } else {
                line.replace(" = ", "").split('[', ']').let {
                    Mem(it[1].toLong(), it[2].toLong())
                }
            }
        }

    val memory = LongArray(65364) { 0 }
    var mask = "".toCharArray()

    input.forEach { op ->
        when (op) {
            is Mask -> mask = op.mask.toCharArray()
            is Mem -> memory[op.address.toInt()] = applyMask(op.value, mask)
        }
    }
    println(memory.sum())
}

fun applyMask(value: Long, mask: CharArray): Long {
    var result = 0L
    for (i in mask.indices) {
        result = when(mask[mask.size - i - 1]) {
            '1' -> result or (1L shl i)
            '0' -> result or (0L shl i)
            else -> result or (value and (1L shl i))
        }
    }
    return result
}

sealed class Op
data class Mask(val mask: String) : Op()
data class Mem(val address: Long, val value: Long) : Op()