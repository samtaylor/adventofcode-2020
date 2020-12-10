import IO.readFile

class DayNine(private val preambleSize: Int = 0, private val result: Long = 0) : Day<Long, Long> {

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
        get() = load().run {

            indices.forEach { index ->

                var localIndex = index
                var smallest = get(index)
                var largest = smallest
                var sum = 0L

                do {

                    val value = get(localIndex)
                    smallest = kotlin.math.min(smallest, value)
                    largest = kotlin.math.max(largest, value)
                    println("$sum + $value = ${sum + value}")
                    sum += value
                    localIndex ++
                } while (sum < result)

                if (sum == result) {

                    println(subList(index, localIndex))
                    return@run smallest + largest
                } else {

                    println("No set found")
                }
            }

            -1
        }

    private fun load() = filename.readFile().map { it.trim().toLong() }

    private fun <T> List<T>.preamble(index: Int, count: Int): List<T> = this.subList(index - count, index)
}