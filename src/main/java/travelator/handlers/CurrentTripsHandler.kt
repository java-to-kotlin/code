package travelator.handlers

import com.fasterxml.jackson.databind.ObjectMapper
import travelator.ITrackTrips
import travelator.http.Request
import travelator.http.Response
import java.net.HttpURLConnection.*
import java.time.Clock

class CurrentTripsHandler(
    private val tracking: ITrackTrips,
    private val clock: Clock
) {
    private val objectMapper = ObjectMapper()

    fun handle(request: Request): Response {
        return try {
            val customerId = request.getQueryParam("customerId").firstOrNull()
                ?: return Response(HTTP_BAD_REQUEST)
            tracking.currentTripFor(customerId, clock.instant())?.let { currentTrip ->
                Response(
                    HTTP_OK,
                    objectMapper.writeValueAsString(currentTrip)
                )
            } ?: Response(HTTP_NOT_FOUND)
        } catch (x: Exception) {
            Response(HTTP_INTERNAL_ERROR)
        }
    }
}