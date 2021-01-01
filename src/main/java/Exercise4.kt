fun main() {
    val input = object {}.javaClass.getResource("input-4.txt").readText()

    input
        .splitToSequence("\n\n")
        .filter {
            it.split(' ', '\n')
                .map { item -> item.substring(0, item.indexOf(':')) }
                .containsAll(listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"))
        }
        .count()
        .also(::println)
}
