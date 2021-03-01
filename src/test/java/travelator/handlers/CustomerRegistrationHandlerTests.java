package travelator.handlers;

import dev.forkhandles.result4k.Failure;
import dev.forkhandles.result4k.Success;
import org.junit.jupiter.api.Test;
import travelator.Customer;
import travelator.Duplicate;
import travelator.Excluded;
import travelator.IRegisterCustomers;
import travelator.http.Request;
import travelator.http.Response;

import static java.net.HttpURLConnection.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerRegistrationHandlerTests {

    final IRegisterCustomers registration =
        mock(IRegisterCustomers.class);
    final CustomerRegistrationHandler handler =
        new CustomerRegistrationHandler(registration);

    final String fredBody = toJson(
        "{ 'name' : 'fred', 'email' : 'fred@bedrock.com' }"
    );
    final RegistrationData fredData =
        new RegistrationData("fred", "fred@bedrock.com");

    @Test
    public void returns_Created_with_body_on_success() {

        when(registration.registerToo(fredData))
            .thenReturn(new Success<>(
                new Customer("0", fredData.name, fredData.email)
            ));

        String expectedBody = toJson(
            "{'id':'0','name':'fred','email':'fred@bedrock.com'}"
        );
        assertEquals(
            new Response(HTTP_CREATED, expectedBody),
            handler.handle(new Request(fredBody))
        );
    }

    @Test
    public void returns_Conflict_for_duplicate() {

        when(registration.registerToo(fredData))
            .thenReturn(new Failure<>(
                new Duplicate("deliberate")
            ));

        assertEquals(
            new Response(HTTP_CONFLICT),
            handler.handle(new Request(fredBody))
        );
    }

    @Test
    public void returns_Forbidden_for_excluded() {

        when(registration.registerToo(fredData))
            .thenReturn(new Failure<>(
                Excluded.INSTANCE
            ));

        assertEquals(
            new Response(HTTP_FORBIDDEN),
            handler.handle(new Request(fredBody))
        );
    }

    @Test
    public void returns_BadRequest_for_bad_json() {
        assertEquals(
            new Response(HTTP_BAD_REQUEST),
            handler.handle(new Request("bad json"))
        );
    }

    @Test
    public void returns_InternalError_for_other_exceptions() {

        when(registration.registerToo(fredData))
            .thenThrow(
                new RuntimeException("deliberate")
            );

        assertEquals(
            new Response(HTTP_INTERNAL_ERROR),
            handler.handle(new Request(fredBody))
        );
    }

    private String toJson(String jsonIsh) {
        return jsonIsh.replace('\'', '"');
    }
}