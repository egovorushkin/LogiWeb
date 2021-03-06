package com.egovorushkin.logiweb.dao.api;

import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.Order;
import com.egovorushkin.logiweb.entities.Truck;

import java.util.List;

/**
 * This interface represent some methods to operate on a {@link Order}
 * (retrieve from and save to database)
 */
public interface OrderDao {

    Order getOrderById(Long id);

    List<Order> getAllOrders();

    void createOrder(Order order);

    void updateOrder(Order order);

    void deleteOrder(Long id);

    List<Truck> findAvailableTrucks(Order order);

    List<Order> findCurrentOrdersForTruck(Long id);

    List<Driver> findAvailableDriversForOrder(Order order);

    List<Order> getLatestOrders();

    Order findOrderByTruckId(Long id);

    List<Order> listAllByPage(int firstResult, int maxResult);

    Long totalCount();
}
