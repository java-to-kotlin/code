package travelator.tablereader

fun readTable(lines: List<String>): List<Map<String, String>> {
    return lines.map(::parseLine)
}

private fun parseLine(line: String): Map<String, String> {
    val values = listOf("field0", "field1")
    val keys = values.indices.map(Int::toString)
    return keys.zip(values).toMap()
}