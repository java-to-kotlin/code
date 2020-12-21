package travelator.tablereader

fun readTable(lines: List<String>): List<Map<String, String>> {
    return lines.map(::parseLine)
}

private fun parseLine(line: String): Map<String, String> {
    val values = splitFields(line)
    val keys = values.indices.map(Int::toString)
    return keys.zip(values).toMap()
}

private fun splitFields(line: String): List<String> =
    if (line.isEmpty()) emptyList() else line.split(",")