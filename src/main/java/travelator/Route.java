package travelator;

import java.util.Vector;

public class Route {
    private final Vector journeys = new Vector();

    public int size() {
        return journeys.size();
    }

    public Journey getJourneys(int index) {
        return (Journey) journeys.elementAt(index);
    }

    public void addJourney(Journey journey) {
        journeys.add(journey);
    }

    public Location getDepartsFrom() {
        return getJourneys(0).getDepartsFrom();
    }
}