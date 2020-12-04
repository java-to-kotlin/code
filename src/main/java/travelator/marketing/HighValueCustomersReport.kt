package travelator.marketing

fun Sequence<String>.toHighValueCustomerReport(
    onErrorLine: (String) -> Unit = {}
): Sequence<String> {
    val valuableCustomers = this
        .withoutHeader()
        .map { line ->
            val customerData = line.toCustomerData()
            if (customerData == null)
                onErrorLine(line)
            customerData
        }
        .filterNotNull()
        .filter { it.score >= 10 }
        .sortedBy(CustomerData::score)
        .toList()
    return sequenceOf("ID\tName\tSpend") +
        valuableCustomers.map(CustomerData::outputLine) +
        valuableCustomers.summarised()
}

private fun List<CustomerData>.summarised(): String =
    sumByDouble { it.spend }.let { total ->
        "\tTOTAL\t${total.toMoneyString()}"
    }

private fun Sequence<String>.withoutHeader() = drop(1)

internal fun String.toCustomerData(): CustomerData? =
    split("\t").let { parts ->
        if (parts.size < 4)
            return null
        val score = parts[3].toIntOrNull() ?:
            return null
        val spend = if (parts.size == 4) 0.0 else parts[4].toDoubleOrNull() ?:
            return null
        CustomerData(
            id = parts[0],
            givenName = parts[1],
            familyName = parts[2],
            score = score,
            spend = spend
        )
    }

private val CustomerData.outputLine: String
    get() = "$id\t$marketingName\t${spend.toMoneyString()}"

private fun Double.toMoneyString() = this.formattedAs("%#.2f")

private fun Any?.formattedAs(format: String) = String.format(format, this)

private val CustomerData.marketingName: String
    get() = "${familyName.toUpperCase()}, $givenName"