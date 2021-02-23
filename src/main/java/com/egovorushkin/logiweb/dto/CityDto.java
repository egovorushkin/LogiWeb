package com.egovorushkin.logiweb.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represent a city
 * extends {@link AbstractDto}
 * implements {@link Serializable}
 */
public class CityDto extends AbstractDto implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CityDto cityDto = (CityDto) o;

        return Objects.equals(name, cityDto.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CityDto{" +
                "id=" + super.getId() +
                "name='" + name + '\'' +
                '}';
    }
}
