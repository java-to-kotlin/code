package travelator.tablereader

fun readTableWithHeader(lines: List<String>): List<Map<String, String>> {
    return readTable(lines)
}

fun readTable(lines: List<String>): List<Map<String, String>> {
    return lines.map(::parseLine)
}

private fun parseLine(line: String): Map<String, String> {
    val values = line.splitFields(",")
    val headerProvider: (Int) -> String = Int::toString
    val keys = values.indices.map(headerProvider)
    return keys.zip(values).toMap()
}

private fun String.splitFields(separators: String): List<String> =
    if (isEmpty()) emptyList() else split(separators)