package travelator.marketing


fun main() {
    System.`in`.reader().use { reader ->
        System.out.writer().use { writer ->
            generate(
                reader.readLines()
            ).forEach { line ->
                writer.appendLine(line)
            }
        }
    }
}