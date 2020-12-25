package com.egovorushkin.logiweb.entities;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {

    private int id;

    private int uniqueNumber;

    private boolean isCompleted;

    private Truck truck;

    private List<Driver> drivers;
}
