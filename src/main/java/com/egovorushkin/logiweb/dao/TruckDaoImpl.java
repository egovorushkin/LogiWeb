package com.egovorushkin.logiweb.dao;

import com.egovorushkin.logiweb.entities.Truck;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository()
public class TruckDaoImpl implements TruckDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Truck getTruckById(int id) {
        Truck truck = entityManager.find(Truck.class, id);
        return truck;
    }

    public List<Truck> listAll() {
        TypedQuery<Truck> q = entityManager.createQuery("SELECT t FROM Truck t", Truck.class);
        List<Truck> trucks = q.getResultList();
        return trucks;
    }

    public Truck showTruck(int id) {
        Truck truck;
        TypedQuery<Truck> q = entityManager.createQuery("SELECT t FROM Truck t WHERE t.id=:id", Truck.class).setParameter("id", id);
        truck = q.getSingleResult();
        return truck;
    }

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

}
