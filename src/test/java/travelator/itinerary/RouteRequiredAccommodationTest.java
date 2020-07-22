package travelator.itinerary;

import org.junit.jupiter.api.Test;
import travelator.Id;
import travelator.Location;
import travelator.money.Money;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static travelator.itinerary.TestData.*;
import static travelator.money.Currencies.EUR;

public class RouteRequiredAccommodationTest {
    @Test
    public void an_empty_route_does_not_need_accommodation() {
        Route emptyRoute = new Route(emptyList());

        assertEquals(emptyList(), emptyRoute.requiredAccommodation());
    }

    @Test
    public void a_route_of_one_journey_does_not_need_accommodation() {
        Route r = routeOf(
            journey(
                Jakarta_Tanjung_Priok_Ferry_Terminal, "2020-08-06 23:59 Asia/Jakarta",
                Batam_Batu_Ampar_Ferry_Terminal, "2020-08-08 05:40 Asia/Jakarta"));
        assertEquals(emptyList(), r.requiredAccommodation());
    }

    @Test
    public void a_route_of_two_journeys_does_not_need_accommodation_if_continue_on_same_day() {
        Route r = routeOf(
            journey(
                Batam_Sekupang_Ferry_Terminal, "2020-08-08 07:00 Asia/Jakarta",
                Singapore_Harbourfront_Centre, "2020-08-08 06:50 Asia/Singapore"),
            journey(
                Singapore_Harbourfront_Centre, "2020-08-08 07:00 Asia/Singapore",
                Johor_Bahru_Sentral, "2020-08-08 08:00 Asia/Kuala_Lumpur"));

        assertEquals(emptyList(), r.requiredAccommodation());
    }

    @Test
    public void a_route_of_two_journeys_that_requires_accommodation() {
        Route r = routeOf(
            journey(
                Johor_Bahru_Sentral, "2020-08-10 10:10 Asia/Kuala_Lumpur",
                Kuala_Lumpur_Sentral, "2020-08-10 17:36 Asia/Kuala_Lumpur"),
            journey(
                Kuala_Lumpur_Sentral, "2020-08-11 10:28 Asia/Kuala_Lumpur",
                Bangkok, "2020-08-12 09:10 Asia/Bangkok"));

        assertEquals(
            List.of(new Interchange(
                Kuala_Lumpur_Sentral, dateTime("2020-08-10 17:36 Asia/Kuala_Lumpur"),
                Kuala_Lumpur_Sentral, dateTime("2020-08-11 10:28 Asia/Kuala_Lumpur"))),
            r.requiredAccommodation());
    }

    @Test
    public void a_route_of_two_journeys_that_has_a_short_overnight_wait_so_does_not_need_accommodation() {
        Route r = routeOf(
            journey(
                Johor_Bahru_Sentral, "2020-08-10 10:10 Asia/Kuala_Lumpur",
                Kuala_Lumpur_Sentral, "2020-08-10 23:30 Asia/Kuala_Lumpur"),
            journey(
                Kuala_Lumpur_Sentral, "2020-08-11 00:30 Asia/Kuala_Lumpur",
                Bangkok, "2020-08-12 09:10 Asia/Bangkok"));

        assertEquals(emptyList(), r.requiredAccommodation());
    }

    @Test
    public void a_route_that_requires_accommodation_for_multiple_nights() {
        Route r = routeOf(
            journey(
                Jakarta_Tanjung_Priok_Ferry_Terminal, "2020-08-06 23:59 Asia/Jakarta",
                Batam_Batu_Ampar_Ferry_Terminal, "2020-08-08 05:40 Asia/Jakarta"),
            journey(
                Batam_Sekupang_Ferry_Terminal, "2020-08-08 07:00 Asia/Jakarta",
                Singapore_Harbourfront_Centre, "2020-08-08 06:50 Asia/Singapore"),
            journey(
                Johor_Bahru_Sentral, "2020-08-10 10:10 Asia/Kuala_Lumpur",
                Kuala_Lumpur_Sentral, "2020-08-10 17:36 Asia/Kuala_Lumpur"),
            journey(
                Kuala_Lumpur_Sentral, "2020-08-11 10:28 Asia/Kuala_Lumpur",
                Bangkok, "2020-08-12 09:10 Asia/Bangkok"));

        assertEquals(
            List.of(
                new Interchange(
                    Singapore_Harbourfront_Centre, dateTime("2020-08-08 06:50 Asia/Singapore"),
                    Johor_Bahru_Sentral, dateTime("2020-08-10 10:10 Asia/Kuala_Lumpur")),
                new Interchange(
                    Kuala_Lumpur_Sentral, dateTime("2020-08-10 17:36 Asia/Kuala_Lumpur"),
                    Kuala_Lumpur_Sentral, dateTime("2020-08-11 10:28 Asia/Kuala_Lumpur"))),
            r.requiredAccommodation());
    }

    private Journey journey(Location origin,
                            String departureTimeStr,
                            Location destination,
                            String arrivalTimeStr
    ) {
        return new Journey(
            origin,
            destination,
            Id.of("example-operator"),
            TravelMethod.RAIL,
            dateTime(departureTimeStr),
            dateTime(arrivalTimeStr),
            "Standard",
            Money.of(100, EUR)
        );
    }

    public static Route routeOf(Journey... journeys) {
        return new Route(List.of(journeys));
    }
}