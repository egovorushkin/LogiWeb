package com.egovorushkin.logiweb.entities.enums;

/**
 * Represent a statuses for {@link com.egovorushkin.logiweb.entities.Order}
 * and for {@link com.egovorushkin.logiweb.dto.OrderDto}
 *
 */
public enum OrderStatus {

    NOT_COMPLETED("NOT_COMPLETED", "Not completed"),
    COMPLETED("COMPLETED", "Completed");

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
