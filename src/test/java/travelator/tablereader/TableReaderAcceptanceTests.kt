package travelator.tablereader

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class TableReaderAcceptanceTests {
    data class Measurement(
        val t: Double,
        val x: Double,
        val y: Double,
    )

    @Disabled
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
            readTable(input).map { record ->
                Measurement(
                    t = record["time"]?.toDoubleOrNull() ?: error("in time"),
                    x = record["x"]?.toDoubleOrNull() ?: error("in x"),
                    y = record["y"]?.toDoubleOrNull() ?: error("in y"),
                )
            }
        )
    }
}