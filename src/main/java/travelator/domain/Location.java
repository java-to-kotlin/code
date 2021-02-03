package travelator.domain;

import travelator.Id;

import java.util.Objects;

public class Location {
    private final Id<Location> id;
    private final String localName;
    private final String userReadableName;

    public Location(Id<Location> id, String localName, String userReadableName) {
        this.id = id;
        this.localName = localName;
        this.userReadableName = userReadableName;
    }

    public Id<Location> getId() {
        return id;
    }

    public String getLocalName() {
        return localName;
    }

    public String getUserReadableName() {
        return userReadableName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return id.equals(location.id) &&
            localName.equals(location.localName) &&
            userReadableName.equals(location.userReadableName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, localName, userReadableName);
    }

    @Override
    public String toString() {
        return "Location(" + id + ": " + userReadableName + ")";
    }
}