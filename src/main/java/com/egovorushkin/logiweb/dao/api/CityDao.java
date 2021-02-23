package com.egovorushkin.logiweb.dao.api;

import com.egovorushkin.logiweb.entities.City;

import java.util.List;

/**
 * This interface represent some methods to operate on a {@link City}
 * (retrieve from and save to database)
 */
public interface CityDao {

    City getCityById(Long id);

    List<City> getAllCities();

}
