package com.egovorushkin.logiweb.dao;

import com.egovorushkin.logiweb.entities.Order;

import java.util.List;

public interface OrderDao {

    Order getOrderById(int id);

    List<Order> listAll();

    void saveOrder(Order order);

    void update(Order order);

    void delete(int id);

    Order showOrder(int id);
}
