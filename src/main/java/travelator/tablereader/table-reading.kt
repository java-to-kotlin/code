package travelator.tablereader

fun readTableWithHeader(
    lines: List<String>
): List<Map<String, String>> =
    when {
        lines.isEmpty() -> emptyList()
        else -> readTable(
            lines.drop(1),
            headerProviderFrom(lines.first())
        )
    }

fun readTable(
    lines: List<String>,
    headerProvider: (Int) -> String = Int::toString
): List<Map<String, String>> =
    lines.map {
        parseLine(it, headerProvider) { line -> // <1>
            line.splitFields(",")
        }
    }

private fun headerProviderFrom(header: String): (Int) -> String {
    val headers = header.splitFields(",")
    return { index -> headers[index] }
}

private fun parseLine(
    line: String,
    headerProvider: (Int) -> String,
    splitter: (String) -> List<String>, // <2>
): Map<String, String> {
    val values = splitter(line)
    val keys = values.indices.map(headerProvider)
    return keys.zip(values).toMap()
}

private fun String.splitFields(separators: String): List<String> =
    if (isEmpty()) emptyList() else split(separators)