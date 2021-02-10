package com.egovorushkin.logiweb.dto;

import com.egovorushkin.logiweb.entities.City;
import com.egovorushkin.logiweb.entities.enums.DriverStatus;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Objects;

public class DriverDto implements Serializable {

    private long id;

    private String username;

    @Pattern(regexp = "[A-Z][a-z]*", message =
            "Firstname must be like \"Ivan\"")
    @NotEmpty(message = "Firstname should not be empty.")
    private String firstName;

    @Pattern(regexp = "[A-Z][a-z]*", message =
            "Lastname must be like \"Ivanov\"")
    @NotEmpty(message = "Lastname should not be empty.")
    private String lastName;

    @Range(min = 0, max = 176,
            message = "Worked hours should be between 0 and 176.")
    private int workedHoursPerMonth;

    private boolean isInShift;

    private DriverStatus status;

    private City currentCity;

    private TruckDto truck;

    public DriverDto() {
        status = DriverStatus.RESTING;
        workedHoursPerMonth = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public void setStatus(DriverStatus status) {
        this.status = status;
    }

    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City currentCity) {
        this.currentCity = currentCity;
    }

    public TruckDto getTruck() {
        return truck;
    }

    public void setTruck(TruckDto truck) {
        this.truck = truck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriverDto driverDto = (DriverDto) o;
        return id == driverDto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DriverDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", workedHoursPerMonth=" + workedHoursPerMonth +
                ", isInShift=" + isInShift +
                ", status=" + status +
                '}';
    }
}
