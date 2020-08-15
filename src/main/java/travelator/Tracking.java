package travelator;

import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static travelator.Trip.BookingStatus.BOOKED;

class Tracking implements ITrackTrips {

    private final Trips trips;

    public Tracking(Trips trips) {
        this.trips = trips;
    }

    @Override
    public Optional<Trip> currentTripFor(String customerId) {
        var candidates = trips.currentTripsFor(customerId).stream()
            .filter((trip) -> trip.getBookingStatus() == BOOKED)
            .collect(toList());
        if (candidates.size() == 1)
            return Optional.of(candidates.get(0));
        else if (candidates.size() == 0)
            return Optional.empty();
        else
            throw new IllegalStateException(
                "Unexpectedly more than one current trip for " + customerId
            );
    }
}