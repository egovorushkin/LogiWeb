package com.egovorushkin.logiweb.dao.impl;

import com.egovorushkin.logiweb.dao.api.CargoDao;
import com.egovorushkin.logiweb.entities.Cargo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
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
        return entityManager.createQuery("SELECT c FROM Cargo c",
                Cargo.class).getResultList();
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

    @Override
    public List<Cargo> findAvailableCargoes() {
        return entityManager.createQuery("SELECT c FROM Cargo c " +
                "WHERE c.status='PREPARED'", Cargo.class).getResultList();
    }

    @Override
    public List<Cargo> listAllByPage(int firstResult, int maxResult) {
        TypedQuery<Long> idQuery = entityManager
                .createQuery("SELECT c.id FROM Cargo c ORDER BY c.id",
                        Long.class);

        List<Long> cargoesIds = idQuery.setFirstResult(firstResult)
                .setMaxResults(maxResult).getResultList();

        TypedQuery<Cargo> cargoQuery = entityManager.createQuery("SELECT " +
                        "DISTINCT c FROM Cargo c " +
                        "WHERE c.id IN (:ids)", Cargo.class)
                .setParameter("ids", cargoesIds);

        return cargoQuery.getResultList();
    }

    @Override
    public Long totalCount() {
        return entityManager.createQuery("SELECT COUNT (c.id) FROM Cargo c",
                Long.class).getSingleResult();
    }

}
