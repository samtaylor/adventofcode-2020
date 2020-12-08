import IO.readFile

data class Person(val questions: String)
class Group(private val people: List<Person>) {

    val numberOfYesQuestionsForPartOne: Int
    get() {

        var value = 0
        var alreadyCounted = ""

        ('a'..'z').forEach { question ->

            people.forEach { person ->

                if (person.questions.contains(question) && !alreadyCounted.contains(question)) {

                    value ++
                    alreadyCounted += question
                }
            }
        }
        return value
    }

    val numberOfQuestionsForPartTwo: Int
    get() {

        var value = 0

        ('a'..'z').forEach { question ->

            if (people.filter { person -> person.questions.contains(question) }.size == people.size) {

                value ++
            }
        }

        return value
    }
}

object DaySix : Day<Int, Int> {

    override val filename = "/day-six.txt"

    override val partOneResult = groups().sumBy { it.numberOfYesQuestionsForPartOne }
    override val partTwoResult = groups().sumBy { it.numberOfQuestionsForPartTwo }

    private fun groups() = mutableListOf<Group>().apply {

        var people = mutableListOf<Person>()
        filename.readFile().forEach { line ->

            if (line.isNotBlank()) {

                people.add(Person(line))
            } else {

                add(Group(people))
                people = mutableListOf()
            }
        }

        add(Group(people))
    }
}