package travelator.marketing

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.StringReader
import java.io.StringWriter

class HighValueCustomersReportTests {

    @Test
    fun test() {
        check(
            inputLines = listOf(
                "ID\tFirstName\tLastName\tScore\tSpend",
                "1\tFred\tFlintstone\t11\t1000.00",
                "4\tBetty\tRubble\t10\t2000.00",
                "2\tBarney\tRubble\t0\t20.00",
                "3\tWilma\tFlintstone\t9\t0.00"
            ),
            expectedLines = listOf(
                "ID\tName\tSpend",
                "4\tRUBBLE, Betty\t2000.00",
                "1\tFLINTSTONE, Fred\t1000.00",
                "\tTOTAL\t3000.00"
            )
        )
    }


    @Test
    fun emptyTest() {
        check(
            inputLines = listOf(
                "ID\tFirstName\tLastName\tScore\tSpend"
            ),
            expectedLines = listOf(
                "ID\tName\tSpend",
                "\tTOTAL\t0.00"
            )
        )
    }

    @Test
    fun emptySpendIs0() {
        assertEquals(
            CustomerData("1", "Fred", "Flintstone", 0, 0.0),
            "1\tFred\tFlintstone\t0".toCustomerData()
        )
    }

    private fun check(
        inputLines: List<String>,
        expectedLines: List<String>
    ) {
        val output = StringWriter()
        generate(
            StringReader(inputLines.joinToString("\n")),
            output
        )
        assertEquals(expectedLines.joinToString("\n"), output.toString())
    }
}