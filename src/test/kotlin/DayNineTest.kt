import org.junit.Test
import kotlin.test.assertEquals

internal class DayNineTest {

    @Test
    fun partOne() = assertEquals(127, DayNine(preambleSize = 5).partOneResult)

    @Test
    fun partTwo() = assertEquals(62, DayNine(result = 127).partTwoResult)
}