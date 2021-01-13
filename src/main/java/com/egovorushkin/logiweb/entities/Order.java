package com.egovorushkin.logiweb.entities;

import com.egovorushkin.logiweb.entities.enums.OrderStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_city_id")
    private City fromCity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_city_id")
    private City toCity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;

    @Column(name = "distance")
    private Integer distance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "truck_id")
    private Truck truck;

    public Order() {
    }

    public Order(int id, OrderStatus status, City fromCity, City toCity,
                 Cargo cargo, Integer distance, Truck truck) {
        this.id = id;
        this.status = status;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.cargo = cargo;
        this.distance = distance;
        this.truck = truck;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Order{" +
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