package com.egovorushkin.logiweb.entities;

import com.egovorushkin.logiweb.entities.status.TruckStatus;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "truck")
public class Truck implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Registration Number should not be empty")
    @Pattern(regexp = "^[a-zA-Z]{2}[0-9]{5}$", message = "Registration Number must be 2 characters and 5 digits (ex. \"AB12345\")")
    @Column(name = "registration_number", unique = true)
    private String registrationNumber;

    @Range(max = 3, message = "Team size should be greater than 0 and less or equals 3.")
    @Column(name = "team_size")
    private int teamSize;

    @Range(max = 28000, message = "Capacity should be less or equals 28000 kg.")
    @Column(name = "capacity")
    private int capacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TruckStatus truckStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City currentCity;

    @OneToMany(mappedBy = "currentTruck", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Driver> currentDrivers = new ArrayList<>();

    public Truck() {
    }

    public Truck(String registrationNumber, int teamSize, int capacity, TruckStatus status, City currentCity) {
        this.registrationNumber = registrationNumber;
        this.teamSize = teamSize;
        this.capacity = capacity;
        this.truckStatus = status;
        this.currentCity = currentCity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return truckStatus;
    }

    public void setStatus(TruckStatus status) {
        this.truckStatus = status;
    }

    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City currentCity) {
        this.currentCity = currentCity;
    }

    public void addDriver(Driver driver) {
        currentDrivers.add(driver);
        driver.setCurrentTruck(this);
    }

    public void removeDriver(Driver driver) {
        currentDrivers.remove(driver);
        driver.setCurrentTruck(null);
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
        return Objects.hash(id, registrationNumber);
    }

    @Override
    public String toString() {
        return registrationNumber;
    }
}
