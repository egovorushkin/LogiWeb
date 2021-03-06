package com.egovorushkin.logiweb.dao.impl;

import com.egovorushkin.logiweb.dao.api.DriverDao;
import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.Truck;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Represent a repository
 * and implements methods to operate for {@link Driver}
 * extends {@link AbstractDao}
 * implements {@link DriverDao}
 * annotated {@link Repository}
 *
 */
@Repository
public class DriverDaoImpl extends AbstractDao implements DriverDao {

    @Override
    public Driver getDriverById(Long id) {
        return entityManager.createQuery("SELECT d FROM Driver d " +
                "LEFT JOIN FETCH d.currentCity " +
                "LEFT JOIN FETCH d.truck " +
                "WHERE d.id=:id", Driver.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<Driver> getAllDrivers() {
        return entityManager.createQuery("SELECT d FROM Driver d " +
                        "LEFT JOIN FETCH d.currentCity " +
                        "LEFT JOIN FETCH d.truck", Driver.class)
                .getResultList();
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
    public void deleteDriver(Long id) {
        Driver driver = entityManager.find(Driver.class, id);

        if (driver != null) {
            entityManager.remove(driver);
        }
    }

    @Override
    public Driver getDriverByUsername(String username) {
        return entityManager.createQuery("SELECT d FROM Driver d " +
                "WHERE d.username=:username", Driver.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public List<Truck> findAvailableTrucksByDriver(Driver driver) {
        return entityManager.createQuery("SELECT t FROM Truck t " +
                        "LEFT JOIN FETCH t.currentCity " +
                        "WHERE t.status='PARKED' " +
                        "AND t.state='SERVICEABLE' " +
                        "AND t.currentCity=:driverCurrentCity", Truck.class)
                .setParameter("driverCurrentCity", driver.getCurrentCity())
                .getResultList();
    }

    @Override
    public boolean driverExistsById(Long id) {
        Long count = entityManager.createQuery("SELECT COUNT(d) FROM Driver " +
                "d WHERE d.id=:id", Long.class).
                setParameter("id", id).getSingleResult();
        return count > 0;
    }

    @Override
    public List<Driver> listAllByPage(int firstResult, int maxResult) {
        TypedQuery<Long> idQuery = entityManager
                .createQuery("SELECT d.id FROM Driver d ORDER BY d.id",
                        Long.class);

        List<Long> driversIds = idQuery.setFirstResult(firstResult - 1)
                .setMaxResults(maxResult).getResultList();

        TypedQuery<Driver> driverQuery = entityManager.createQuery("SELECT " +
                        "DISTINCT d FROM Driver d " +
                        "LEFT JOIN FETCH d.currentCity " +
                        "LEFT JOIN FETCH d.truck " +
                        "WHERE d.id IN (:ids)", Driver.class)
                .setParameter("ids", driversIds);

        return driverQuery.getResultList();
    }

    @Override
    public Long totalCount() {
        return entityManager.createQuery("SELECT COUNT (d.id) FROM Driver d",
                Long.class).getSingleResult();
    }

}
