package travelator

import dev.forkhandles.result4k.Result
import travelator.handlers.RegistrationData

interface IRegisterCustomers {
    fun register(data: RegistrationData):
        Result<Customer, RegistrationProblem>
}

sealed class RegistrationProblem

object Excluded : RegistrationProblem()

data class Duplicate(val message: String) : RegistrationProblem()

data class DatabaseProblem(val message: String) : RegistrationProblem()