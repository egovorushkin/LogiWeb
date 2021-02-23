package com.egovorushkin.logiweb.dto;

import com.egovorushkin.logiweb.entities.enums.DriverStatus;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represent a driver
 * extends {@link AbstractDto}
 * implements {@link Serializable}
 */
public class DriverDto extends AbstractDto implements Serializable {

    private String username;

    @Pattern(regexp = "[A-Z][a-z]*", message =
            "Firstname must be like \"John\"")
    @NotEmpty(message = "Firstname should not be empty.")
    private String firstName;

    @Pattern(regexp = "[A-Z][a-z]*", message =
            "Lastname must be like \"Johnson\"")
    @NotEmpty(message = "Lastname should not be empty.")
    private String lastName;

    private int workedHoursPerMonth;

    private boolean isInShift;

    private DriverStatus status;

    private CityDto currentCity;

    private TruckDto truck;


    public DriverDto() {
        status = DriverStatus.RESTING;
        workedHoursPerMonth = 0;
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

    public CityDto getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(CityDto currentCity) {
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

        return Objects.equals(username, driverDto.username);
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "DriverDto{" +
                "id=" + super.getId() +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", workedHoursPerMonth=" + workedHoursPerMonth +
                ", isInShift=" + isInShift +
                ", status=" + status +
                ", currentCity=" + currentCity +
                ", truck=" + truck +
                '}';
    }
}
