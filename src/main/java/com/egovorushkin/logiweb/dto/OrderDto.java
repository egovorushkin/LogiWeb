package com.egovorushkin.logiweb.dto;

import com.egovorushkin.logiweb.entities.City;
import com.egovorushkin.logiweb.entities.enums.OrderStatus;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.util.Objects;

public class OrderDto implements Serializable {

    private long id;
    private OrderStatus status;
    private City fromCity;
    private City toCity;
    private CargoDto cargo;
    @Range(min = 0, max = 10000, message = "Distance should be between 0 and 10000 km.")
    private Integer distance;
    private TruckDto truck;

    public OrderDto() {
        status = OrderStatus.NOT_COMPLETED;
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

    public CargoDto getCargo() {
        return cargo;
    }

    public void setCargo(CargoDto cargo) {
        this.cargo = cargo;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return distance / 80;
    }

    public TruckDto getTruck() {
        return truck;
    }

    public void setTruck(TruckDto truck) {
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
