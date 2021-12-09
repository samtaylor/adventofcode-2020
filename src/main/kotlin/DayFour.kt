object DayFour : Day<Int, Int>("/day-four.txt") {

    private const val BIRTH_YEAR = "byr"
    private const val ISSUE_YEAR = "iyr"
    private const val EXPIRATION_YEAR = "eyr"
    private const val HEIGHT = "hgt"
    private const val HAIR_COLOUR = "hcl"
    private const val EYE_COLOUR = "ecl"
    private const val PASSPORT_ID = "pid"

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

    override val partOneResult = passports().filter { it.isValidForPartOne }.size

    override val partTwoResult = passports().filter { it.isValidForPartTwo }.size

    private fun passports() = mutableListOf<Passport>().apply {
        val tokens = mutableListOf<Pair<String, String>>()
        data.forEach { line ->

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

    class Passport(
        private val birthYear: String? = null,
        private val issueYear: String? = null,
        private val expirationYear: String? = null,
        private val height: String? = null,
        private val hairColour: String? = null,
        private val eyeColour: String? = null,
        private val passportId: String? = null,
    ) {

        val isValidForPartOne: Boolean
            get() = birthYear != null && issueYear != null && expirationYear != null && height != null &&
                    hairColour != null && eyeColour != null && passportId != null

        val isValidForPartTwo: Boolean
            get() = validBirthYear && validIssueYear &&
                    validExpirationYear && validHeight &&
                    validHairColour && validEyeColour &&
                    validPassportID

        private fun validateYear(year: String?, from: Int, to: Int) =
            year?.let { it.length == 4 && (with (it.toInt()) { this in from..to }) } ?: false

        private fun matchRegex(string: String?, regex: String) =
            string?.let { regex.toRegex().matches(it) } ?: false

        private val validBirthYear
            get() = validateYear(birthYear, 1920, 2002)

        private val validIssueYear
            get() = validateYear(issueYear, 2010, 2020)

        private val validExpirationYear
            get() = validateYear(expirationYear, 2020, 2030)

        private val validHeight
            get() = height?.let {

                when {

                    height.contains(CENTIMETRES) -> {

                        val heightVal = height.substring(0, height.indexOf(CENTIMETRES)).toInt()
                        heightVal in 150..193
                    }

                    height.contains(INCHES) -> {

                        val heightVal = height.substring(0, height.indexOf(INCHES)).toInt()
                        heightVal in 59..76
                    }

                    else -> false
                }
            } ?: false

        private val validHairColour
            get() = matchRegex(hairColour, "#[0-9a-f]{6}")

        private val validEyeColour
            get() = eyeColour?.let {

                listOf(AMBER, BLUE, BROWN, GREY, GREEN, HAZEL, OTHER).contains(it)
            } ?: false

        private val validPassportID
            get() = matchRegex(passportId, "[0-9]{9}")

        companion object {

            private const val CENTIMETRES = "cm"
            private const val INCHES = "in"

            private const val AMBER = "amb"
            private const val BLUE = "blu"
            private const val BROWN = "brn"
            private const val GREY = "gry"
            private const val GREEN = "grn"
            private const val HAZEL = "hzl"
            private const val OTHER = "oth"
        }
    }
}