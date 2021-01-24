package com.egovorushkin.logiweb.dao.impl;

import com.egovorushkin.logiweb.dao.api.DriverDao;
import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.Truck;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class DriverDaoImpl implements DriverDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Driver getDriverById(long id) {
        return entityManager.find(Driver.class, id);
    }

    @Override
    public List<Driver> getAllDrivers() {
        TypedQuery<Driver> q = entityManager.createQuery("SELECT d FROM " +
                "Driver d " +
                "LEFT JOIN FETCH d.currentCity currentCity LEFT JOIN FETCH d" +
                ".truck currentTruck", Driver.class);

        return q.getResultList();
    }

    @Override
    public void saveDriver(Driver driver) {
        entityManager.persist(driver);
    }

    @Override
    public void updateDriver(Driver driver) {
        entityManager.merge(driver);
    }

    @Override
    public void deleteDriver(long id) {
        Driver driver = entityManager.find(Driver.class, id);

        if (driver != null) {
            entityManager.remove(driver);
        }
    }

    @Override
    public Driver getDriverByUsername(String username) {
        TypedQuery<Driver> q = entityManager.createQuery("SELECT d FROM " +
                "Driver d " +
                "WHERE d.username=:username", Driver.class).setParameter(
                        "username", username);
        return q.getSingleResult();
    }

    @Override
    public List<Truck> findAvailableTrucksByDriver(Driver driver) {

        TypedQuery<Truck> q = entityManager.createQuery("SELECT t FROM " +
                "Truck t WHERE t.status='PARKED' AND t.state='SERVICEABLE' " +
                "AND t.currentCity=:driverCurrentCity",
                Truck.class)
                .setParameter("driverCurrentCity", driver.getCurrentCity());

        return q.getResultList();
    }

}
