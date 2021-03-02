package com.egovorushkin.logiweb.entities;

import com.egovorushkin.logiweb.entities.enums.TruckState;
import com.egovorushkin.logiweb.entities.enums.TruckStatus;
import javax.persistence.*;
import java.util.*;

/**
 * Represent a truck
 * extends {@link AbstractEntity}
 */
@Entity
@Table(name = "truck")
public class Truck extends AbstractEntity{

    @Column(name = "registration_number", unique = true, nullable = false,
            length = 7)
    private String registrationNumber;

    @Column(name = "team_size", nullable = false)
    private int teamSize;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TruckStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private TruckState state;

    @Column(name = "busy")
    private boolean isBusy;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City currentCity;

    @OneToMany(mappedBy = "truck")
    private Set<Driver> drivers = new HashSet<>();

    @OneToMany(mappedBy = "truck")
    private Set<Order> orders = new HashSet<>();

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public TruckStatus getStatus() {
        return status;
    }

    public void setStatus(TruckStatus status) {
        this.status = status;
    }

    public TruckState getState() {
        return state;
    }

    public void setState(TruckState state) {
        this.state = state;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City currentCity) {
        this.currentCity = currentCity;
    }

    public Set<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(Set<Driver> currentDrivers) {
        this.drivers = currentDrivers;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> currentOrders) {
        this.orders = currentOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Truck truck = (Truck) o;
        return id.equals(truck.id) &&
                registrationNumber.equals(truck.registrationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNumber);
    }
}
