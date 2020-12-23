fun main() {
    val input = object {}.javaClass.getResource("input-23.txt").readText()
        .toCharArray()
        .map(Character::getNumericValue)

    val size = 1_000_000
    val cups = Array(size + 1) { Cup(it) }

    cups.onEachIndexed { index, cup ->
        if (index == cups.size - 1) {
            cup.next = cups[1]
        } else {
            cup.next = cups[index + 1]
        }
    }

    input.forEachIndexed { index, elem ->
        if (index != input.size - 1) {
            cups[elem].next = cups[input[index + 1]]
        } else {
            cups[elem].next = cups[input.size + 1]
        }
    }

    var current = cups[input.first()]
    cups.last().next = current

    repeat(10_000_000) {
        val curr1 = current.next
        val curr2 = curr1.next
        val curr3 = curr2.next
        val currAfter = curr3.next

        var toFind = current.num - 1

        while (toFind == curr1.num || toFind == curr2.num || toFind == curr3.num) toFind--
        if (toFind == 0) {
            toFind = size
            while (toFind == curr1.num || toFind == curr2.num || toFind == curr3.num) toFind--
        }
        val dest0 = cups[toFind]
        val dest1 = dest0.next
        dest0.next = curr1
        curr3.next = dest1
        current.next = currAfter
        current = currAfter
    }
    println((cups[1].next.num).toLong() * (cups[1].next.next.num).toLong())
}