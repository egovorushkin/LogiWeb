package com.egovorushkin.logiweb.dao.impl;

import com.egovorushkin.logiweb.dao.api.CargoDao;
import com.egovorushkin.logiweb.entities.Cargo;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Represent a repository
 * and implements methods to operate for {@link Cargo}
 * extends {@link AbstractDao}
 * implements {@link CargoDao}
 * annotated {@link Repository}
 *
 */
@Repository
public class CargoDaoImpl extends AbstractDao implements CargoDao {

    @Override
    public Cargo getCargoById(Long id) {
        return entityManager.find(Cargo.class, id);
    }

    @Override
    public List<Cargo> getAllCargoes() {
        return entityManager.createQuery("SELECT t FROM Cargo t",
                Cargo.class)
                .getResultList();
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
    public void deleteCargo(Long id) {
        Cargo cargo = entityManager.find(Cargo.class, id);

        if (cargo != null) {
            entityManager.remove(cargo);
        }
    }

    @Override
    public boolean cargoExistsById(Long id) {
        Long count = entityManager.createQuery("SELECT COUNT(c)  FROM Cargo " +
                "c WHERE c.id=:id", Long.class).
                setParameter("id", id).getSingleResult();
        return count > 0;
    }

}
