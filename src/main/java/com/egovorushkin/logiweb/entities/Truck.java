package com.egovorushkin.logiweb.entities;

import com.egovorushkin.logiweb.entities.status.TruckStatus;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "truck")
public class Truck implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Registration Number should not be empty")
    @Pattern(regexp = "^[a-zA-Z]{2}[0-9]{5}$", message = "Registration Number must be 2" +
            " characters and 5 digits (ex. \"AB12345\")")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City currentCity;

    public Truck() {
    }

    public Truck(String registrationNumber, int teamSize, int capacity,
                 TruckStatus status, City currentCity) {
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
        return "Truck{" +
                "id=" + id +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", teamSize=" + teamSize +
                ", capacity=" + capacity +
                ", truckStatus=" + truckStatus +
                ", currentCity=" + currentCity +
                '}';
    }
}
