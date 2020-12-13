import IO.readFile
import kotlin.math.*
import kotlin.text.StringBuilder

const val FLOOR = '.'
const val EMPTY_SEAT = 'L'
const val OCCUPIED_SEAT = '#'

val partOneRule: (Int, Int, List<String>) -> Int = { x, y, data ->

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

    count
}

data class WaitingArea(private var seats: List<String>) {

    val numberOfOccupiedSeats: Long
    get() = seats.sumBy { it.count { seat -> seat == '#' } }.toLong()

    fun tick(count: Int, rule: (Int, Int, List<String>) -> Int) = WaitingArea(mutableListOf<String>().apply {

        seats.indices.forEach { y ->

            val row = StringBuilder()
            seats[y].indices.forEach { x ->

                row.append(applyRule(x, y, seats, rule, count))
            }
            add(row.toString())
        }
    })

    private fun applyRule(x: Int,
                          y: Int,
                          data: List<String>,
                          occupiedSeats: (Int, Int, List<String>) -> Int,
                          count: Int) = when (data[y][x]) {

        EMPTY_SEAT -> if (occupiedSeats(x, y, data) == 0) {

            OCCUPIED_SEAT
        } else {

            EMPTY_SEAT
        }

        OCCUPIED_SEAT -> if (occupiedSeats(x, y, data) >= count) {

            EMPTY_SEAT
        } else {

            OCCUPIED_SEAT
        }

        else -> FLOOR
    }
}

object DayEleven : Day<Long, Long> {

    override val filename = "/day-eleven.txt"

    override val partOneResult: Long
        get() = settledWaitingArea(waitingArea(), partOneRule, 4).numberOfOccupiedSeats

    override val partTwoResult: Long
        get() = TODO("Not yet implemented")

    private fun waitingArea() = WaitingArea(filename.readFile())

    private fun settledWaitingArea(start: WaitingArea, rule: (Int, Int, List<String>) -> Int, count: Int): WaitingArea {

        lateinit var waitingArea: WaitingArea
        var newWaitingArea = start
        do {

            waitingArea = newWaitingArea
            newWaitingArea = waitingArea.tick(count, rule)
        } while (waitingArea != newWaitingArea)

        return newWaitingArea
    }
}