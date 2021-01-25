package com.egovorushkin.logiweb.dto;

import com.egovorushkin.logiweb.entities.enums.CargoStatus;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Objects;

public class CargoDto {

    private long id;

    @Pattern(regexp = "[A-Z][a-z]*", message = "Name of cargo must be like \"Stone\"")
    @NotEmpty(message = "Cargo name should not be empty.")
    private String name;

    @Range(min = 5000, max = 40000, message = "Weight of cargo should be " +
            "between 5000 and 40000 kg.")
    private int weight;

    private CargoStatus status;

    public CargoDto() {
        status = CargoStatus.PREPARED;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
        CargoDto cargoDto = (CargoDto) o;
        return id == cargoDto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name + " (" + weight + " kg) - " + status;
    }
}
