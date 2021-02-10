package com.egovorushkin.logiweb.dao.impl;

import com.egovorushkin.logiweb.dao.api.TruckDao;
import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.Truck;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Repository
public class TruckDaoImpl implements TruckDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Truck getTruckById(long id) {
        return entityManager
                .createQuery("SELECT t FROM Truck t " +
                                "LEFT JOIN FETCH t.currentDrivers " +
                                "LEFT JOIN FETCH t.currentOrders " +
                                "LEFT JOIN FETCH t.currentCity WHERE t.id=:id",
                        Truck.class)
                .setParameter("id", id).getSingleResult();
    }

    @Override
    public List<Truck> getAllTrucks() {
        return entityManager
                .createQuery("SELECT t FROM Truck " +
                "t LEFT JOIN FETCH t.currentDrivers LEFT JOIN FETCH t" +
                ".currentOrders LEFT JOIN FETCH t.currentCity", Truck.class)
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
    public void deleteTruck(long id) {
        Truck truck = entityManager.find(Truck.class, id);
        Set<Driver> currentDrivers = truck.getCurrentDrivers();

        for (Driver d : currentDrivers) {
            d.setTruck(null);
        }

        entityManager.remove(truck);
    }

    @Override
    public List<Driver> findCurrentDriversByTruckId(long id) {
        return entityManager
                .createQuery("SELECT d FROM Driver d " +
                        "LEFT JOIN FETCH d.truck " +
                        "LEFT JOIN FETCH d.currentCity WHERE d.truck.id=:id",
                        Driver.class)
                .setParameter("id", id).getResultList();
    }

    @Override
    public List<Driver> findAvailableDriversByTruck(Truck truck) {
        return entityManager
                .createQuery("SELECT d FROM Driver d " +
                        "LEFT JOIN FETCH d.truck " +
                        "LEFT JOIN FETCH d.currentCity WHERE d.status='RESTING' " +
                        "AND d.currentCity=:truckCurrentCity", Driver.class)
                .setParameter("truckCurrentCity", truck.getCurrentCity())
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

}
