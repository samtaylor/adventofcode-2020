data class Passport(
    val birthYear: String? = null,
    val issueYear: String? = null,
    val expirationYear: String? = null,
    val height: String? = null,
    val hairColour: String? = null,
    val eyeColour: String? = null,
    val passportId: String? = null,
    val countryId: String? = null)

fun Passport.isValid(): Boolean =
    birthYear != null && issueYear != null && expirationYear != null && height != null &&
            hairColour != null && eyeColour != null && passportId != null

object DayFour : Day<Int, String> {

    private const val BIRTH_YEAR        = "byr"
    private const val ISSUE_YEAR        = "iyr"
    private const val EXPIRATION_YEAR   = "eyr"
    private const val HEIGHT            = "hgt"
    private const val HAIR_COLOUR       = "hcl"
    private const val EYE_COLOUR        = "ecl"
    private const val PASSPORT_ID       = "pid"
    private const val COUNTRY_ID        = "cid"

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

    private val List<Pair<String, String>>.countryId
        get() = firstOrNull { it.first == COUNTRY_ID }?.second

    override fun runPartOne(input: List<String>): Int {

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

    override fun runPartTwo(input: List<String>): String {
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
            tokens.countryId
        )
}