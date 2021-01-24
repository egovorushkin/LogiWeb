package com.egovorushkin.logiweb.entities;

import com.egovorushkin.logiweb.entities.enums.TruckState;
import com.egovorushkin.logiweb.entities.enums.TruckStatus;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "truck")
public class Truck extends AbstractEntity {


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

    @OneToMany(mappedBy = "truck", fetch = FetchType.EAGER)
    private List<Driver> currentDrivers = new ArrayList<>();

    public Truck() {
    }

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

    public List<Driver> getCurrentDrivers() {
        return currentDrivers;
    }

    public void setCurrentDrivers(List<Driver> currentDrivers) {
        this.currentDrivers = currentDrivers;
    }

    public void addDriver(Driver driver) {
        currentDrivers.add(driver);
        driver.setTruck(this);
    }

    public void removeDriver(Driver driver) {
        currentDrivers.remove(driver);
        driver.setTruck(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Truck truck = (Truck) o;
        return id == truck.id &&
                registrationNumber.equals(truck.registrationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNumber);
    }
}
