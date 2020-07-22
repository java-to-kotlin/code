package travelator.itinerary;

import travelator.Location;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Objects;

public class Interchange {
    private final Location arrivalLocation;
    private final ZonedDateTime arrivalTime;
    private final Location departureLocation;
    private final ZonedDateTime departureTime;

    public Interchange(Location arrivalLocation, ZonedDateTime arrivalTime, Location departureLocation, ZonedDateTime departureTime) {
        this.arrivalLocation = arrivalLocation;
        this.arrivalTime = arrivalTime;
        this.departureLocation = departureLocation;
        this.departureTime = departureTime;
    }

    public static Interchange between(Journey incoming, Journey outgoing) {
        return new Interchange(
            incoming.getArrivesAt(), incoming.getArrivalTime(),
            outgoing.getDepartsFrom(), outgoing.getDepartureTime());
    }

    public Location getArrivalLocation() {
        return arrivalLocation;
    }

    public ZonedDateTime getArrivalTime() {
        return arrivalTime;
    }

    public Location getDepartureLocation() {
        return departureLocation;
    }

    public ZonedDateTime getDepartureTime() {
        return departureTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interchange that = (Interchange) o;
        return arrivalLocation.equals(that.arrivalLocation) &&
            arrivalTime.equals(that.arrivalTime) &&
            departureLocation.equals(that.departureLocation) &&
            departureTime.equals(that.departureTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arrivalLocation, arrivalTime, departureLocation, departureTime);
    }

    @Override
    public String toString() {
        return "RequiredStay{" +
            "arrivalLocation=" + arrivalLocation +
            ", arrivalTime=" + arrivalTime +
            ", departureLocation=" + departureLocation +
            ", departureTime=" + departureTime +
            '}';
    }

    public boolean isAccommodationRequired() {
        boolean waitIsOvernight =
            arrivalTime.toLocalDate().isBefore(departureTime.toLocalDate());
        Duration waitDuration =
            Duration.between(arrivalTime, departureTime);

        return waitIsOvernight && waitDuration.toHours() >= 6;
    }
}