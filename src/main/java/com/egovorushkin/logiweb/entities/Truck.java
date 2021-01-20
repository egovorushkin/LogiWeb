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

    @NotEmpty(message = "Registration Number should not be empty")
    @Pattern(regexp = "^[a-zA-Z]{2}[0-9]{5}$", message = "Registration Number" +
            " must be 2" +
            " characters and 5 digits (ex. \"AB12345\")")
    @Column(name = "registration_number", unique = true, nullable = false,length = 7)
    private String registrationNumber;

    @Range(max = 2, message = "Team size should be greater than 0 and less or" +
            " equals 2.")
    @Column(name = "team_size")
    private int teamSize;

    @Range(max = 28000, message = "Capacity should be less or equals 28000 kg.")
    @Column(name = "capacity")
    private int capacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TruckStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private TruckState state;

    @ManyToOne
    @JoinColumn(name = "city_id")
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
