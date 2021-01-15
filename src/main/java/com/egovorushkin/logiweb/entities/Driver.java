package com.egovorushkin.logiweb.entities;

import com.egovorushkin.logiweb.entities.enums.DriverStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "driver")
public class Driver extends User implements Serializable {

    @Column(name = "personal_number")
    private int personalNumber;

    @Column(name = "worked_hours_per_month")
    private int workedHoursPerMonth;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DriverStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City currentCity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "truck_id")
    private Truck truck;

    public Driver() {
    }

    public Driver(int id, String username, String password, String email, List<Role> roles, int personalNumber, int workedHoursPerMonth, DriverStatus status, City currentCity, Truck truck) {
        super(id, username, password, email, roles);
        this.personalNumber = personalNumber;
        this.workedHoursPerMonth = workedHoursPerMonth;
        this.status = status;
        this.currentCity = currentCity;
        this.truck = truck;
    }

    public int getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(int personalNumber) {
        this.personalNumber = personalNumber;
    }

    public int getWorkedHoursPerMonth() {
        return workedHoursPerMonth;
    }

    public void setWorkedHoursPerMonth(int workedHoursPerMonth) {
        this.workedHoursPerMonth = workedHoursPerMonth;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }

    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City currentCity) {
        this.currentCity = currentCity;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Driver driver = (Driver) o;
        return personalNumber == driver.personalNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), personalNumber);
    }

    @Override
    public String toString() {
        return "Driver{" +
                "personalNumber=" + personalNumber +
                ", workedHoursPerMonth=" + workedHoursPerMonth +
                ", status=" + status +
                ", currentCity=" + currentCity +
                ", truck=" + truck +
                '}';
    }
}
