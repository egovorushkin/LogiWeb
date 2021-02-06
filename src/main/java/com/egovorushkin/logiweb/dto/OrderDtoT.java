package com.egovorushkin.logiweb.dto;

import com.egovorushkin.logiweb.entities.City;
import com.egovorushkin.logiweb.entities.enums.OrderStatus;

import java.time.LocalDateTime;

public class OrderDtoT {

    private long id;
//    private LocalDateTime dateOfCreation;
    private OrderStatus status;
    private City fromCity;
    private City toCity;
    private CargoDto cargo;
    private Integer distance;
    private TruckDto truck;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public LocalDateTime getDateOfCreation() {
//        return dateOfCreation;
//    }
//
//    public void setDateOfCreation(LocalDateTime dateOfCreation) {
//        this.dateOfCreation = dateOfCreation;
//    }
//
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

    public TruckDto getTruck() {
        return truck;
    }

    public void setTruck(TruckDto truck) {
        this.truck = truck;
    }
}
