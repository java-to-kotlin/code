package travelator.marketing

import java.io.*
import kotlin.system.exitProcess

fun main() {
    val statusCode = try {
        using(
            System.`in`.reader(),
            System.out.writer(),
            System.err.writer()
        ) { reader, writer, error ->
            val errorLines = mutableListOf<ParseFailure>()
            val reportLines = reader
                .asLineSequence()
                .toHighValueCustomerReport {
                    errorLines += it
                }
            if (errorLines.isEmpty()) {
                reportLines.writeTo(writer)
                0
            } else {
                errorLines.writeTo(error)
                -1
            }
        }
    } catch (x: IOException) {
        System.err.println("IO error processing report ${x.message}")
        -1
    }
    exitProcess(statusCode)
}

inline fun <A : Closeable, B : Closeable, C : Closeable, R> using(
    a: A,
    b: B,
    c: C,
    block: (A, B, C) -> R
): R =
    a.use {
        b.use {
            c.use {
                block(a, b, c)
            }
        }
    }

private fun List<ParseFailure>.writeTo(error: OutputStreamWriter) {
    error.appendLine("Lines with errors")
    asSequence().map { parseFailure ->
        "${parseFailure::class.simpleName} in ${parseFailure.line}"
    }.writeTo(error)
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