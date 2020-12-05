import org.junit.Assert.assertEquals
import org.junit.Test

internal class DayFourTest {

    @Test
    fun partOne() {

        assertEquals(2, DayFour("/day-four-test.txt").runPartOne())
    }
}