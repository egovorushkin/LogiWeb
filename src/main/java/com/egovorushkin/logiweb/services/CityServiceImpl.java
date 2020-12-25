package com.egovorushkin.logiweb.services;

import com.egovorushkin.logiweb.dao.CityDao;
import com.egovorushkin.logiweb.entities.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityDao cityDao;

    @Autowired
    public CityServiceImpl(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    @Override
    @Transactional
    public City getCityById(int id) {
        return cityDao.getCityById(id);
    }

    @Override
    @Transactional
    public List listAll() {
        return cityDao.listAll();
    }

    @Override
    public City showCity(int id) {
        return cityDao.showCity(id);
    }

    @Override
    @Transactional
    public void saveCity(City city) {
        cityDao.saveCity(city);
    }

    @Override
    @Transactional
    public void update(City city) {
        cityDao.update(city);
    }

    @Override
    @Transactional
    public void delete(int id) {
        cityDao.delete(id);
    }

}
