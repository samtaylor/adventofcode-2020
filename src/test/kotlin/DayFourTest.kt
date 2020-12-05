import junit.framework.Assert.assertEquals
import org.junit.Test

internal class DayFourTest {

    @Test
    fun partOne() {

        assertEquals(2, DayFour.runPartOne(IO.readFile("/four-one-test.txt")))
    }
}