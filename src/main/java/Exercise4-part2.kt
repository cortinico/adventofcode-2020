fun main() {
    val input = object {}.javaClass.getResource("input-4.txt").readText()

    val eyeColors = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    val keys = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

    input.splitToSequence("\n\n")
        .filter { it ->
            val count = it.splitToSequence(' ', '\n')
                .map { item -> item.split(":").let { (it[0] to it[1]) } }
                .filter { (key, _) -> key in keys }
                .filter { (key, value) -> key != "byr" || value.toIntOrNull() in 1920..2002 }
                .filter { (key, value) -> key != "iyr" || value.toIntOrNull() in 2010..2020 }
                .filter { (key, value) -> key != "eyr" || value.toIntOrNull() in 2020..2030 }
                .filter { (key, value) ->
                    if (key != "hgt") {
                        true
                    } else {
                        val unit = value.substring(value.length - 2)
                        val size = value.substring(0, value.length - 2).toIntOrNull()
                        ((unit == "cm" && size in 150..193) || (unit == "in" && size in 59..76))
                    }
                }
                .filter { (key, value) -> key != "hcl" || value.matches(Regex("#[0-9a-z]{6}")) }
                .filter { (key, value) -> key != "ecl" || value in eyeColors }
                .filter { (key, value) -> key != "pid" || value.matches(Regex("[0-9]{9}")) }
                .count()
            count == 7 || count == 8
        }
        .count()
        .also(::println)
}