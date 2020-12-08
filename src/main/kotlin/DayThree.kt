import IO.readFile


class Forest(private val terrain: List<List<Char>>) {

    fun numberOfTreesHit(right: Int, down: Int): Long {

        val maxColumn = terrain[0].size
        val maxRow = terrain.size

        var treeCount = 0
        var column = 0
        var row = 0

        while (row < maxRow) {

            if (terrain[row][column] == '#') {

                treeCount ++
            }

            row += down
            column += right
            if (column >= maxColumn) {

                column -= maxColumn
            }
        }

        return treeCount.toLong()
    }
}

object DayThree : Day<Long, Long> {

    override val filename = "/day-three.txt"

    override val partOneResult = forest().numberOfTreesHit(3, 1)

    override val partTwoResult = with(forest()) {

        val one_one = numberOfTreesHit(1, 1)
        val three_one = numberOfTreesHit(3, 1)
        val five_one = numberOfTreesHit(5, 1)
        val seven_one = numberOfTreesHit(7, 1)
        val one_two = numberOfTreesHit(1, 2)

        val result = one_one * three_one * five_one * seven_one * one_two

        println("$one_one * $three_one * $five_one * $seven_one * $one_two = $result")

        result
    }

    private fun forest() = Forest(filename.readFile().map { row ->

        row.indices.map { column -> row[column] }
    })
}