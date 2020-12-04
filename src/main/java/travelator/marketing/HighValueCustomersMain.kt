package travelator.marketing

import java.io.Reader
import java.io.Writer
import kotlin.system.exitProcess

fun main() {
    System.`in`.reader().use { reader ->
        System.out.writer().use { writer ->
            val errorLines = mutableListOf<String>()
            val reportLines = reader
                .asLineSequence()
                .toHighValueCustomerReport {
                    errorLines += it
                }
            if (errorLines.isNotEmpty()) {
                System.err.writer().use { error ->
                    error.appendLine("Lines with errors")
                    errorLines.asSequence().writeTo(error)
                }
                exitProcess(-1)
            } else {
                reportLines.writeTo(writer)
            }
        }
    }
}

fun Reader.asLineSequence() = buffered().lineSequence()

fun Sequence<CharSequence>.writeTo(writer: Writer) {
    writer.appendLines(this)
}

fun Writer.appendLines(lines: Sequence<CharSequence>): Writer {
    return this.also {
        lines.forEach(this::appendLine)
    }
}