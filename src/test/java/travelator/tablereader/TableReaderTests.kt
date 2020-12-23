package travelator.tablereader

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TableReaderTests {
    @Test
    fun `empty list returns empty list`() {
        val input: List<String> = emptyList()
        val expectedResult: List<Map<String, String>> = emptyList()
        assertEquals(
            expectedResult,
            readTable(input)
        )
    }
}