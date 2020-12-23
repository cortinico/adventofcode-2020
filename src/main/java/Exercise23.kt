fun main() {
    val input = object {}.javaClass.getResource("input-23.txt").readText()
        .toCharArray()
        .map(Character::getNumericValue)

    val size = input.size
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
            cups[elem].next = cups[input.first()]
        }
    }

    var current = cups[input.first()]
    repeat(100) {
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
    var t = cups[1]
    (1 until size).fold("") { acc, _ ->
        t = t.next
        "$acc${t.num}"
    }.also(::println)
}

class Cup(val num: Int) {
    lateinit var next: Cup
}