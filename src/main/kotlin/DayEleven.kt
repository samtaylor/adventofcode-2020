import IO.readFile
import kotlin.math.max
import kotlin.math.min

object DayEleven : Day<Int, Int>("/day-eleven.txt") {

    private const val FLOOR = '.'
    private const val EMPTY_CHAIR = 'L'
    private const val OCCUPIED_CHAIR = '#'

    override val partOneResult: Int
        get() {

            var waitingArea = data.map { it.toCharArray().toList() }
            var newWaitingArea = tick(waitingArea, ::adjacentOccupiedSeats, 4)
            while(newWaitingArea != waitingArea) {

                waitingArea = newWaitingArea
                newWaitingArea = tick(waitingArea, ::adjacentOccupiedSeats, 4)
            }

            return waitingArea.flatten().count { it == OCCUPIED_CHAIR }
        }

    override val partTwoResult: Int
        get() {

            var waitingArea = data.map { it.toCharArray().toList() }
            var newWaitingArea = tick(waitingArea, ::adjacentOccupiedSeatsForPartTwo, 5)
            while(newWaitingArea != waitingArea) {

                waitingArea = newWaitingArea
                newWaitingArea = tick(waitingArea, ::adjacentOccupiedSeatsForPartTwo, 5)
            }

            return waitingArea.flatten().count { it == OCCUPIED_CHAIR }
        }

    private fun tick(
        waitingArea: List<List<Char>>,
        adjacentOccupiedSeats: (List<List<Char>>, Int, Int) -> Int,
        maxNumberOfOccupiedSeats: Int,
    ) = MutableList(waitingArea.size) {

        MutableList(waitingArea[0].size) { FLOOR }
    }.also { newWaitingArea ->

        waitingArea.indices.forEach { y ->

            waitingArea[0].indices.forEach { x->

                if (waitingArea[y][x] == EMPTY_CHAIR && adjacentOccupiedSeats(waitingArea, x, y) == 0) {

                    newWaitingArea[y][x] = OCCUPIED_CHAIR
                } else if (waitingArea[y][x] == OCCUPIED_CHAIR && adjacentOccupiedSeats(waitingArea, x, y) >= maxNumberOfOccupiedSeats) {

                    newWaitingArea[y][x] = EMPTY_CHAIR
                } else {

                    newWaitingArea[y][x] = waitingArea[y][x]
                }
            }
        }
    }

    private fun adjacentOccupiedSeats(waitingArea: List<List<Char>>, x: Int, y: Int): Int {

        val startX = max(0, x - 1)
        val startY = max(0, y - 1)
        val endX = min(waitingArea[0].size - 1, x + 1)
        val endY = min(waitingArea.size - 1, y + 1)

        var count = 0

        (startY .. endY).forEach { tempY ->

            (startX .. endX).forEach { tempX ->

                if (!(tempX == x && tempY == y)) {

                    if (waitingArea[tempY][tempX] == OCCUPIED_CHAIR) {

                        count ++
                    }
                }
            }
        }
        return count
    }

    private fun seatFoundForHorizontalAndVertical(
        waitingArea: List<List<Char>>,
        xRange: IntProgression,
        yRange: IntProgression,
    ): Boolean {

        var done = false
        yRange.forEach { y ->

            xRange.forEach { x ->

                if (!done) {

                    if (waitingArea[y][x] != FLOOR) {

                        if (waitingArea[y][x] == OCCUPIED_CHAIR) {

                            return true
                        }
                        done = true
                    }
                }
            }
        }

        return false
    }

    private fun seatFoundForHorizontalAndVertical(waitingArea: List<List<Char>>, x: Int, yRange: IntProgression) =
        seatFoundForHorizontalAndVertical(waitingArea, x .. x, yRange)

    private fun seatFoundForHorizontalAndVertical(waitingArea: List<List<Char>>, xRange: IntProgression, y: Int) =
        seatFoundForHorizontalAndVertical(waitingArea, xRange, y .. y)

    private fun seatFoundForDiagonal(waitingArea: List<List<Char>>, xRange: IntProgression, yRange: IntProgression): Boolean {

        val x = xRange.map { it }
        val y = yRange.map { it }
        var done = false

        x.indices.forEach { index ->

            if (!done) {

                if (waitingArea[y[index]][x[index]] != FLOOR) {

                    if (waitingArea[y[index]][x[index]] == OCCUPIED_CHAIR) {

                        return true
                    }
                    done = true
                }
            }
        }

        return false
    }

    private fun adjacentOccupiedSeatsForPartTwo(waitingArea: List<List<Char>>, x: Int, y: Int): Int {

        var count = 0

        if (seatFoundForHorizontalAndVertical(waitingArea, x, y + 1 until waitingArea.size)) { count ++ }
        if (seatFoundForHorizontalAndVertical(waitingArea, x, (0 until y).reversed())) { count ++ }
        if (seatFoundForHorizontalAndVertical(waitingArea, x + 1 until waitingArea[0].size, y)) { count ++ }
        if (seatFoundForHorizontalAndVertical(waitingArea, (0 until x).reversed(), y)) { count ++ }

        var tempX = x - 1
        var tempY = y - 1
        while (tempX >= 0 && tempY >= 0) {

            if (waitingArea[tempY][tempX] != FLOOR) {

                if (waitingArea[tempY][tempX] == OCCUPIED_CHAIR) {

                    count ++
                }
                tempX = -1
                tempY = -1
            }

            tempX --
            tempY --
        }

        tempX = x + 1
        tempY = y + 1
        while (tempX < waitingArea[0].size && tempY < waitingArea.size) {

            if (waitingArea[tempY][tempX] != FLOOR) {

                if (waitingArea[tempY][tempX] == OCCUPIED_CHAIR) {

                    count ++
                }
                tempX = waitingArea[0].size + 1
                tempY = waitingArea.size + 1
            }

            tempX ++
            tempY ++
        }

        tempX = x + 1
        tempY = y - 1
        while (tempX < waitingArea[0].size && tempY >= 0) {

            if (waitingArea[tempY][tempX] != FLOOR) {

                if (waitingArea[tempY][tempX] == OCCUPIED_CHAIR) {

                    count ++
                }
                tempX = waitingArea[0].size + 1
                tempY = -1
            }

            tempX ++
            tempY --
        }

        tempX = x - 1
        tempY = y + 1
        while (tempX >= 0 && tempY < waitingArea.size) {

            if (waitingArea[tempY][tempX] != FLOOR) {

                if (waitingArea[tempY][tempX] == OCCUPIED_CHAIR) {

                    count ++
                }
                tempX = -1
                tempY = waitingArea.size + 1
            }

            tempX --
            tempY ++
        }

        return count
    }
}