package com.egovorushkin.logiweb.entities.enums;

public enum TruckState {
    SERVICEABLE("SERVICEABLE", "Serviceable"),
    FAULTY("FAULTY", "Faulty");

    String title;
    String name;

    TruckState(String title, String name) {
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
