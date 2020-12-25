package com.egovorushkin.logiweb.dao;

import com.egovorushkin.logiweb.entities.City;

import java.util.List;

public interface CityDao {

    City getCityById(int id);

    List<City> listAll();

    void saveCity(City city);

    void update(City city);

    void delete(int id);

    City showCity(int id);
}
