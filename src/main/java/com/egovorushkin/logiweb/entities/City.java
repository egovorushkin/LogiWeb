package com.egovorushkin.logiweb.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "city")
public class City {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", unique = true, nullable = false)
    @NotNull
    private String name;

    @OneToOne(mappedBy = "currentCity", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private Truck truck;

    @OneToOne(mappedBy = "currentCity", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private Driver driver;

    public City() {
    }

    public City(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        if (truck == null) {
            if (this.truck != null) {
                this.truck.setCurrentCity(null);
            }
        } else {
            truck.setCurrentCity(this);
        }
        this.truck = truck;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        if (driver == null) {
            if (this.driver != null) {
                this.driver.setCurrentCity(null);
            }
        } else {
            driver.setCurrentCity(this);
        }
        this.driver = driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return id == city.id &&
                name.equals(city.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return name;
    }
}
