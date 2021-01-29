package com.egovorushkin.logiweb.entities;

import com.egovorushkin.logiweb.entities.enums.DriverStatus;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@DynamicUpdate
@Table(name = "driver")
public class Driver extends AbstractEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "personal_number", nullable = false)
    private int personalNumber;

    @Column(name = "worked_hours_per_month", nullable = false)
    private int workedHoursPerMonth;

    @Column(name = "in_shift")
    private boolean isInShift;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DriverStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City currentCity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "truck_id")
    private Truck truck;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public boolean isInShift() {
        return isInShift;
    }

    public void setInShift(boolean inShift) {
        isInShift = inShift;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus driverStatus) {
        this.status = driverStatus;
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

    public void setTruck(Truck currentTruck) {
        this.truck = currentTruck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return personalNumber == driver.personalNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(personalNumber);
    }

    @Override
    public String toString() {
        return "Driver{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", personalNumber=" + personalNumber +
                ", workedHoursPerMonth=" + workedHoursPerMonth +
                ", isInShift=" + isInShift +
                ", status=" + status +
                ", currentCity=" + currentCity +
                ", truck=" + truck +
                ", id=" + id +
                '}';
    }
}
