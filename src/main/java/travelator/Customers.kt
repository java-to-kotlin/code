package travelator

import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Result
import dev.forkhandles.result4k.Success
import java.util.*

interface Customers {

    @Throws(DuplicateException::class)
    fun add(name: String, email: String): Customer

    fun addToo(name:String, email:String)
        : Result<Customer, DuplicateException> =
        try {
            Success(add(name, email))
        } catch (x: DuplicateException) {
            Failure(x)
        }

    fun find(id: String): Optional<Customer>
}