package travelator

import java.util.*

interface Customers {
    @Throws(DuplicateException::class) // <1>
    fun add(name: String?, email: String?): Customer?
    fun find(id: String?): Optional<Customer?>?
}