import IO.readFile

object DayOne : Day<Int, Int> {

    override val filename = "/day-one.txt"

    override val partOneResult: Int
    get() = pairWhichSumTo(filename.readFile().map { it.toLong() }, 2020)?.run { first * second }?.toInt() ?: 0

    fun pairWhichSumTo(numbers: List<Long>, sum: Long): Pair<Long, Long>? {

        print("numbers = $numbers, sum = $sum: ")
        numbers.indices.forEach { start ->

            (start + 1 until numbers.size).forEach { index ->

                if (numbers[start] + numbers[index] == sum) {

                    println("output = ${numbers[start]}, ${numbers[index]}")
                    return Pair(numbers[start], numbers[index])
                }
            }
        }

        println("no pair found.")
        return null
    }

    override val partTwoResult: Int
    get() {

        val numbers = filename.readFile().map { it.toInt() }
        numbers.indices.forEach { start ->

            (start + 1 until numbers.size).forEach { index1 ->

                (index1 + 1 until numbers.size).forEach { index2 ->

                    if (numbers[start] + numbers[index1] + numbers[index2] == 2020) {

                        return numbers[start] * numbers[index1] * numbers[index2]
                    }
                }
            }
        }

        return 0
    }
}