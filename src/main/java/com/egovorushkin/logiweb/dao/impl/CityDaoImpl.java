package com.egovorushkin.logiweb.dao.impl;

import com.egovorushkin.logiweb.dao.api.CityDao;
import com.egovorushkin.logiweb.entities.City;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CityDaoImpl extends AbstractDao implements CityDao {

    @Override
    public City getCityById(Long id) {
        return entityManager.find(City.class, id);
    }

    @Override
    public List<City> getAllCities() {
        return entityManager.createQuery("SELECT c FROM City c " +
                "ORDER BY c.name", City.class)
                .getResultList();
    }

}
