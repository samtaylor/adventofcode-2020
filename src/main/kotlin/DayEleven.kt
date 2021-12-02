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

val partTwoRule: (Int, Int, List<String>) -> Int = { x, y, data ->

    var count = 0

    // west
    for (tempX in max(0,x - 1) until 0) {

        when (data[y][tempX]) {

            OCCUPIED_SEAT -> {

                count ++
                break
            }
            EMPTY_SEAT -> {

                break
            }
        }
    }
    // east
    for (tempX in min(data[0].length, (x + 1)) until data[0].length) {

        when (data[y][tempX]) {

            OCCUPIED_SEAT -> {

                count ++
                break
            }
            EMPTY_SEAT -> {

                break
            }
        }
    }
    // north
    for (tempY in max(0, y - 1) until 0) {

        when (data[tempY][x]) {

            OCCUPIED_SEAT -> {

                count ++
                break
            }
            EMPTY_SEAT -> {

                break
            }
        }
    }
    // south
    for (tempY in min(data.size, (y + 1)) until data.size) {

        when (data[tempY][x]) {

            OCCUPIED_SEAT -> {

                count ++
                break
            }
            EMPTY_SEAT -> {

                break
            }
        }
    }
    // north-west
    var tempX = x - 1
    var tempY = y - 1
    while (tempX >= 0 && tempY >= 0) {

        when (data[y][tempX]) {

            OCCUPIED_SEAT -> {

                count ++
                break
            }
            EMPTY_SEAT -> {

                break
            }
            else -> {

                tempX --
                tempY --
            }
        }
    }
    // south-east
    tempX = x + 1
    tempY = y + 1
    while (tempX < data[0].length && tempY < data.size) {

        when (data[y][tempX]) {

            OCCUPIED_SEAT -> {

                count ++
                break
            }
            EMPTY_SEAT -> {

                break
            }
            else -> {

                tempX ++
                tempY ++
            }
        }
    }
    // north-east
    tempX = x + 1
    tempY = y - 1
    while (tempX < data[0].length && tempY >= 0) {

        when (data[y][tempX]) {

            OCCUPIED_SEAT -> {

                count ++
                break
            }
            EMPTY_SEAT -> {

                break
            }
            else -> {

                tempX ++
                tempY --
            }
        }
    }
    // south-west
    tempX = x - 1
    tempY = y + 1
    while (tempX >= 0 && tempY < data.size) {

        when (data[y][tempX]) {

            OCCUPIED_SEAT -> {

                count ++
                break
            }
            EMPTY_SEAT -> {

                break
            }
            else -> {

                tempX --
                tempY ++
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

    override fun toString(): String {

        return StringBuilder().apply {

            seats.forEach {

                append(it)
                append("\n")
            }
        }.toString()
    }
}

object DayEleven : Day<Long, Long> {

    override val filename = "/day-eleven.txt"

    override val partOneResult: Long
        get() = settledWaitingArea(waitingArea(), partOneRule, 4).numberOfOccupiedSeats

    override val partTwoResult: Long
        get() = settledWaitingArea(waitingArea(), partTwoRule, 5).numberOfOccupiedSeats

    private fun waitingArea() = WaitingArea(filename.readFile())

    private fun settledWaitingArea(start: WaitingArea, rule: (Int, Int, List<String>) -> Int, count: Int): WaitingArea {

        lateinit var waitingArea: WaitingArea
        var newWaitingArea = start
        println(newWaitingArea)
        do {

            waitingArea = newWaitingArea
            newWaitingArea = waitingArea.tick(count, rule)
            println(newWaitingArea)
        } while (waitingArea != newWaitingArea)

        return newWaitingArea
    }
}