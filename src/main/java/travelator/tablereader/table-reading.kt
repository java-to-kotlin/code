package travelator.tablereader

fun readTable(lines: List<String>): List<Map<String, String>> {
    return lines.map {
        mapOf("0" to "field0", "1" to "field1")
    }
}