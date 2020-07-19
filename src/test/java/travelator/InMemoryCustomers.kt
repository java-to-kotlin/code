package travelator

import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Result
import dev.forkhandles.result4k.Success
import travelator.util.toOptional
import java.util.*

class InMemoryCustomers : Customers {

    private val list: MutableList<Customer> = ArrayList()
    private var id = 0

    override fun add(name: String, email: String)
        : Result<Customer, DuplicateCustomerProblem> =
        when {
            list.any { it.email == email } -> Failure(
                DuplicateCustomerProblem(
                    "customer with email $email already exists"
                )
            )
            else -> {
                val result = Customer(id++.toString(), name, email)
                list.add(result)
                Success(result)
            }
        }

    override fun find(id: String): Optional<Customer> =
        list.firstOrNull { it.id == id }.toOptional()

    // for test
    fun add(customer: Customer) {
        list.add(customer)
    }

    fun size(): Int = list.size
}