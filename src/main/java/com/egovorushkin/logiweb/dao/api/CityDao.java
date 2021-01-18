package com.egovorushkin.logiweb.dao.api;

import com.egovorushkin.logiweb.entities.City;

import java.util.List;

public interface CityDao {

    City getCityById(long id);

    List<City> getAllCities();

}
