package com.egovorushkin.logiweb.dao.impl;

import com.egovorushkin.logiweb.dao.api.TruckDao;
import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.Truck;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

/**
 * Represent a repository
 * and implements methods to operate for {@link Truck}
 * extends {@link AbstractDao}
 * implements {@link TruckDao}
 * annotated {@link Repository}
 *
 */
@Repository
public class TruckDaoImpl extends AbstractDao implements TruckDao {

    private static final Logger LOGGER =
            Logger.getLogger(TruckDaoImpl.class.getName());

    @Override
    public Truck getTruckById(Long id) {
        return entityManager
                .createQuery("SELECT t FROM Truck t " +
                                "LEFT JOIN FETCH t.drivers " +
                                "LEFT JOIN FETCH t.orders " +
                                "LEFT JOIN FETCH t.currentCity WHERE t.id=:id",
                        Truck.class)
                .setParameter("id", id).getSingleResult();
    }

    @Override
    public List<Truck> getAllTrucks() {
        return entityManager
                .createQuery("SELECT t FROM Truck " +
                "t LEFT JOIN FETCH t.currentCity", Truck.class)
                .getResultList();
    }

    @Override
    public void createTruck(Truck truck) {
        entityManager.persist(truck);
    }

    @Override
    public void updateTruck(Truck truck) {

        entityManager.merge(truck);
    }

    @Override
    public void deleteTruck(Long id) {
        Truck truck = entityManager.find(Truck.class, id);
        Set<Driver> currentDrivers = truck.getDrivers();

        for (Driver d : currentDrivers) {
            d.setTruck(null);
        }

        entityManager.remove(truck);
    }

    @Override
    public List<Driver> findAvailableDriversByTruck(Truck truck) {
        return entityManager
                .createQuery("SELECT d FROM Driver d " +
                        "LEFT JOIN FETCH d.truck " +
                        "LEFT JOIN FETCH d.currentCity " +
                        "WHERE d.status='RESTING' " +
                        "AND d.isInShift=false " +
                        "AND d.currentCity=:truckCurrentCity " +
                        "AND (d.truck.id IS NULL " +
                        "OR d.truck.id<>:truckId)", Driver.class)
                .setParameter("truckCurrentCity", truck.getCurrentCity())
                .setParameter("truckId", truck.getId())
                .getResultList();
    }

    @Override
    public boolean truckExistsByRegistrationNumber(String registrationNumber) {
        return entityManager
                .createQuery("SELECT COUNT(t)  FROM Truck t " +
                        "WHERE t.registrationNumber=:registrationNumber",
                        Long.class).
                setParameter("registrationNumber", registrationNumber)
                .getSingleResult() > 0;
    }

    @Override
    public Truck findByRegistrationNumber(String registrationNumber) {
        TypedQuery<Truck> q = entityManager.createQuery("SELECT t " +
                "FROM Truck t " +
                "LEFT JOIN FETCH t.drivers " +
                "LEFT JOIN FETCH t.orders " +
                "LEFT JOIN FETCH t.currentCity " +
                "WHERE t.registrationNumber=:registrationNumber", Truck.class)
                .setParameter("registrationNumber", registrationNumber);
        Truck truck = null;
        try {
            truck = q.getSingleResult();
        } catch (NoResultException ignored) {
            LOGGER.info("Truck with registration number " + registrationNumber +
                    " not found");
        }

        return truck;
    }

    @Override
    public List<Truck> listAllByPage(int firstResult, int maxResult) {
        TypedQuery<Long> idQuery = entityManager.createQuery("SELECT t.id " +
                        "FROM Truck t ORDER BY t.id", Long.class);

        List<Long> trucksIds = idQuery.setFirstResult(firstResult)
                .setMaxResults(maxResult).getResultList();

        TypedQuery<Truck> truckQuery = entityManager.createQuery("SELECT " +
                        "DISTINCT t FROM Truck t " +
                        "LEFT JOIN FETCH t.drivers " +
                        "LEFT JOIN FETCH t.orders " +
                        "LEFT JOIN FETCH t.currentCity WHERE t.id IN (:ids)",
                Truck.class)
                .setParameter("ids", trucksIds);

        return truckQuery.getResultList();
    }

    @Override
    public Long totalCount() {
        return entityManager.createQuery("SELECT COUNT (t.id) " +
                        "FROM Truck t", Long.class)
                .getSingleResult();
    }

}
