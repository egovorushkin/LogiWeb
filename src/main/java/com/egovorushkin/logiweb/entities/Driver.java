package com.egovorushkin.logiweb.entities;

import com.egovorushkin.logiweb.entities.status.DriverStatus;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "driver")
public class Driver implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "personal_number")
    private int personalNumber;

    @Column(name = "worked_hours_per_month")
    private int workedHoursPerMonth;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DriverStatus driverStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City currentCity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "truck_id")
    private Truck currentTruck;

    public Driver() {
    }

    public Driver(String firstName, String lastName, int personalNumber, int workedHoursPerMonth, DriverStatus driverStatus, City currentCity, Truck currentTruck) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalNumber = personalNumber;
        this.workedHoursPerMonth = workedHoursPerMonth;
        this.driverStatus = driverStatus;
        this.currentCity = currentCity;
        this.currentTruck = currentTruck;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public DriverStatus getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(DriverStatus driverStatus) {
        this.driverStatus = driverStatus;
    }

    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City currentCity) {
        this.currentCity = currentCity;
    }

    public Truck getCurrentTruck() {
        return currentTruck;
    }

    public void setCurrentTruck(Truck currentTruck) {
        this.currentTruck = currentTruck;
    }


}
