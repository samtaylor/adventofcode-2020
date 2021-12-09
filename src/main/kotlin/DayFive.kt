object DayFive : Day<Int, Int>("/day-five.txt") {

    override val partOneResult = boardingPasses().maxOf { it.seatId }
    override val partTwoResult: Int
        get() {

            with (boardingPasses()) {

                (minOf { it.seatId } + 1 until maxOf { it.seatId }).forEach { potentialSeatId ->

                    if (none { it.seatId == potentialSeatId }) {

                        return potentialSeatId
                    }
                }
            }

            return 0
        }

    private fun boardingPasses() = data.map { BoardingPass(it) }

    class BoardingPass(private val seat: String) {

        private val row: Int
            get() = binarySearch(127, 0..6, 'F')

        private val column: Int
            get() = binarySearch(7, 7..9, 'L')

        val seatId: Int
            get() = (row * 8) + column

        private fun binarySearch(end: Int, range: IntRange, upperChar: Char): Int {

            var lower = 0
            var upper = end

            range.forEach { index ->

                when (seat[index]) {

                    upperChar   -> upper -= ((upper - lower) / 2) + 1
                    else        -> lower += ((upper - lower) / 2) + 1
                }
            }

            return lower
        }
    }
}