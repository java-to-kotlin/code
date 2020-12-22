package travelator.tablereader

fun readTableWithHeader(
    lines: List<String>,
    splitter: (String) -> List<String> = splitOnComma
): List<Map<String, String>> =
    readTableWithHeader(
        lines.asSequence(),
        splitter
    ).toList()

fun readTableWithHeader(
    lines: Sequence<String>,
    splitter: (String) -> List<String> = splitOnComma
) = when {
    lines.firstOrNull() == null -> emptySequence()
    else -> {
        readTable(
            lines.drop(1),
            headerProviderFrom(lines.first(), splitter),
            splitter
        )
    }
}

fun readTable(
    lines: List<String>,
    headerProvider: (Int) -> String = Int::toString,
    splitter: (String) -> List<String> = splitOnComma
): List<Map<String, String>> =
    readTable(
        lines.asSequence(),
        headerProvider,
        splitter
    ).toList()

fun readTable(
    lines: Sequence<String>,
    headerProvider: (Int) -> String = Int::toString,
    splitter: (String) -> List<String> = splitOnComma
) = lines.map {
    parseLine(it, headerProvider, splitter)
}

val splitOnComma: (String) -> List<String> = splitOn(",")
val splitOnTab: (String) -> List<String> = splitOn("\t")

fun splitOn(
    separators: String
) = { line: String ->
    line.splitFields(separators)
}

private fun headerProviderFrom(
    header: String,
    splitter: (String) -> List<String>
): (Int) -> String {
    val headers = splitter(header)
    return { index -> headers[index] }
}

private fun parseLine(
    line: String,
    headerProvider: (Int) -> String,
    splitter: (String) -> List<String>,   // <2>
): Map<String, String> {
    val values = splitter(line)
    val keys = values.indices.map(headerProvider)
    return keys.zip(values).toMap()
}

private fun String.splitFields(separators: String): List<String> =
    if (isEmpty()) emptyList() else split(separators)