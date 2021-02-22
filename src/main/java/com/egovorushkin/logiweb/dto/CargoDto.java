package com.egovorushkin.logiweb.dto;

import com.egovorushkin.logiweb.entities.enums.CargoStatus;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Objects;

public class CargoDto extends AbstractDto implements Serializable {

    @Pattern(regexp = "[A-Z]*[a-z]*",
            message = "Name of cargo must be like \"Stone\"")
    @NotEmpty(message = "Cargo name should not be empty.")
    private String name;

    @Range(min = 5000, max = 40000,
            message = "Weight of cargo should be between 5000 and 40000 kg.")
    private int weight;

    private CargoStatus status;

    public CargoDto() {
        status = CargoStatus.PREPARED;
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
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

    @Override
    public String toString() {
        return name + " (" + weight + "kg), " + status.getName();
    }
}
