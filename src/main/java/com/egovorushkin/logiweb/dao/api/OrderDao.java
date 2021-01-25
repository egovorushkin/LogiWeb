package com.egovorushkin.logiweb.dao.api;

import com.egovorushkin.logiweb.entities.Order;
import com.egovorushkin.logiweb.entities.Truck;

import java.util.List;

public interface OrderDao {

    Order getOrderById(long id);

    List<Order> getAllOrders();

    void createOrder(Order order);

    void updateOrder(Order order);

    void deleteOrder(long id);

    List<Truck> findAvailableTrucks(Order order);

    List<Order> findCurrentOrdersForTruck(long id);

}
