package travelator.tablereader

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TableReaderAcceptanceTests {
    data class Measurement(
        val t: Double,
        val x: Double,
        val y: Double,
    )

    @Test
    fun `acceptance test`() {
        val input = listOf(
            "time,x,y",
            "0.0,  1,  1",
            "0.1,1.1,1.2",
            "0.2,1.2,1.4",
        )
        val expected = listOf(
            Measurement(0.0, 1.0, 1.0),
            Measurement(0.1, 1.1, 1.2),
            Measurement(0.2, 1.2, 1.4)
        )
        assertEquals(
            expected,
            someFunction(input)
        )
    }

    private fun someFunction(input: List<String>): List<Measurement> =
        readTable(input) // <1>
            .map { record -> // <2> <3>
            Measurement(
                record["time"].toDouble(), // <4>
                record["x"].toDouble(), // <4>
                record["y"].toDouble(), // <4>
            )
        }

    private fun readTable(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}