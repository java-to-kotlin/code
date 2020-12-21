package travelator.tablereader

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TableReaderTests {
    @Test
    fun `empty list returns empty list`() {
        assertEquals(
            emptyList<Map<String, String>>(),
            readTable(emptyList())
        )
    }

    @Test
    fun `one line of input with default field names`() {
        assertEquals(
            listOf(
                mapOf("0" to "field0", "1" to "field1")
            ),
            readTable(listOf(
                "field0,field1"
            ))
        )
    }
}