package travelator

import com.natpryce.failureOrNull
import com.natpryce.valueOrNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import travelator.handlers.RegistrationData
import java.util.*

class CustomerRegistrationTests {

    private val customers = InMemoryCustomers()
    private val excluded = setOf("cruella@hellhall.co.uk")
    private val registration = CustomerRegistration(
        customers,
        ExclusionList { excluded.contains(it.email) }
    )

    @Test
    fun `adds a customer when not excluded`() {
        assertEquals(Optional.empty<Any>(), customers.find("0"))
        val added = registration.registerToo(
            RegistrationData("fred flintstone", "fred@bedrock.com")
        ).valueOrNull()
        assertEquals(
            Customer("0", "fred flintstone", "fred@bedrock.com"),
            added
        )
        assertEquals(added, customers.find("0").orElseThrow())
    }

    @Test
    fun `returns Duplicate when email address exists`() {
        customers.add(Customer("0", "fred flintstone", "fred@bedrock.com"))
        assertEquals(1, customers.size())
        val failure = registration.registerToo(
            RegistrationData("another name", "fred@bedrock.com")
        ).failureOrNull()
        assertEquals(
            Duplicate("customer with email fred@bedrock.com already exists"),
            failure
        )
        assertEquals(1, customers.size())
    }

    @Test
    fun `returns Excluded when excluded`() {
        val failure = registration.registerToo(
            RegistrationData("cruella de vil", "cruella@hellhall.co.uk")
        ).failureOrNull()
        assertEquals(
            Excluded,
            failure
        )
        assertEquals(0, customers.size())
    }
}