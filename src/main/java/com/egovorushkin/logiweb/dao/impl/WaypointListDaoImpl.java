package com.egovorushkin.logiweb.dao.impl;

import com.egovorushkin.logiweb.dao.api.WaypointListDao;
import com.egovorushkin.logiweb.entities.WaypointList;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class WaypointListDaoImpl implements WaypointListDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public WaypointList getWaypointListById(int id) {
        return entityManager.find(WaypointList.class, id);
    }

    @Override
    public List<WaypointList> listAll() {
        TypedQuery<WaypointList> q = entityManager.createQuery("SELECT w FROM WaypointList w " +
                "LEFT JOIN FETCH w.fromCity fromCity LEFT JOIN FETCH w.toCity toCity LEFT JOIN FETCH w.cargo cargo", WaypointList.class);

        return q.getResultList();
    }

    @Override
    public WaypointList showWaypointList(int id) {
        WaypointList waypointList;
        TypedQuery<WaypointList> q = entityManager.createQuery("SELECT t FROM WaypointList t WHERE t.id=:id", WaypointList.class).setParameter("id", id);
        waypointList = q.getSingleResult();
        return waypointList;
    }

    @Override
    public void saveWaypointList(WaypointList waypointList) {
        entityManager.persist(waypointList);
    }

    @Override
    public void update(WaypointList waypointList) {
        entityManager.merge(waypointList);
    }

    @Override
    public void delete(int id) {
        WaypointList waypointList = entityManager.find(WaypointList.class, id);

        if (waypointList != null) {
            entityManager.remove(waypointList);
        }
    }

}
