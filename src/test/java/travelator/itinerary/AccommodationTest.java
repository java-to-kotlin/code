package travelator.itinerary;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import travelator.Id;
import travelator.Location;
import travelator.geo.Position;
import travelator.money.Money;

import java.time.ZonedDateTime;
import java.util.Currency;

public class AccommodationTest {
    @Test
    public void reports_number_of_nights() {
        var a = new Accommodation(
            Id.mint(),
            new Location(Id.mint(), "The Davis Bangkok", "The Davis Bangkok", new Position(0,0)),
            ZonedDateTime.parse("2022-05-13T15:00:00+07:00[Asia/Bangkok]"),
            ZonedDateTime.parse("2022-05-15T10:00:00+07:00[Asia/Bangkok]"),
            Money.of(250, Currency.getInstance("USD")));

        Assertions.assertEquals(2, a.getNights());
    }
}