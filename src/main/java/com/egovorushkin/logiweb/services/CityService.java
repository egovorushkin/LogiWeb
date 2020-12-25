package com.egovorushkin.logiweb.services;

import com.egovorushkin.logiweb.entities.City;

import java.util.List;

public interface CityService {

    City getCityById(int id);

    List<City> listAll();

    void saveCity(City city);

    void update(City city);

    void delete(int id);

    City showCity(int id);
}
