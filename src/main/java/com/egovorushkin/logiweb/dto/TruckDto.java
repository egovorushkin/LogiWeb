package com.egovorushkin.logiweb.dto;

import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.enums.TruckState;
import com.egovorushkin.logiweb.entities.enums.TruckStatus;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * Represent a truck
 * extends {@link AbstractDto}
 * implements {@link Serializable}
 */
public class TruckDto extends AbstractDto implements Serializable {

    @NotEmpty(message = "Registration Number should not be empty")
    @Pattern(regexp = "^[A-Z]{2}[0-9]{5}$", message = "Registration Number" +
            " must be 2" +
            " characters and 5 digits (ex. \"AB12345\")")
    private String registrationNumber;
    private int teamSize;

    @Range(min = 5000, max = 40000, message = "Capacity should be between " +
            "5000 and 40000 kg.")
    private int capacity;
    private TruckStatus status = TruckStatus.PARKED;
    private TruckState state = TruckState.SERVICEABLE;
    private boolean isBusy = false;
    private CityDto currentCity;
    private Set<DriverDto> drivers;
    private Set<OrderDto> orders;

    public TruckDto() {
    }

    public TruckDto(String registrationNumber,
                    int teamSize,
                    int capacity,
                    CityDto currentCity) {
        this.registrationNumber = registrationNumber;
        this.teamSize = teamSize;
        this.capacity = capacity;
        this.currentCity = currentCity;
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

    public CityDto getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(CityDto currentCity) {
        this.currentCity = currentCity;
    }

    public Set<DriverDto> getDrivers() {
        return drivers;
    }

    public void setDrivers(Set<DriverDto> drivers) {
        this.drivers = drivers;
    }

    public Set<OrderDto> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderDto> orders) {
        this.orders = orders;
    }

    public void addDriver(DriverDto driverDto) {
        drivers.add(driverDto);
        driverDto.setTruck(this);
    }

    public void removeDriver(DriverDto driverDto) {
        drivers.remove(driverDto);
        driverDto.setTruck(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TruckDto truckDto = (TruckDto) o;

        return Objects.equals(registrationNumber, truckDto.registrationNumber);
    }

    @Override
    public int hashCode() {
        return registrationNumber != null ? registrationNumber.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TruckDto{" +
                "id=" + super.getId() +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", teamSize=" + teamSize +
                ", capacity=" + capacity +
                ", status=" + status +
                ", state=" + state +
                ", isBusy=" + isBusy +
                ", currentCity=" + currentCity +
                '}';
    }
}
