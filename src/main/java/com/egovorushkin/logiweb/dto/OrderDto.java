package com.egovorushkin.logiweb.dto;

import com.egovorushkin.logiweb.entities.Cargo;
import com.egovorushkin.logiweb.entities.City;
import com.egovorushkin.logiweb.entities.Truck;
import com.egovorushkin.logiweb.entities.enums.OrderStatus;

import java.util.Objects;

public class OrderDto {

    private long id;
    private OrderStatus status;
    private City fromCity;
    private City toCity;
    private Cargo cargo;
    private Integer distance;
    private double duration;
    private Truck truck;

    public OrderDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public City getFromCity() {
        return fromCity;
    }

    public void setFromCity(City fromCity) {
        this.fromCity = fromCity;
    }

    public City getToCity() {
        return toCity;
    }

    public void setToCity(City toCity) {
        this.toCity = toCity;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public double getDuration() {
        return distance / 80;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return id == orderDto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", status=" + status +
                ", fromCity=" + fromCity +
                ", toCity=" + toCity +
                ", cargo=" + cargo +
                ", distance=" + distance +
                ", truck=" + truck +
                '}';
    }
}
