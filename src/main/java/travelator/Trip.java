package travelator;

import java.time.Instant;
import java.time.ZonedDateTime;

public class Trip {
    private final String id;
    private final String customerId;
    private final String name;

    private final ZonedDateTime plannedStartTime;
    private final ZonedDateTime plannedEndTime;
    private final BookingStatus bookingStatus;

    public Trip(
        String id,
        String customerId,
        String name,
        ZonedDateTime plannedStartTime,
        ZonedDateTime plannedEndTime,
        BookingStatus bookingStatus
    ) {
        this.id = id;
        this.customerId = customerId;
        this.name = name;
        this.plannedStartTime = plannedStartTime;
        this.plannedEndTime = plannedEndTime;
        this.bookingStatus = bookingStatus;
    }

    public String getCustomerId() {
        return customerId;
    }

    public ZonedDateTime getPlannedStartTime() {
        return plannedStartTime;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public boolean isPlannedToBeActiveAt(Instant time) {
        return !plannedStartTime.toInstant().isAfter(time)
            && plannedEndTime.toInstant().isAfter(time);
    }


    enum BookingStatus {
        BOOKED, NOT_BOOKED
    }
}