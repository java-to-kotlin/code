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

    public void addCostsTo(CostSummaryCalculator calculator) {
        for (var j : journeys) {
            calculator.addCost(j.getPrice());
        }
    }

    public List<Interchange> requiredAccommodation() {
        var results = new ArrayList<Interchange>();

        for (int i = 1; i < journeys.size(); i++) {
            var interchange =
                Interchange.between(journeys.get(i - 1), journeys.get(i));

            if (interchange.isAccommodationRequired()) {
                results.add(interchange);
            }
        }

        return results;
    }
}