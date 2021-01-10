package com.egovorushkin.logiweb.dao.impl;

import com.egovorushkin.logiweb.dao.api.TruckDao;
import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.Truck;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class TruckDaoImpl implements TruckDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Truck getTruckById(int id) {
        return entityManager.find(Truck.class, id);
    }

    @Override
    public List<Truck> listAll() {
        TypedQuery<Truck> q = entityManager.createQuery("SELECT t FROM Truck " +
                "t " +
                "JOIN FETCH t.currentCity", Truck.class);
        return q.getResultList();
    }

    @Override
    public Truck showTruck(int id) {
        TypedQuery<Truck> q = entityManager.createQuery("SELECT t FROM Truck " +
                "t " +
                "JOIN FETCH t.currentCity WHERE t.id=:id", Truck.class).setParameter("id", id);
        return q.getSingleResult();
    }

    @Override
    public void saveTruck(Truck truck) {
        entityManager.persist(truck);
    }

    @Override
    public void update(Truck truck) {
        entityManager.merge(truck);
    }

    @Override
    public void delete(int id) {
        Truck truck = entityManager.find(Truck.class, id);

        if (truck != null) {
            entityManager.remove(truck);
        }
    }

    @Override
    public List<Driver> findCurrentDrivers(int id) {
        TypedQuery<Driver> q = entityManager.createQuery("SELECT d FROM " +
                "Driver d " +
                "WHERE d.currentTruck.id=:id", Driver.class).setParameter("id"
                , id);

        return q.getResultList();
    }

}
