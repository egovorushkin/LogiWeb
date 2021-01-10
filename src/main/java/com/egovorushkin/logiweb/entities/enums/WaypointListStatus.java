package com.egovorushkin.logiweb.entities.enums;

public enum WaypointListStatus {

    LOADING("LOADING", "Loading"),
    UNLOADING("UNLOADING", "Unloading");

    String title;
    String name;

    WaypointListStatus(String title, String name) {
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
