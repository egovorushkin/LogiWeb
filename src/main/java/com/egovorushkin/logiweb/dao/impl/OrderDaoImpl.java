package com.egovorushkin.logiweb.dao.impl;

import com.egovorushkin.logiweb.dao.api.OrderDao;
import com.egovorushkin.logiweb.entities.Order;
import com.egovorushkin.logiweb.entities.Truck;
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
    public Order getOrderById(long id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public List<Order> getAllOrders() {
        TypedQuery<Order> q = entityManager.createQuery("SELECT o FROM Order " +
                        "o LEFT JOIN FETCH o.fromCity LEFT JOIN FETCH o" +
                        ".toCity LEFT JOIN FETCH o.cargo LEFT JOIN FETCH o" +
                        ".truck",
                Order.class);
        return q.getResultList();
    }

    @Override
    public void createOrder(Order order) {
        entityManager.persist(order);
    }

    @Override
    public void updateOrder(Order order) {
        entityManager.merge(order);
    }

    @Override
    public void deleteOrder(long id) {
        Order order = entityManager.find(Order.class, id);

        if (order != null) {
            entityManager.remove(order);
        }
    }

    @Override
    public List<Truck> findAvailableTrucks(Order order) {
        TypedQuery<Truck> q = entityManager.createQuery("SELECT t FROM " +
                        "Truck t WHERE t.state='SERVICEABLE' AND t.status='PARKED' " +
                        "AND t.capacity>=:cargoWeight AND t.currentCity.id=:fromCity",
                Truck.class)
                .setParameter("cargoWeight", order.getCargo().getWeight())
                .setParameter("fromCity", order.getFromCity().getId());

        return q.getResultList();
    }

    @Override
    public List<Order> findCurrentOrders(long id) {
        TypedQuery<Order> q = entityManager.createQuery("SELECT o FROM Order o " +
                "WHERE o.truck.id=:id", Order.class).setParameter("id", id);
        return q.getResultList();
    }

}
