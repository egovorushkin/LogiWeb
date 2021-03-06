package com.egovorushkin.logiweb.entities.enums;

/**
 * Represent a statuses for {@link com.egovorushkin.logiweb.entities.Driver}
 * and for {@link com.egovorushkin.logiweb.dto.DriverDto}
 *
 */
public enum DriverStatus {

    RESTING("RESTING", "Resting"),
    DRIVING("DRIVING", "Driving"),
    SECOND_DRIVER("SECOND_DRIVER", "Second driver"),
    LOADING_UNLOADING("LOADING_UNLOADING", "Loading-Unloading");

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
