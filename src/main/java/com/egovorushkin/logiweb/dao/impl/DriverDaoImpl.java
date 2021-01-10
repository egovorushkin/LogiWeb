package com.egovorushkin.logiweb.dao.impl;

import com.egovorushkin.logiweb.dao.api.DriverDao;
import com.egovorushkin.logiweb.entities.Driver;
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
    public Driver getDriverById(int id) {
        return entityManager.find(Driver.class, id);
    }

    @Override
    public List<Driver> listAll() {
        TypedQuery<Driver> q = entityManager.createQuery("SELECT d FROM " +
                "Driver d " +
                "LEFT JOIN FETCH d.currentCity currentCity LEFT JOIN FETCH d" +
                ".currentTruck currentTruck", Driver.class);

        return q.getResultList();
    }

    @Override
    public void saveDriver(Driver driver) {
        entityManager.persist(driver);
    }

    @Override
    public void update(Driver driver) {
        entityManager.merge(driver);
    }

    @Override
    public void delete(int id) {
        Driver driver = entityManager.find(Driver.class, id);

        if (driver != null) {
            entityManager.remove(driver);
        }
    }

    @Override
    public Driver showDriver(int id) {
        TypedQuery<Driver> q = entityManager.createQuery("SELECT d FROM " +
                "Driver d JOIN FETCH d.currentCity JOIN FETCH d.currentTruck WHERE " +
                "d.id=:id", Driver.class).setParameter("id", id);
        return q.getSingleResult();
    }
}
