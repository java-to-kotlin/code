package travelator

import dev.forkhandles.result4k.Result
import java.util.*

interface Customers {

    fun add(name:String, email:String): Result<Customer, CustomersProblem>

    fun find(id: String): Optional<Customer>
}

sealed class CustomersProblem

data class DuplicateCustomerProblem(val message: String): CustomersProblem()

data class DatabaseCustomerProblem(val message: String): CustomersProblem()