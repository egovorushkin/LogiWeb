package com.egovorushkin.logiweb.entities;

import com.egovorushkin.logiweb.entities.status.OrderStatus;

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

    @Column(name = "unique_number")
    private int uniqueNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus orderStatus;

//    private Truck truck;
//
//    private List<Driver> drivers;


    public Order() {
    }

    public Order(int id, int uniqueNumber, OrderStatus orderStatus) {
        this.id = id;
        this.uniqueNumber = uniqueNumber;
        this.orderStatus = orderStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(int uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                uniqueNumber == order.uniqueNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uniqueNumber);
    }
}
