package com.egovorushkin.logiweb.entities;

import com.egovorushkin.logiweb.entities.enums.CargoStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import java.util.Objects;

@Entity
@Table(name = "cargo")
public class Cargo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "weight")
    private int weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CargoStatus status;

    public Cargo() {
    }

    public Cargo(int id, String name, int weight, CargoStatus status) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.status = status;
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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public CargoStatus getStatus() {
        return status;
    }

    public void setStatus(CargoStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cargo cargo = (Cargo) o;
        return id == cargo.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name + " " + weight + " " + status;
    }
}
