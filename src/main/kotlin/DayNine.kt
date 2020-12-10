import IO.readFile

class DayNine(private val preambleSize: Int) : Day<Long, Long> {

    override val filename = "/day-nine.txt"

    override val partOneResult: Long
        get() = load().run {

            (preambleSize until size).forEach { index ->

                if (DayOne.pairWhichSumTo(preamble(index, preambleSize), get(index)) == null) {

                    return@run get(index)
                }
            }

            -1
        }

    override val partTwoResult: Long
        get() = TODO("Not yet implemented")

    private fun load() = filename.readFile().map { it.trim().toLong() }

    private fun <T> List<T>.preamble(index: Int, count: Int): List<T> = this.subList(index - count, index)
}