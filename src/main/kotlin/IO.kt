import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception

object IO {

    fun readFile(filename: String): List<String> =
        try {

            mutableListOf<String>().apply {

                val inputStream = javaClass.getResourceAsStream(filename)
                with(BufferedReader(InputStreamReader(inputStream))) {

                    var line = readLine()
                    while (line != null) {

                        add(line)
                        line = readLine()
                    }
                }
            }
        } catch (e: Exception) {

            e.printStackTrace()
            emptyList()
        }
}