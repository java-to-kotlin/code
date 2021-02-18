package travelator.itinerary;

import travelator.Location;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Route {
    private final List<Journey> journeys;

    public Route(List<Journey> journeys) {
        this.journeys = journeys;
    }

    public int size() {
        return journeys.size();
    }

    public Journey get(int index) {
        return journeys.get(index);
    }

    public Location getDepartsFrom() {
        return get(0).getDepartsFrom();
    }

    public Location getArrivesAt() {
        return get(size() - 1).getArrivesAt();
    }

    public Duration getDuration() {
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