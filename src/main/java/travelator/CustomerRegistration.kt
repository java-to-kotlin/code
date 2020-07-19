package travelator

import dev.forkhandles.result4k.Result
import travelator.handlers.RegistrationData

class CustomerRegistration(
    private val customers: Customers,
    private val exclusionList: ExclusionList
) : IRegisterCustomers {

    @Throws(ExcludedException::class, DuplicateException::class)
    override fun register(data: RegistrationData): Customer {
        return if (exclusionList.exclude(data)) {
            throw ExcludedException()
        } else {
            val result: Result<Customer, DuplicateException> =
                customers.addToo(data.name, data.email)
            result // <1>
        }
    }
}