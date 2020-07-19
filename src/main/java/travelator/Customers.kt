package travelator

import dev.forkhandles.result4k.Result
import java.util.*

interface Customers {

    fun add(name:String, email:String): Result<Customer, DuplicateCustomerProblem>

    fun find(id: String): Optional<Customer>
}

data class DuplicateCustomerProblem(val message: String)