interface Day<T, U> {

    val filename: String

    fun runPartOne(): T
    fun runPartTwo(): U
}