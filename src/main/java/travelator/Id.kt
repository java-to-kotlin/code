package travelator

import java.util.*

data class Id<out T> constructor(private val raw: String) {
    override fun toString(): String {
        return raw
    }

    companion object {
        @JvmStatic
        fun <T> raw(id: Id<T>) = id.raw

        @JvmStatic
        fun <T> derivedFrom(id: Id<*>) = Id<T>(id.raw)

        @JvmStatic
        fun <T> mint() = Id<T>(UUID.randomUUID().toString())
    }
}