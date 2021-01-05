package com.egovorushkin.logiweb.services.impl;

import com.egovorushkin.logiweb.dao.api.OrderDao;
import com.egovorushkin.logiweb.entities.Order;
import com.egovorushkin.logiweb.services.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public Order getOrderById(int id) {
        return orderDao.getOrderById(id);
    }

    @Override
    public List listAll() {
        return orderDao.listAll();
    }

    @Override
    public Order showOrder(int id) {
        return orderDao.showOrder(id);
    }

    @Override
    @Transactional
    public void saveOrder(Order order) {
        orderDao.saveOrder(order);
    }

    @Override
    @Transactional
    public void update(Order order) {
        orderDao.update(order);
    }

    @Override
    @Transactional
    public void delete(int id) {
        orderDao.delete(id);
    }

}
