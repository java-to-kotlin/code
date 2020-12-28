package travelator.tablereader

import java.io.StringReader

class Table(
    val headers: List<String>,
    val records: Sequence<Map<String, String>>
) : Sequence<Map<String, String>> by records

fun readTableWithHeader(
    lines: Sequence<String>,
    splitter: (String) -> List<String> = splitOnComma
): Table =
    lines.destruct()?.let { (first, rest) ->
        tableOf(splitter, first, rest)
    } ?: Table(emptyList(), emptySequence())

private fun tableOf(
    splitter: (String) -> List<String>,
    first: String,
    rest: Sequence<String>
): Table {
    val headers = splitter(first)
    val sequence = readTable(
        lines = rest,
        headerProvider = headers::get,
        splitter = splitter
    )
    return Table(headers, sequence)
}

fun readTable(
    lines: Sequence<String>,
    headerProvider: (Int) -> String = Int::toString,
    splitter: (String) -> List<String> = splitOnComma
): Sequence<Map<String, String>> = lines.map {
    parseLine(it, headerProvider, splitter)
}

fun ((String) -> List<String>).readTableWithHeader(
    reader: StringReader
): Sequence<Map<String, String>> =
    readTableWithHeader(reader.buffered().lineSequence(), this)

val splitOnComma: (String) -> List<String> = splitOn(",")
val splitOnTab: (String) -> List<String> = splitOn("\t")

fun splitOn(
    separators: String
) = { line: String ->
    line.splitFields(separators)
}


private fun parseLine(
    line: String,
    headerProvider: (Int) -> String,
    splitter: (String) -> List<String>,
): Map<String, String> {
    val values = splitter(line)
    val keys = values.indices.map(headerProvider)
    return keys.zip(values).toMap()
}

// Necessary because String.split returns a list of an empty string
// when called on an empty string.
private fun String.splitFields(separators: String): List<String> =
    if (isEmpty()) emptyList() else split(separators)

fun <T> Sequence<T>.destruct()
    : Pair<T, Sequence<T>>? {
    val iterator = this.iterator()
    return when {
        iterator.hasNext() ->
            iterator.next() to iterator.asSequence()
        else -> null
    }
}