package travelator.handlers

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import dev.forkhandles.result4k.map
import dev.forkhandles.result4k.recover
import travelator.Duplicate
import travelator.Excluded
import travelator.IRegisterCustomers
import travelator.RegistrationProblem
import travelator.http.Request
import travelator.http.Response
import java.net.HttpURLConnection.*

class CustomerRegistrationHandler(
    private val registration: IRegisterCustomers
) {
    private val objectMapper = ObjectMapper()

    fun handle(request: Request): Response =
        try {
            val data = objectMapper.readValue(
                request.body,
                RegistrationData::class.java
            )
            registration.registerToo(data)
                .map { value ->
                    Response(
                        HTTP_CREATED,
                        objectMapper.writeValueAsString(value)
                    )
                }
                .recover { reason -> reason.toResponse() }
        } catch (x: JsonProcessingException) {
            Response(HTTP_BAD_REQUEST)
        } catch (x: Exception) {
            Response(HTTP_INTERNAL_ERROR)
        }
}

private fun RegistrationProblem.toResponse() = when (this) {
    is Duplicate -> Response(HTTP_CONFLICT)
    is Excluded -> Response(HTTP_FORBIDDEN)
}