package com.egovorushkin.logiweb.entities.enums;

/**
 * Represent a statuses for {@link com.egovorushkin.logiweb.entities.Truck}
 * and for {@link com.egovorushkin.logiweb.dto.TruckDto}
 *
 */
public enum TruckStatus {

    PARKED("PARKED", "Parked"),
    ON_THE_WAY("ON_THE_WAY", "On the way");

    String title;
    String name;

    TruckStatus(String title, String name) {
        this.title = title;
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
