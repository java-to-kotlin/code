package travelator.marketing

fun main() {
    System.`in`.reader().use { reader ->
        System.out.writer().use { writer ->
            writer.append(
                generate(
                    reader.readLines()
                ).joinToString("\n")
            )
        }
    }
}