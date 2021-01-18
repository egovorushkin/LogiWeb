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
    public Cargo getCargoById(long id) {
        return entityManager.find(Cargo.class, id);
    }

    @Override
    public List<Cargo> getAllCargoes() {
        TypedQuery<Cargo> q = entityManager.createQuery("SELECT t FROM Cargo t",
                Cargo.class);
        return q.getResultList();
    }

    @Override
    public void createCargo(Cargo cargo) {
        entityManager.persist(cargo);
    }

    @Override
    public void updateCargo(Cargo cargo) {
        entityManager.merge(cargo);
    }

    @Override
    public void deleteCargo(long id) {
        Cargo cargo = entityManager.find(Cargo.class, id);

        if (cargo != null) {
            entityManager.remove(cargo);
        }
    }

}
