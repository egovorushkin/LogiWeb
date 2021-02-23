package com.egovorushkin.logiweb.dto;

import com.egovorushkin.logiweb.entities.enums.CargoStatus;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represent a cargo
 * extends {@link AbstractDto}
 * implements {@link Serializable}
 */
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CargoDto cargoDto = (CargoDto) o;

        if (weight != cargoDto.weight) return false;
        return Objects.equals(name, cargoDto.name);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + weight;
        return result;
    }

    @Override
    public String toString() {
        return name + " (" + weight + "kg), " + status.getName();
    }
}
