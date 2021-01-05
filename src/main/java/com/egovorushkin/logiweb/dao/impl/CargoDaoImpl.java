package com.egovorushkin.logiweb.dao.impl;

import com.egovorushkin.logiweb.dao.api.CargoDao;
import com.egovorushkin.logiweb.entities.Cargo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CargoDaoImpl implements CargoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Cargo getCargoById(int id) {
        return entityManager.find(Cargo.class, id);
    }

    @Override
    public List<Cargo> listAll() {
        TypedQuery<Cargo> q = entityManager.createQuery("SELECT t FROM Cargo t", Cargo.class);
        return q.getResultList();
    }

    @Override
    public Cargo showCargo(int id) {
        Cargo cargo;
        TypedQuery<Cargo> q = entityManager.createQuery("SELECT c FROM Cargo c WHERE c.id=:id", Cargo.class).setParameter("id", id);
        cargo = q.getSingleResult();
        return cargo;
    }

    @Override
    public void saveCargo(Cargo cargo) {
        entityManager.persist(cargo);
    }

    @Override
    public void update(Cargo cargo) {
        entityManager.merge(cargo);
    }

    @Override
    public void delete(int id) {
        Cargo cargo = entityManager.find(Cargo.class, id);

        if (cargo != null) {
            entityManager.remove(cargo);
        }
    }

}
