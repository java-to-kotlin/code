package travelator.tablereader

fun readTableWithHeader(lines: List<String>): List<Map<String, String>> {
    return readTable(
        lines.drop(1),
        headerProviderFrom(lines.first())
    )
}

fun headerProviderFrom(header: String): (Int) -> String {
    TODO("Not yet implemented")
}

fun readTable(
    lines: List<String>,
    headerProvider: (Int) -> String = Int::toString
): List<Map<String, String>> {
    return lines.map { parseLine(it, headerProvider) }
}

private fun parseLine(
    line: String,
    headerProvider: (Int) -> String
): Map<String, String> {
    val values = line.splitFields(",")
    val keys = values.indices.map(headerProvider)
    return keys.zip(values).toMap()
}

private fun String.splitFields(separators: String): List<String> =
    if (isEmpty()) emptyList() else split(separators)