import org.junit.Test
import kotlin.test.assertEquals

internal class DayOneTest {

    @Test
    fun partOne() = assertEquals(514579, DayOne.runPartOne())

    @Test
    fun partTwo() = assertEquals(241861950, DayOne.runPartTwo())
}