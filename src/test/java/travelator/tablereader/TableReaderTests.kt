package travelator.tablereader

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.StringReader

class TableReaderTests {
    @Test
    fun `empty input returns empty`() {
        checkReadTable(
            lines = emptyList(),
            shouldReturn = emptyList()
        )
    }

    @Test
    fun `one line of input with default field names`() {
        checkReadTable(
            lines = listOf("field0,field1"),
            shouldReturn = listOf(
                mapOf("0" to "field0", "1" to "field1")
            )
        )
    }

    @Test
    fun `empty line returns empty map`() {
        checkReadTable(
            lines = listOf(""),
            shouldReturn = listOf(
                emptyMap()
            )
        )
    }

    @Test
    fun `two lines of input with default field names`() {
        checkReadTable(
            lines = listOf(
                "row0field0,row0field1",
                "row1field0,row1field1"
            ),
            shouldReturn = listOf(
                mapOf("0" to "row0field0", "1" to "row0field1"),
                mapOf("0" to "row1field0", "1" to "row1field1")
            )
        )
    }

    @Test
    fun `can specify header names when there is no header row`() {
        val headers = listOf("apple", "banana")
        checkReadTable(
            lines = listOf("field0,field1"),
            withHeaderProvider = headers::get,
            shouldReturn = listOf(
                mapOf(
                    "apple" to "field0",
                    "banana" to "field1",
                )
            )
        )
    }

    @Test
    fun `readTableWithHeader takes headers from header line`() {
        checkReadTableWithHeader(
            lines = listOf(
                "H0,H1",
                "field0,field1"
            ),
            shouldReturn = listOf(
                mapOf("H0" to "field0", "H1" to "field1")
            )
        )
    }

    @Test
    fun `readTableWithHeader empty if lines empty`() {
        checkReadTableWithHeader(
            lines = emptyList(),
            shouldReturn = emptyList(),
        )
    }

    @Test
    fun `readTableWithHeader empty if header line but no body`() {
        checkReadTableWithHeader(
            lines = listOf("H0,H1"),
            shouldReturn = emptyList(),
        )
    }

    @Test
    fun `can specify splitter`() {
        checkReadTableWithHeader(
            lines = listOf(
                "header1\theader2",
                "field0\tfield1"
            ),
            withSplitter = splitOnTab,
            shouldReturn = listOf(
                mapOf(
                    "header1" to "field0",
                    "header2" to "field1",
                )
            ),
        )
    }

    @Test
    fun `read from reader`() {
        val fileContents = """
            H0,H1
            row0field0,row0field1
            row1field0,row1field1
        """.trimIndent()
        StringReader(fileContents).useLines { lines ->
            val result = readTableWithHeader(lines).toList()
            assertEquals(
                listOf(
                    mapOf("H0" to "row0field0", "H1" to "row0field1"),
                    mapOf("H0" to "row1field0", "H1" to "row1field1")
                ),
                result
            )
        }
    }
}

private fun checkReadTable(
    lines: List<String>,
    withHeaderProvider: (Int) -> String = Int::toString,
    shouldReturn: List<Map<String, String>>,
) {
    assertEquals(
        shouldReturn,
        readTable(
            lines.asSequence().constrainOnce(),
            headerProvider = withHeaderProvider,
            splitter = splitOnComma
        ).toList()
    )
}

private fun checkReadTableWithHeader(
    lines: List<String>,
    withSplitter: (String) -> List<String> = splitOnComma,
    shouldReturn: List<Map<String, String>>,
) {
    assertEquals(
        shouldReturn,
        readTableWithHeader(
            lines.asSequence().constrainOnce(),
            splitter = withSplitter
        ).toList()
    )
}