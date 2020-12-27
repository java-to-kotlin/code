package travelator.tablereader

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.StringReader

class CsvExampleTests {

    data class Measurement(
        val t: Double,
        val x: Double,
        val y: Double,
    )

    val fileContents = """
            time,x,y,z
            0.0,  1,  1
            0.1,1.1,1.2
            0.2,1.2,1.4
        """.trimIndent()

    val reader = StringReader(fileContents)

    val expected = listOf(
        Measurement(0.0, 1.0, 1.0),
        Measurement(0.1, 1.1, 1.2),
        Measurement(0.2, 1.2, 1.4)
    )

    @Test
    fun example() {
        reader.useLines { lines ->
            val measurements: Sequence<Measurement> =
                readTableWithHeader(lines, splitOnComma)
                    .map { record ->
                        Measurement(
                            t = record["time"]?.toDoubleOrNull()
                                ?: error("in time"),
                            x = record["x"]?.toDoubleOrNull()
                                ?: error("in x"),
                            y = record["y"]?.toDoubleOrNull()
                                ?: error("in y"),
                        )
                    }
            assertEquals(
                expected,
                measurements.toList()
            )
        }
    }

    @Test
    fun `commons csv`() {
        reader.use { reader ->
            val parser = CSVParser.parse(
                reader,
                CSVFormat.DEFAULT.withFirstRecordAsHeader()
            )
            val measurements: Sequence<Measurement> = parser
                .asSequence()
                .map { record ->
                    Measurement(
                        t = record["time"]?.toDoubleOrNull()
                            ?: error("in time"),
                        x = record["x"]?.toDoubleOrNull()
                            ?: error("in x"),
                        y = record["y"]?.toDoubleOrNull()
                            ?: error("in y"),
                    )
                }
            assertEquals(
                expected,
                measurements.toList()
            )
        }
    }

}