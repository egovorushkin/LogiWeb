package com.egovorushkin.logiweb.dto;

import com.egovorushkin.logiweb.entities.City;
import com.egovorushkin.logiweb.entities.enums.TruckState;
import com.egovorushkin.logiweb.entities.enums.TruckStatus;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class TruckDto implements Serializable {

    private long id;
    private String registrationNumber;
    private int teamSize;
    private int capacity;
    private int currentNumberOfDrivers;
    private TruckStatus status;
    private TruckState state;
    private City currentCity;
    private Set<DriverDto> currentDrivers;

    public TruckDto() {
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getCurrentNumberOfDrivers() {
        return currentNumberOfDrivers;
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

    public void addDriver(DriverDto driverDto) throws Exception {
        if (currentNumberOfDrivers < teamSize) {
            currentDrivers.add(driverDto);
            driverDto.setTruck(this);
        } else {
            throw new Exception("The truck shift is complete!");
        }
    }

    public void removeDriver(DriverDto driverDto) {
        currentDrivers.remove(driverDto);
        driverDto.setTruck(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TruckDto truckDto = (TruckDto) o;
        return registrationNumber.equals(truckDto.registrationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNumber);
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
                ", currentCity=" + currentCity +
                '}';
    }
}
