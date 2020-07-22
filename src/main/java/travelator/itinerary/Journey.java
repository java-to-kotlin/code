package travelator.itinerary;

import travelator.Id;
import travelator.Location;
import travelator.money.Money;

import java.time.ZonedDateTime;

/**
 * A single journey
 */
public class Journey {
    private Id<Journey> id;
    private Location departsFrom, arrivesAt;
    private Id<Operator> operatorId;
    private TravelMethod method;
    private ZonedDateTime departureTime; // in the timezone of from Location
    private ZonedDateTime arrivalTime;   // in the timezone of destination
    private String operatorClass;
    private Money price;

    public Journey() {
    }

    public Journey(
        Location departsFrom,
        Location arrivesAt,
        Id<Operator> operatorId,
        TravelMethod method,
        ZonedDateTime departureTime,
        ZonedDateTime arrivalTime,
        String operatorClass,
        Money price
    ) {
        this.id = Id.mint();
        this.departsFrom = departsFrom;
        this.arrivesAt = arrivesAt;
        this.operatorId = operatorId;
        this.method = method;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.operatorClass = operatorClass;
        this.price = price;
    }

    public Location getDepartsFrom() {
        return departsFrom;
    }

    public void setDepartsFrom(Location departsFrom) {
        this.departsFrom = departsFrom;
    }

    public Location getArrivesAt() {
        return arrivesAt;
    }

    public void setArrivesAt(Location arrivesAt) {
        this.arrivesAt = arrivesAt;
    }

    public ZonedDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(ZonedDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public ZonedDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(ZonedDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public TravelMethod getMethod() {
        return method;
    }

    public void setMethod(TravelMethod method) {
        this.method = method;
    }

    public Id<Operator> getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Id<Operator> operatorId) {
        this.operatorId = operatorId;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public String getOperatorClass() {
        return operatorClass;
    }

    public void setOperatorClass(String operatorClass) {
        this.operatorClass = operatorClass;
    }

    public Id<Journey> getId() {
        return id;
    }

    public void setId(Id<Journey> id) {
        this.id = id;
    }
}