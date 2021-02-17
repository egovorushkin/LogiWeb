package com.egovorushkin.logiweb.dto;

import java.io.Serializable;

public class CityDto extends AbstractDto implements Serializable {

    private String name;

    public CityDto() {
    }

    public CityDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "CityDto{" +
                "id=" + super.getId() +
                "name='" + name + '\'' +
                '}';
    }
}
