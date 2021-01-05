package com.egovorushkin.logiweb.dao.impl;

import com.egovorushkin.logiweb.dao.api.OrderDao;
import com.egovorushkin.logiweb.entities.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository()
public class OrderDaoImpl implements OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order getOrderById(int id) {
        Order order = entityManager.find(Order.class, id);
        return order;
    }

    public List<Order> listAll() {
        TypedQuery<Order> q = entityManager.createQuery("SELECT o FROM Order o", Order.class);
        List<Order> orders = q.getResultList();
        return orders;
    }

    public Order showOrder(int id) {
        Order order;
        TypedQuery<Order> q = entityManager.createQuery("SELECT o FROM Order o WHERE o.id=:id", Order.class).setParameter("id", id);
        order = q.getSingleResult();
        return order;
    }

    public void saveOrder(Order order) {
        entityManager.persist(order);
    }

    @Override
    public void update(Order order) {
        entityManager.merge(order);
    }

    @Override
    public void delete(int id) {
        Order order = entityManager.find(Order.class, id);

        if (order != null) {
            entityManager.remove(order);
        }
    }

}
