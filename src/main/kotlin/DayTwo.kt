object DayTwo : Day<Int, Int>("/day-two.txt") {

    override val partOneResult = parse().filter { it.isValidForPartOne }.size

    override val partTwoResult = parse().filter { it.isValidForPartTwo }.size

    private fun parse(): List<Line> = data.map { line ->

        line.split(":").run {

            val min = this[0].split(" ")[0].split("-")[0].toInt()
            val max = this[0].split(" ")[0].split("-")[1].toInt()
            val letter = this[0].split(" ")[1].trim()
            Line(Policy(min, max, letter[0]), this[1].trim())
        }
    }

    data class Policy(val min: Int, val max: Int, val letter: Char)
    class Line(private val policy: Policy, private val password: String) {

        val isValidForPartOne: Boolean
            get() = with (password.count { it == policy.letter }) {
                this >= policy.min && this <= policy.max
            }

        val isValidForPartTwo: Boolean
            get() = (password[policy.min - 1] == policy.letter) xor (password[policy.max - 1] == policy.letter)
    }
}