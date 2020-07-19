package travelator

import travelator.handlers.RegistrationData

interface IRegisterCustomers {

    @Throws(ExcludedException::class, DuplicateException::class)
    fun register(data: RegistrationData): Customer
}