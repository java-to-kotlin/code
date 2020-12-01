package travelator.marketing

import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() {
    InputStreamReader(System.`in`).use { reader ->
        OutputStreamWriter(System.out).use { writer ->
            generate(writer, reader.readLines())
        }
    }
}