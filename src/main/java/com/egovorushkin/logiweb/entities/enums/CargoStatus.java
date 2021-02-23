package com.egovorushkin.logiweb.entities.enums;

/**
 * Represent a statuses for {@link com.egovorushkin.logiweb.entities.Cargo}
 * and for {@link com.egovorushkin.logiweb.dto.CargoDto}
 *
 */
public enum CargoStatus {

    PREPARED("PREPARED", "Prepared"),
    SHIPPED("SHIPPED", "Shipped"),
    DELIVERED("DELIVERED", "Delivered");

    String title;
    String name;

    CargoStatus(String title, String name) {
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
