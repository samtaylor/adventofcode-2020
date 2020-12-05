import IO.readFile

class Passport(
    private val birthYear: String? = null,
    private val issueYear: String? = null,
    private val expirationYear: String? = null,
    private val height: String? = null,
    private val hairColour: String? = null,
    private val eyeColour: String? = null,
    private val passportId: String? = null,
) {

    fun isValid(): Boolean =
        birthYear != null && issueYear != null && expirationYear != null && height != null &&
                hairColour != null && eyeColour != null && passportId != null
}

class DayFour(override val filename: String) : Day<Int, String> {

    companion object {

        private const val BIRTH_YEAR = "byr"
        private const val ISSUE_YEAR = "iyr"
        private const val EXPIRATION_YEAR = "eyr"
        private const val HEIGHT = "hgt"
        private const val HAIR_COLOUR = "hcl"
        private const val EYE_COLOUR = "ecl"
        private const val PASSPORT_ID = "pid"
    }

    private val List<Pair<String, String>>.birthYear
        get() = firstOrNull { it.first == BIRTH_YEAR }?.second

    private val List<Pair<String, String>>.issueYear
        get() = firstOrNull { it.first == ISSUE_YEAR }?.second

    private val List<Pair<String, String>>.expirationYear
        get() = firstOrNull { it.first == EXPIRATION_YEAR }?.second

    private val List<Pair<String, String>>.height
        get() = firstOrNull { it.first == HEIGHT }?.second

    private val List<Pair<String, String>>.hairColour
        get() = firstOrNull { it.first == HAIR_COLOUR }?.second

    private val List<Pair<String, String>>.eyeColour
        get() = firstOrNull { it.first == EYE_COLOUR }?.second

    private val List<Pair<String, String>>.passportId
        get() = firstOrNull { it.first == PASSPORT_ID }?.second

    override fun runPartOne(): Int {

        val input = filename.readFile()
        return mutableListOf<Passport>().apply {

            val tokens = mutableListOf<Pair<String, String>>()
            input.forEach { line ->

                if (line.isNotBlank()) {

                    tokens.addAll(
                        line
                            .split(" ")
                            .map { with(it.split(":")) { Pair(first(), last()) } }
                    )
                } else {

                    add(passportFromTokens(tokens))
                    tokens.clear()
                }
            }

            add(passportFromTokens(tokens))
        }.filter { it.isValid() }.size
    }

    override fun runPartTwo(): String {
        TODO("Not yet implemented")
    }

    private fun passportFromTokens(tokens: List<Pair<String, String>>) =
        Passport(
            tokens.birthYear,
            tokens.issueYear,
            tokens.expirationYear,
            tokens.height,
            tokens.hairColour,
            tokens.eyeColour,
            tokens.passportId,
        )
}