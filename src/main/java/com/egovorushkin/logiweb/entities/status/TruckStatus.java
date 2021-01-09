package com.egovorushkin.logiweb.entities.status;

public enum TruckStatus {

    OK("Ok"), FAULTY("Faulty");

    String title;

    TruckStatus(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
