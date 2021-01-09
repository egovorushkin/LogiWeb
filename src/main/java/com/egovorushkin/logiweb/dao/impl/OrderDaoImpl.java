package com.egovorushkin.logiweb.dao.impl;

import com.egovorushkin.logiweb.dao.api.OrderDao;
import com.egovorushkin.logiweb.entities.Order;
import com.egovorushkin.logiweb.entities.WaypointList;
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
        return entityManager.find(Order.class, id);
    }

    public List<Order> listAll() {
        TypedQuery<Order> q = entityManager.createQuery("SELECT o FROM Order o LEFT " +
                "JOIN FETCH " +
                "o.waypointList wList LEFT JOIN FETCH o.truck truck", Order.class);
        return q.getResultList();
    }

    public Order showOrder(int id) {
        TypedQuery<Order> q = entityManager.createQuery("SELECT o FROM Order o WHERE o" +
                ".id=:id", Order.class).setParameter("id", id);
        return q.getSingleResult();
    }

    @Override
    public WaypointList findCurrentWaypointList(int id) {
        TypedQuery<WaypointList> q = entityManager.createQuery("SELECT w FROM WaypointList w WHERE w" +
                ".id=:id", WaypointList.class).setParameter("id", id);
        return q.getSingleResult();
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
