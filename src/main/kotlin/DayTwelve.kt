import kotlin.RuntimeException

object DayTwelve : Day<Long, Long>("/day-twelve.txt") {

    private val instructions: List<Instruction>
    get() = data.map { line -> Instruction(line[0], line.substring(1).toInt()) }

    override val partOneResult: Long
        get() = Ship()
            .also { ship -> instructions.forEach { instruction ->

                ship.executeInstruction(instruction)
            } }
            .manhattanDistance

    override val partTwoResult: Long
        get() {

            val ship = Ship()
            val waypoint = Waypoint()

            instructions.forEach { instruction ->

                if (instruction.action == 'F') {

                    repeat(instruction.value) {

                        ship.executeInstruction(Instruction('N', waypoint.north.toInt()))
                        ship.executeInstruction(Instruction('S', waypoint.south.toInt()))
                        ship.executeInstruction(Instruction('E', waypoint.east.toInt()))
                        ship.executeInstruction(Instruction('W', waypoint.west.toInt()))
                    }
                } else {

                    waypoint.executeInstruction(instruction)
                }
            }

            return ship.manhattanDistance
        }

    class Waypoint {

        var east = 10L
        var north = 1L
        var west = 0L
        var south = 0L

        fun executeInstruction(instruction: Instruction) {

            when (instruction.action) {

                'N' -> moveNorth(instruction.value)
                'S' -> moveSouth(instruction.value)
                'E' -> moveEast(instruction.value)
                'W' -> moveWest(instruction.value)
                'L' -> turnLeft(instruction.value)
                'R' -> turnRight(instruction.value)
            }

            printState()
        }

        private fun printState() {

            println("waypoint state: n = $north, s = $south, e = $east, w = $west")
        }

        private fun turnLeft(degrees: Int) {

            repeat(degrees / 90) { turnLeft() }
        }

        private fun turnRight(degrees: Int) {

            repeat(degrees / 90) { turnRight() }
        }

        private fun turnLeft() {

            println("waypoint turn left 90")
            val temp = east
            east = south
            south = west
            west = north
            north = temp
        }

        private fun turnRight() {

            println("waypoint turn right 90")
            val temp = east
            east = north
            north = west
            west = south
            south = temp
        }

        private fun moveNorth(distance: Int) {

            println("waypoint move north $distance")
            if (south > 0) {

                if (distance >= south) {

                    north = distance - south
                    south = 0
                } else {

                    south -= distance
                }
            } else {

                north += distance
            }

        }

        private fun moveSouth(distance: Int) {

            println("waypoint move south $distance")
            if (north > 0) {

                if (distance >= north) {

                    south = distance - north
                    north = 0
                } else {

                    north -= distance
                }
            } else {

                south += distance
            }
        }

        private fun moveEast(distance: Int) {

            println("waypoint move east $distance")
            if (west > 0) {

                if (distance >= west) {

                    east = distance - west
                    west = 0
                } else {

                    west -= distance
                }
            } else {

                east += distance
            }
        }

        private fun moveWest(distance: Int) {

            println("waypoint move west $distance")
            if (east > 0) {

                if (distance >= east) {

                    west = distance - east
                    east = 0
                } else {

                    east -= distance
                }
            } else {

                west += distance
            }
        }
    }

    class Ship {
        private var direction: Direction = East
        private var east = 0L
        private var north = 0L
        private var west = 0L
        private var south = 0L

        val manhattanDistance: Long
        get() = north + south + east + west

        fun executeInstruction(instruction: Instruction) {

            when (instruction.action) {

                'N' -> moveNorth(instruction.value)
                'S' -> moveSouth(instruction.value)
                'E' -> moveEast(instruction.value)
                'W' -> moveWest(instruction.value)
                'L' -> turnLeft(instruction.value)
                'R' -> turnRight(instruction.value)
                'F' -> moveForward(instruction.value)
            }

            printState()
        }

        private fun turnLeft(degrees: Int) {

            repeat(degrees / 90) { turnLeft() }
        }

        private fun turnRight(degrees: Int) {

            repeat(degrees / 90) { turnRight() }
        }

        private fun turnLeft() {

            println("ship turn left 90")
            direction = when (direction) {

                West -> South
                South -> East
                East -> North
                North -> West
                else -> throw RuntimeException()
            }
        }

        private fun turnRight() {

            println("ship turn right 90")
            direction = when (direction) {

                West -> North
                North -> East
                East -> South
                South -> West
                else -> throw RuntimeException()
            }
        }

        private fun moveForward(distance: Int) {

            println("ship move forward $distance")
            when (direction) {

                North -> moveNorth(distance)
                South -> moveSouth(distance)
                East -> moveEast(distance)
                West -> moveWest(distance)
            }
        }

        private fun moveNorth(distance: Int) {

            println("ship move north $distance")
            if (south > 0) {

                if (distance >= south) {

                    north = distance - south
                    south = 0
                } else {

                    south -= distance
                }
            } else {

                north += distance
            }

        }

        private fun moveSouth(distance: Int) {

            println("ship move south $distance")
            if (north > 0) {

                if (distance >= north) {

                    south = distance - north
                    north = 0
                } else {

                    north -= distance
                }
            } else {

                south += distance
            }
        }

        private fun moveEast(distance: Int) {

            println("ship move east $distance")
            if (west > 0) {

                if (distance >= west) {

                    east = distance - west
                    west = 0
                } else {

                    west -= distance
                }
            } else {

                east += distance
            }
        }

        private fun moveWest(distance: Int) {

            println("ship move west $distance")
            if (east > 0) {

                if (distance >= east) {

                    west = distance - east
                    east = 0
                } else {

                    east -= distance
                }
            } else {

                west += distance
            }
        }

        private fun printState() {

            println("ship state: d = $direction, n = $north, s = $south, e = $east, w = $west")
        }
    }

    abstract class Direction
    object North : Direction()
    object South : Direction()
    object East : Direction()
    object West : Direction()

    data class Instruction(val action: Char, val value: Int)
}