package travelator;

import java.util.Set;

public interface Trips {
    Set<Trip> tripsFor(String customerId);
    Set<Trip> currentTripsFor(String customerId);
}