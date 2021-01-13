package com.egovorushkin.logiweb.dao.api;

import com.egovorushkin.logiweb.entities.Order;
import com.egovorushkin.logiweb.entities.Truck;

import java.util.List;

public interface OrderDao {

    Order getOrderById(int id);

    List<Order> listAll();

    void saveOrder(Order order);

    void update(Order order);

    void delete(int id);

    Order showOrder(int id);

    List<Truck> findAvailableTrucks(Order order);

}
