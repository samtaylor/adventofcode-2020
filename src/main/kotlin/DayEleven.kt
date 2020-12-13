import IO.readFile
import kotlin.math.*
import kotlin.text.StringBuilder

data class WaitingArea(private var seats: List<String>) {

    companion object {

        private const val FLOOR = '.'
        private const val EMPTY_SEAT = 'L'
        private const val OCCUPIED_SEAT = '#'
    }

    val numberOfOccupiedSeats: Long
    get() = seats.sumBy { it.count { seat -> seat == '#' } }.toLong()

    fun tick() = WaitingArea(mutableListOf<String>().apply {

        seats.indices.forEach { y ->

            val row = StringBuilder()
            seats[y].indices.forEach { x ->

                row.append(applyRule(x, y, seats))
            }
            add(row.toString())
        }
    })

    private fun applyRule(x: Int, y: Int, data: List<String>) = when (data[y][x]) {

        EMPTY_SEAT -> if (occupiedAdjacentSeats(x, y, data) == 0) {

            OCCUPIED_SEAT
        } else {

            EMPTY_SEAT
        }

        OCCUPIED_SEAT -> if (occupiedAdjacentSeats(x, y, data) >= 4) {

            EMPTY_SEAT
        } else {

            OCCUPIED_SEAT
        }

        else -> FLOOR
    }

    private fun occupiedAdjacentSeats(x: Int, y: Int, data: List<String>): Int {

        var count = 0
        val startX = max(0, x - 1)
        val endX = min(x + 1, data[0].length - 1)
        val startY = max(0, y - 1)
        val endY = min(y + 1, data.size - 1)

        (startY .. endY).forEach { adjacentY ->

            (startX .. endX).forEach { adjacentX ->

                if (adjacentX != x || adjacentY != y) {

                    if (data[adjacentY][adjacentX] == OCCUPIED_SEAT) {

                        count++
                    }
                }
            }
        }

        return count
    }
}

object DayEleven : Day<Long, Long> {

    override val filename = "/day-eleven.txt"

    override val partOneResult: Long
        get() {

            lateinit var waitingArea: WaitingArea
            var newWaitingArea = waitingArea()
            do {

                waitingArea = newWaitingArea
                newWaitingArea = waitingArea.tick()
            } while (waitingArea != newWaitingArea)

            return newWaitingArea.numberOfOccupiedSeats
        }
    override val partTwoResult: Long
        get() = TODO("Not yet implemented")

    private fun waitingArea() = WaitingArea(filename.readFile())
}