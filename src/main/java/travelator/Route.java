package travelator;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private final List<Journey> journeys = new ArrayList<>(); // <1>

    public int size() {
        return journeys.size();
    }

    public Journey getJourney(int index) {
        return journeys.get(index); // <2>
    }

    public void addJourney(Journey journey) {
        journeys.add(journey);
    }

    public Location getDepartsFrom() {
        return getJourney(0).getDepartsFrom();
    }
}