package com.egovorushkin.logiweb.dao.impl;

import com.egovorushkin.logiweb.dao.api.OrderDao;
import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.Order;
import com.egovorushkin.logiweb.entities.Truck;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository()
public class OrderDaoImpl extends AbstractDao implements OrderDao {

    @Override
    public Order getOrderById(Long id) {
        return entityManager.createQuery("SELECT o FROM Order o " +
                "LEFT JOIN FETCH o.cargo " +
                "LEFT JOIN FETCH o.truck " +
                "WHERE o.id=:id", Order.class).setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<Order> getAllOrders() {
        return entityManager.createQuery("SELECT o FROM Order o " +
                        "LEFT JOIN FETCH o.cargo " +
                        "LEFT JOIN FETCH o.truck", Order.class)
                .getResultList();
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
    public void deleteOrder(Long id) {
        Order order = entityManager.find(Order.class, id);

        if (order != null) {
            entityManager.remove(order);
        }
    }

    @Override
    public List<Truck> findAvailableTrucks(Order order) {
        return entityManager.createQuery("SELECT t FROM Truck t " +
                        "WHERE t.state='SERVICEABLE' " +
                        "AND t.status='PARKED' " +
                        "AND t.capacity>=:cargoWeight " +
                        "AND t.currentCity.name=:fromCity " +
                        "AND t.isBusy=false", Truck.class)
                .setParameter("cargoWeight", order.getCargo().getWeight())
                .setParameter("fromCity", order.getFromCity())
                .getResultList();
    }

    @Override
    public List<Order> findCurrentOrdersForTruck(Long id) {
        return entityManager.createQuery("SELECT o FROM Order o " +
                    "LEFT JOIN FETCH o.cargo " +
                    "LEFT JOIN FETCH o.truck " +
                    "WHERE o.truck.id=:id", Order.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public List<Driver> findAvailableDriversForOrder(Order order) {
        return entityManager.createQuery("SELECT d FROM Driver d " +
                    "LEFT JOIN FETCH d.currentCity " +
                    "LEFT JOIN FETCH d.truck " +
                    "WHERE d.status='RESTING' AND d.currentCity=:truckCurrentCity",
                Driver.class)
                .setParameter("truckCurrentCity",
                        order.getTruck().getCurrentCity())
                .getResultList();
    }

    @Override
    public List<Order> getLatestOrders() {
        return entityManager.createQuery("SELECT o FROM Order o " +
                        "LEFT JOIN FETCH o.cargo " +
                        "LEFT JOIN FETCH o.truck " +
                        "ORDER BY o.id DESC",
                Order.class).setMaxResults(12)
                .getResultList();
    }

    @Override
    public Order findOrderByTruckId(Long id) {
        return entityManager.createQuery("SELECT o FROM Order o " +
                        "LEFT JOIN FETCH o.cargo " +
                        "LEFT JOIN FETCH o.truck " +
                        "WHERE o.truck.id=:id", Order.class)
                .setParameter("id", id)
                .getSingleResult();
    }

}
