import IO.readFile

object DayOne : Day<Int, Int> {

    override val filename = "/day-one.txt"

    override fun runPartOne(): Int {

        val numbers = filename.readFile().map { it.toInt() }
        numbers.indices.forEach { start ->

            (start + 1 until numbers.size).forEach { index ->

                if (numbers[start] + numbers[index] == 2020) {

                    return numbers[start] * numbers[index]
                }
            }
        }

        return 0
    }

    override fun runPartTwo(): Int {

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