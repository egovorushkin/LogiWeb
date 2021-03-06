package com.egovorushkin.logiweb.dao.impl;

import com.egovorushkin.logiweb.dao.api.OrderDao;
import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.Order;
import com.egovorushkin.logiweb.entities.Truck;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Represent a repository
 * and implements methods to operate for {@link Order}
 * extends {@link AbstractDao}
 * implements {@link OrderDao}
 * annotated {@link Repository}
 *
 */
@Repository
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

    @Override
    public List<Order> listAllByPage(int firstResult, int maxResult) {
        TypedQuery<Long> idQuery = entityManager
                .createQuery("SELECT o.id FROM Order o ORDER BY o.id",
                        Long.class);

        List<Long> ordersIds = idQuery.setFirstResult(firstResult)
                .setMaxResults(maxResult).getResultList();

        TypedQuery<Order> orderQuery = entityManager.createQuery("SELECT o " +
                        "FROM Order o " +
                        "LEFT JOIN FETCH o.cargo " +
                        "LEFT JOIN FETCH o.truck " +
                        "WHERE o.id IN (:ids)", Order.class)
                .setParameter("ids", ordersIds);

        return orderQuery.getResultList();
    }

    @Override
    public Long totalCount() {
        return entityManager.createQuery("SELECT COUNT (o.id) " +
                        "FROM Order o", Long.class)
                .getSingleResult();
    }

}
