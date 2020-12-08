import IO.readFile

data class Bag(val colour: String, val contents: List<Item> = emptyList()) {

    fun canHold(bags: List<Bag>, colour: String): Boolean {

        if (contents.any { it.colour == colour }) {

            return true
        } else {

            contents.forEach { item ->

                if (bags.find { it.colour == item.colour }?.canHold(bags, colour) == true) {

                    return true
                }
            }
        }

        return false
    }

    fun countBagsWithin(bags: List<Bag>): Int {

        var sum = contents.sumBy { it.count }

        contents.forEach { item ->

            sum += item.count * (bags.find { it.colour == item.colour }?.countBagsWithin(bags) ?: 0)
        }

        return sum
    }
}
data class Item(val count: Int, val colour: String)

object DaySeven : Day<Int, Int> {

    override val filename = "/day-seven.txt"

    override val partOneResult: Int
        get() {

            var count = 0
            val bags = bags()

            bags.forEach { bag ->

                if (bag.canHold(bags, "shiny gold")) {

                    count ++
                }
            }

            return count
        }

    override val partTwoResult: Int
        get() {

            val bags = bags()
            return bags.find { it.colour == "shiny gold" }?.countBagsWithin(bags) ?: 0
        }

    private fun bags(): List<Bag> = filename.readFile().map { line ->

        val colourContents = line.replace("bag.", "")
            .replace("bags.", "")
            .replace("bags", "")
            .replace("bag", "")
            .split(" contain ")

        val colour = colourContents[0].trim()
        val contents: List<Item> = when {

                colourContents[1].contains("no other") -> emptyList()
                else -> colourContents[1].split(", ").map {

                    val count = it.substring(0, it.indexOfFirst { char -> char == ' ' })
                    val bag = it.substring(it.indexOfFirst { char -> char == ' ' }).trim()
                    Item(count.toInt(), bag)
                }
            }
        Bag(colour, contents)
    }
}