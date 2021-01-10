package com.egovorushkin.logiweb.entities.enums;

public enum DriverStatus {

    RESTING("RESTING", "Resting"),
    IN_SHIFT("IN_SHIFT", "In shift"),
    DRIVING("DRIVING", "Driving");

    String title;
    String name;

    DriverStatus(String title, String name) {
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
