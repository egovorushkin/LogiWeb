package com.egovorushkin.logiweb.dao.impl;

import com.egovorushkin.logiweb.dao.api.CityDao;
import com.egovorushkin.logiweb.entities.City;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository()
public class CityDaoImpl implements CityDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public City getCityById(int id) {
        return entityManager.find(City.class, id);
    }

    @Override
    public List<City> listAll() {
        TypedQuery<City> q = entityManager.createQuery("SELECT c FROM City c", City.class);
        return q.getResultList();
    }

    @Override
    public City showCity(int id) {
        City city;
        TypedQuery<City> q = entityManager.createQuery("SELECT c FROM City c WHERE c.id=:id", City.class).setParameter("id", id);
        city = q.getSingleResult();
        return city;
    }

    @Override
    public void saveCity(City city) {
        entityManager.persist(city);
    }

    @Override
    public void update(City city) {
        entityManager.merge(city);
    }

    @Override
    public void delete(int id) {
        City city = entityManager.find(City.class, id);

        if (city != null) {
            entityManager.remove(city);
        }
    }

}
