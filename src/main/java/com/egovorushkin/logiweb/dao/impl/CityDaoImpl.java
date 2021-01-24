package com.egovorushkin.logiweb.dao.impl;

import com.egovorushkin.logiweb.dao.api.CityDao;
import com.egovorushkin.logiweb.entities.City;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CityDaoImpl implements CityDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public City getCityById(long id) {
        return entityManager.find(City.class, id);
    }

    @Override
    public List<City> getAllCities() {
        TypedQuery<City> q = entityManager.createQuery("SELECT c FROM City c ORDER BY c.name",
                City.class);
        return q.getResultList();
    }

}
