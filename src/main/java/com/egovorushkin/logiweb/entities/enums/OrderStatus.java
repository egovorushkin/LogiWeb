package com.egovorushkin.logiweb.entities.enums;

public enum OrderStatus {

    COMPLETED("COMPLETED", "Completed"),
    NOT_COMPLETED("NOT_COMPLETED", "Not completed");

    String title;
    String name;

    OrderStatus(String title, String name) {
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
