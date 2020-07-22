package travelator.itinerary;

import travelator.Id;
import travelator.Location;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;
import static java.util.Locale.ROOT;

public class TestData {
    public static Location Jakarta_Tanjung_Priok_Ferry_Terminal = new Location(
        Id.of("TestData.Jakarta_Tanjung_Priok_Ferry_Terminal"),
        "Jakarta Tanjung Priok",
        "Jakarta Tanjung Priok");

    public static Location Batam_Batu_Ampar_Ferry_Terminal = new Location(
        Id.of("TestData.Batam_Batu_Ampar_Ferry_Terminal"),
        "Batam Batu Ampar",
        "Batam Batu Ampar");

    public static Location Batam_Sekupang_Ferry_Terminal = new Location(
        Id.of("TestData.Batam_Sekupang_Ferry_Terminal"),
        "Batam Sekupang",
        "Batam Sekupang");

    public static Location Singapore_Harbourfront_Centre = new Location(
        Id.of("TestData.Singapore_Harbourfront_Centre"),
        "Singapore Harbourfront Centre",
        "Singapore Harbourfront Centre");

    public static Location Johor_Bahru_Sentral = new Location(
        Id.of("TestData.Johor_Bahru_Sentral"),
        "Johor Bahru Sentral",
        "Johor Bahru Central Station");

    public static Location Kuala_Lumpur_Sentral = new Location(
        Id.of("TestData.Kuala_Lumpur_Sentral"),
        "Kuala Lumpur Sentral",
        "Kuala Lumpur Central Station");

    public static Location Bangkok = new Location(
        Id.of("TestData.Bangkok"),
        "Bangkok",
        "Bangkok");

    public static ZonedDateTime dateTime(String s) {
        return ZonedDateTime.parse(s, formatter);
    }

    private static final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
        .parseCaseInsensitive()
        .parseCaseInsensitive()
        .append(ISO_LOCAL_DATE)
        .appendLiteral(" ")
        .append(ISO_LOCAL_TIME)
        .appendLiteral(" ")
        .appendZoneOrOffsetId()
        .toFormatter(ROOT);
}