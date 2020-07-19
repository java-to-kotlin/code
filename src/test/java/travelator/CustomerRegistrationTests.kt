package travelator;

import org.junit.jupiter.api.Test;
import travelator.handlers.RegistrationData;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerRegistrationTests {

    InMemoryCustomers customers = new InMemoryCustomers();
    Set<String> excluded = Set.of(
        "cruella@hellhall.co.uk"
    );
    CustomerRegistration registration = new CustomerRegistration(customers,
        (registrationData) -> excluded.contains(registrationData.email)
    );

    @Test
    public void adds_a_customer_when_not_excluded()
        throws DuplicateException, ExcludedException {
        assertEquals(Optional.empty(), customers.find("0"));

        Customer added = registration.register(
            new RegistrationData("fred flintstone", "fred@bedrock.com")
        );
        assertEquals(
            new Customer("0", "fred flintstone", "fred@bedrock.com"),
            added
        );
        assertEquals(added, customers.find("0").orElseThrow());
    }

    @Test
    public void throws_DuplicateException_when_email_address_exists() {
        customers.add(new Customer("0", "fred flintstone", "fred@bedrock.com"));
        assertEquals(1, customers.size());

        assertThrows(DuplicateException.class,
            () -> registration.register(
                new RegistrationData("another name", "fred@bedrock.com")
            )
        );
        assertEquals(1, customers.size());
    }

    @Test
    public void throws_ExcludedException_when_excluded() {
        assertThrows(ExcludedException.class,
            () -> registration.register(
                new RegistrationData("cruella de vil", "cruella@hellhall.co.uk")
            )
        );
        assertEquals(0, customers.size());
    }
}