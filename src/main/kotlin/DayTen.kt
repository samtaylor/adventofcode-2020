class DayTen(filename: String = "/day-ten.txt") : Day<Long, Long>(filename) {

    override val partOneResult: Long
        get() = load().run {

            var numberOfOneJoltDifferences = 0L
            var numberOfThreeJoltDifferences = 0L
            (0 until size - 1).forEach { index ->

                when (get(index + 1) - get(index)) {

                    1L -> numberOfOneJoltDifferences ++
                    3L -> numberOfThreeJoltDifferences ++
                }
            }

            numberOfOneJoltDifferences * numberOfThreeJoltDifferences
        }

    override val partTwoResult: Long
        get() {

            val trees = mutableListOf<List<Node>>()
            val items = load()
            var start = 0
            (0 until items.size - 1).forEach { index ->

                if (items[index] + 3 == items[index + 1]) {

                    val tree = items.subList(start, index + 1).map { Node(it) }
                    tree[0].connections = tree.filter { it.value > tree[0].value && it.value <= tree[0].value + 3 }
                    populate(tree[0], tree)
                    trees.add(tree)

                    start = index
                }
            }

            var count = count(trees[0][0])

            (1 until trees.size).forEach { index ->

                count *= count(trees[index][0])
            }

            return count
        }

    private fun populate(node: Node, nodes: List<Node>): Unit = node.connections.forEach { childNode ->

        if (childNode.connections.isEmpty()) {

            childNode.connections = nodes.filter { it.value > childNode.value && it.value <= childNode.value + 3 }
            populate(childNode, nodes)
        }
    }

    private fun count(node: Node): Long {

        var counter = 0L

        if (node.connections.isNotEmpty()) {

            node.connections.forEach { child -> counter += count(child) }
        } else {

            counter ++
        }

        return counter
    }

    private fun load() = data.map { it.toLong() }.toMutableList().apply {

        add(0, 0)
        add(this.maxOf { it } + 3)
    }.sorted()

    data class Node(val value: Long, var connections: List<Node> = emptyList())
}