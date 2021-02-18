package travelator.itinerary;

import travelator.Location;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Route {
    private final List<Journey> journeys; // <1>

    public Route(List<Journey> journeys) {
        this.journeys = journeys; // <2>
    }

    public int size() { // <3>
        return journeys.size();
    }

    public Journey get(int index) { // <3>
        return journeys.get(index);
    }

    public Location getDepartsFrom() { // <4>
        return get(0).getDepartsFrom();
    }

    public Location getArrivesAt() { // <4>
        return get(size() - 1).getArrivesAt();
    }

    public Duration getDuration() { // <4>
        return Duration.between(
            get(0).getDepartureTime(),
            get(size() - 1).getArrivalTime());
    }

    public Route withJourneyAt(int index, Journey replacedBy) {
        var newJourneys = new ArrayList<>(this.journeys);
        newJourneys.set(index, replacedBy);
        return new Route(newJourneys);
    }
}