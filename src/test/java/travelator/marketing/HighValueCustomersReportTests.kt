package travelator.marketing

import com.natpryce.Success
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

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
            Success(CustomerData("1", "Fred", "Flintstone", 0, 0.0)),
            "1\tFred\tFlintstone\t0".toCustomerData()
        )
    }

    @Test
    fun `calls back on parsing error`() {
        val lines = listOf(
            "ID\tFirstName\tLastName\tScore\tSpend",
            "INVALID LINE",
            "1\tFred\tFlintstone\t11\t1000.00",
        )

        val errorCollector = mutableListOf<ParseFailure>()
        val result = lines
            .asSequence()
            .constrainOnce()
            .toHighValueCustomerReport { badLine ->
                errorCollector += badLine
            }
            .toList()
        assertEquals(
            listOf(NotEnoughFieldsFailure("INVALID LINE")),
            errorCollector
        )

        assertEquals(
            listOf(
                "ID\tName\tSpend",
                "1\tFLINTSTONE, Fred\t1000.00",
                "\tTOTAL\t1000.00"
            ),
            result
        )
    }


    private fun check(
        inputLines: List<String>,
        expectedLines: List<String>
    ) {
        val lines = inputLines.asSequence().constrainOnce()
        assertEquals(
            expectedLines,
            lines.toHighValueCustomerReport().toList()
        )
    }
}