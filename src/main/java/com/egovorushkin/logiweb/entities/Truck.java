package com.egovorushkin.logiweb.entities;

import com.egovorushkin.logiweb.entities.enums.TruckState;
import com.egovorushkin.logiweb.entities.enums.TruckStatus;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
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
    private Integer id;

    @NotEmpty(message = "Registration Number should not be empty")
    @Pattern(regexp = "^[a-zA-Z]{2}[0-9]{5}$", message = "Registration Number" +
            " must be 2" +
            " characters and 5 digits (ex. \"AB12345\")")
    @Column(name = "registration_number", unique = true)
    private String registrationNumber;

    @Range(max = 2, message = "Team size should be greater than 0 and less or" +
            " equals 3.")
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private City currentCity;

    public Truck() {
    }

    public Truck(Integer id, @NotEmpty(message = "Registration Number should not " +
            "be empty")
    @Pattern(regexp = "^[a-zA-Z]{2}[0-9]{5}$", message = "Registration Number" +
            " must be 2" +
            " characters and 5 digits (ex. \"AB12345\")") String registrationNumber,
                 @Range(max = 2, message = "Team size should be greater than " +
                         "0 and less" +
                         " or equals 3.") int teamSize,
                 @Range(max = 28000, message = "Capacity should be less or " +
                         "equals 28000" +
                         " kg.") int capacity,
                 TruckStatus status, TruckState state, City currentCity) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.teamSize = teamSize;
        this.capacity = capacity;
        this.status = status;
        this.state = state;
        this.currentCity = currentCity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
                ", status=" + status +
                ", state=" + state +
                ", currentCity=" + currentCity +
                '}';
    }
}
