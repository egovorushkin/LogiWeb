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

        // TODO remove TypedQuery
        TypedQuery<Driver> q = entityManager.createQuery("SELECT d FROM " +
                        "Driver d LEFT JOIN FETCH d.currentCity LEFT JOIN FETCH " +
                        "d.truck WHERE d.id=:id", Driver.class)
                .setParameter("id", id);

        return q.getSingleResult();
    }

    @Override
    public List<Driver> getAllDrivers() {
        // TODO remove TypedQuery
        TypedQuery<Driver> q = entityManager.createQuery("SELECT d FROM " +
                "Driver d LEFT JOIN FETCH d.currentCity LEFT JOIN FETCH d.truck",
                Driver.class);

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
        // TODO remove TypedQuery
        TypedQuery<Driver> q = entityManager.createQuery("SELECT d FROM " +
                "Driver d WHERE d.username=:username", Driver.class).setParameter(
                        "username", username);
        return q.getSingleResult();
    }

    @Override
    public List<Truck> findAvailableTrucksByDriver(Driver driver) {
        // TODO remove TypedQuery
        TypedQuery<Truck> q = entityManager.createQuery("SELECT t FROM " +
                "Truck t LEFT JOIN FETCH t.currentCity WHERE t.status='PARKED' AND t.state='SERVICEABLE' " +
                "AND t.currentCity=:driverCurrentCity",
                Truck.class)
                .setParameter("driverCurrentCity", driver.getCurrentCity());

        return q.getResultList();
    }

    @Override
    public boolean driverExistsById(long id) {
        Long count = entityManager.createQuery("SELECT COUNT(d)  FROM Driver " +
                "d WHERE d.id=:id", Long.class).
                setParameter("id", id).getSingleResult();
        return count > 0;
    }

}
