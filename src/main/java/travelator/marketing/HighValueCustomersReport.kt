package travelator.marketing

import com.natpryce.Failure
import com.natpryce.Result
import com.natpryce.Success
import com.natpryce.recover

fun Sequence<String>.toHighValueCustomerReport(
    onErrorLine: (ParseFailure) -> Unit = {}
): Sequence<String> {
    val valuableCustomers = this
        .withoutHeader()
        .map { line ->
            line.toCustomerData().recover {
                onErrorLine(it)
                null
            }
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

internal fun String.toCustomerData(): Result<CustomerData, ParseFailure> =
    split("\t").let { parts ->
        if (parts.size < 4)
            return Failure(NotEnoughFieldsFailure(this))
        val score = parts[3].toIntOrNull() ?:
            return Failure(ScoreIsNotAnIntFailure(this))
        val spend = if (parts.size == 4) 0.0 else parts[4].toDoubleOrNull() ?:
            return Failure(SpendIsNotADoubleFailure(this))
        Success(
            CustomerData(
                id = parts[0],
                givenName = parts[1],
                familyName = parts[2],
                score = score,
                spend = spend
            )
        )
    }

sealed class ParseFailure(open val line: String)
data class NotEnoughFieldsFailure(override val line: String) :
    ParseFailure(line)
data class ScoreIsNotAnIntFailure(override val line: String) :
    ParseFailure(line)
data class SpendIsNotADoubleFailure(override val line: String) :
    ParseFailure(line)

private val CustomerData.outputLine: String
    get() = "$id\t$marketingName\t${spend.toMoneyString()}"

private fun Double.toMoneyString() = this.formattedAs("%#.2f")

private fun Any?.formattedAs(format: String) = String.format(format, this)

private val CustomerData.marketingName: String
    get() = "${familyName.toUpperCase()}, $givenName"