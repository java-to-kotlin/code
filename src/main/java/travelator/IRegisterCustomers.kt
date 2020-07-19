package travelator

import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Result
import dev.forkhandles.result4k.Success
import travelator.handlers.RegistrationData

interface IRegisterCustomers {

    @Throws(ExcludedException::class, DuplicateException::class)
    fun register(data: RegistrationData): Customer

    fun registerToo(data: RegistrationData):
        Result<Customer, RegistrationProblem>
}

sealed class RegistrationProblem

object Excluded : RegistrationProblem()

data class Duplicate(
    val message: String?
) : RegistrationProblem()