import org.junit.Test
import kotlin.test.assertEquals

internal class DayTenTest {

    @Test
    fun partOne() {

        assertEquals((7 * 5), DayTen("/day-ten-small.txt").partOneResult)
        assertEquals((22 * 10), DayTen("/day-ten-large.txt").partOneResult)
    }

    @Test
    fun partTwo() {

        assertEquals(4, DayTen("/day-ten-tiny.txt").partTwoResult)
        assertEquals(8, DayTen("/day-ten-small.txt").partTwoResult)
        assertEquals(19208, DayTen("/day-ten-large.txt").partTwoResult)
    }
}
