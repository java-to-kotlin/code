package travelator.handlers;

import org.junit.jupiter.api.Test;
import travelator.Customer;
import travelator.DuplicateException;
import travelator.ExcludedException;
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
    public void returns_Created_with_body_on_success()
        throws DuplicateException, ExcludedException {
        when(registration.register(fredData))
            .thenReturn(
                new Customer("0", fredData.name, fredData.email)
            );

        String expectedBody = toJson(
            "{'id':'0','name':'fred','email':'fred@bedrock.com'}"
        );
        assertEquals(
            new Response(HTTP_CREATED, expectedBody),
            handler.handle(new Request(fredBody))
        );
    }

    @Test
    public void returns_Conflict_for_duplicate()
        throws DuplicateException, ExcludedException {

        when(registration.register(fredData))
            .thenThrow(
                new DuplicateException("deliberate")
            );

        assertEquals(
            new Response(HTTP_CONFLICT),
            handler.handle(new Request(fredBody))
        );
    }

    @Test
    public void returns_Forbidden_for_excluded()
        throws DuplicateException, ExcludedException {

        when(registration.register(fredData))
            .thenThrow(
                new ExcludedException()
            );

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
    public void returns_InternalError_for_other_exceptions()
        throws DuplicateException, ExcludedException {

        when(registration.register(fredData))
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