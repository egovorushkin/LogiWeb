package com.egovorushkin.logiweb.entities;

import com.egovorushkin.logiweb.entities.enums.CargoStatus;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Objects;

@Entity
@Table(name = "cargo")
public class Cargo extends AbstractEntity {

    @Min(value = 2, message = "Name of cargo cannot be less than 2 characters")
    @Max(value = 50, message = "Name of cargo cannot be more than 50 characters")
    @Column(name = "name", nullable = false)
    private String name;

    @Range(min = 5000, max = 40000, message = "Weight should be between 5000 and 40000 kg.")
    @Column(name = "weight", nullable = false)
    private int weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CargoStatus status;

    public Cargo() {
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
