package com.egovorushkin.logiweb.dto;

import com.egovorushkin.logiweb.entities.enums.TruckState;
import com.egovorushkin.logiweb.entities.enums.TruckStatus;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Set;

public class TruckDto implements Serializable {

    private Long id;

    @NotEmpty(message = "Registration Number should not be empty")
    @Pattern(regexp = "^[A-Z]{2}[0-9]{5}$", message = "Registration Number" +
            " must be 2" +
            " characters and 5 digits (ex. \"AB12345\")")
    private String registrationNumber;
    private int teamSize;

    @Range(min = 5000, max = 40000, message = "Capacity should be between " +
            "5000 and 40000 kg.")
    private int capacity;
    private int currentNumberOfDrivers;
    private TruckStatus status = TruckStatus.PARKED;
    private TruckState state = TruckState.SERVICEABLE;
    private boolean isBusy = false;
    private CityDto currentCity;
    private Set<DriverDto> currentDrivers;
    private Set<OrderDto> currentOrders;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public int getCurrentNumberOfDrivers() {
        if (currentDrivers != null) {
            return currentDrivers.size();
        }
        return 0;
    }

    public void setCurrentNumberOfDrivers(int currentNumberOfDrivers) {
        this.currentNumberOfDrivers = currentNumberOfDrivers;
    }

    public Set<DriverDto> getCurrentDrivers() {
        return currentDrivers;
    }

    public void setCurrentDrivers(Set<DriverDto> currentDrivers) {
        this.currentDrivers = currentDrivers;
    }

    public Set<OrderDto> getCurrentOrders() {
        return currentOrders;
    }

    public void setCurrentOrders(Set<OrderDto> currentOrders) {
        this.currentOrders = currentOrders;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "TruckDto{" +
                "id=" + id +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", teamSize=" + teamSize +
                ", capacity=" + capacity +
                ", currentNumberOfDrivers=" + currentNumberOfDrivers +
                ", status=" + status +
                ", state=" + state +
                ", isBusy=" + isBusy +
                ", currentCity=" + currentCity +
                '}';
    }
}
