package travelator;

import java.time.Duration;
import java.time.ZonedDateTime;

public class Journey {

    private final Location departsFrom;
    private final Location arrivesAt;
    private final ZonedDateTime departureTime;
    private final ZonedDateTime arrivalTime;

    public Journey(Location departsFrom, Location arrivesAt, ZonedDateTime departureTime, ZonedDateTime arrivalTime) {
        this.departsFrom = departsFrom;
        this.arrivesAt = arrivesAt;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Location getDepartsFrom() {
        return departsFrom;
    }

    public Location getArrivesAt() {
        return arrivesAt;
    }

    public ZonedDateTime getDepartureTime() {
        return departureTime;
    }

    public ZonedDateTime getArrivalTime() {
        return arrivalTime;
    }

    public Duration getDuration() {
        return Duration.between(departureTime, arrivalTime);
    }
}