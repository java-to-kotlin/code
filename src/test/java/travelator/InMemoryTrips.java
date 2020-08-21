package travelator;

import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;

public class InMemoryTrips implements Trips {

    private final Map<String, Set<Trip>> trips = new HashMap<>();

    public void addTrip(Trip trip) {
        var existingTrips = trips.getOrDefault(
            trip.getCustomerId(),
            new HashSet<>());
        existingTrips.add(trip);
        trips.put(trip.getCustomerId(), existingTrips);
    }

    @Override
    public Set<Trip> tripsFor(String customerId) {
        return trips.getOrDefault(customerId, emptySet());
    }

    @Override
    public Set<Trip> currentTripsFor(String customerId, Instant at) {
        return tripsFor(customerId).stream()
            .filter(trip -> trip.isPlannedToBeActiveAt(at))
            .collect(toSet());
    }
}