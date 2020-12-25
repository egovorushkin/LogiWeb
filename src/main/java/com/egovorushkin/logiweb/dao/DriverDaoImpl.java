package com.egovorushkin.logiweb.dao;

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
        TypedQuery<Driver> q = entityManager.createQuery("SELECT d FROM Driver d", Driver.class);
        List<Driver> drivers = q.getResultList();
        return drivers;
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
        Driver driver;
        TypedQuery<Driver> q = entityManager.createQuery("SELECT d FROM Driver d WHERE d.id=:id", Driver.class).setParameter("id", id);
        driver = q.getSingleResult();
        return driver;
    }
}
