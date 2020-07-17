package travelator.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import travelator.Customer;
import travelator.DuplicateException;
import travelator.ExcludedException;
import travelator.IRegisterCustomers;
import travelator.http.Request;
import travelator.http.Response;

import static java.net.HttpURLConnection.*;

public class CustomerRegistrationHandler {

    private final IRegisterCustomers registration;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomerRegistrationHandler(IRegisterCustomers registration) {
        this.registration = registration;
    }

    public Response handle(Request request) {
        try {
            RegistrationData data = objectMapper.readValue(
                request.getBody(),
                RegistrationData.class
            );
            Customer customer = registration.register(data);
            return new Response(HTTP_CREATED,
                objectMapper.writeValueAsString(customer)
            );
        } catch (JsonProcessingException x) {
            return new Response(HTTP_BAD_REQUEST);
        } catch (ExcludedException x) {
            return new Response(HTTP_FORBIDDEN);
        } catch (DuplicateException x) {
            return new Response(HTTP_CONFLICT);
        } catch (Exception x) {
            return new Response(HTTP_INTERNAL_ERROR);
        }
    }
}