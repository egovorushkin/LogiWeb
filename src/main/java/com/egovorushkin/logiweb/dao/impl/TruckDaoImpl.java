package com.egovorushkin.logiweb.dao.impl;

import com.egovorushkin.logiweb.dao.api.TruckDao;
import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.Truck;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class TruckDaoImpl implements TruckDao {

    private static final Logger LOGGER =
            Logger.getLogger(TruckDaoImpl.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Truck getTruckById(long id) {

        LOGGER.debug("Finding truck by id");

        return entityManager.find(Truck.class, id);
    }

    @Override
    public List<Truck> getAllTrucks() {

        LOGGER.debug("Retrieving all trucks");

        TypedQuery<Truck> q = entityManager.createQuery("SELECT t FROM Truck " +
                "t LEFT JOIN FETCH t.currentCity", Truck.class);

        return q.getResultList();
    }

    @Override
    public void createTruck(Truck truck) {

        LOGGER.debug("Adding new truck");

        entityManager.persist(truck);
    }

    @Override
    public void updateTruck(Truck truck) {
        entityManager.merge(truck);
    }

    @Override
    public void deleteTruck(long id) {

        LOGGER.debug("deleteTruck executed");

        Truck truck = entityManager.find(Truck.class, id);
        List<Driver> currentDrivers = truck.getCurrentDrivers();

        for (Driver d : currentDrivers){
            d.setTruck(null);
        }

        entityManager.remove(truck);
    }

    @Override
    public List<Driver> findCurrentDriversByTruckId(long id) {

        LOGGER.debug("Finding drivers of truck with id = " + id);

        TypedQuery<Driver> q = entityManager.createQuery("SELECT d FROM " +
                "Driver d WHERE d.truck.id=:id", Driver.class)
                .setParameter("id", id);

        return q.getResultList();
    }

    @Override
    public List<Driver> findAvailableDriversByTruck(Truck truck) {

        LOGGER.debug("Finding available drivers for truck with id = " + truck.getId());

        TypedQuery<Driver> q = entityManager.createQuery("SELECT d FROM " +
                "Driver d WHERE d.status='RESTING' AND d" +
                ".currentCity=:truckCurrentCity", Driver.class)
                .setParameter("truckCurrentCity", truck.getCurrentCity());

        return q.getResultList();
    }

}
