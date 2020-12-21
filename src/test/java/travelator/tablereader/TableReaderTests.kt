package travelator.tablereader

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TableReaderTests {
    @Test
    fun `empty list returns empty list`() {
        assertEquals(
            emptyList<Map<String, String>>(),
            readTable(emptyList<String>())
        )
    }
}