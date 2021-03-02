package com.egovorushkin.logiweb.services.impl;

import com.egovorushkin.logiweb.dao.api.CityDao;
import com.egovorushkin.logiweb.dto.CityDto;
import com.egovorushkin.logiweb.entities.City;
import com.egovorushkin.logiweb.exceptions.EntityNotFoundException;
import com.egovorushkin.logiweb.services.api.CityService;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class with business logics for {@link City}
 * and {@link CityDto}
 * implements {@link CityService}
 */
@Service
public class CityServiceImpl implements CityService {

    private static final Logger LOGGER =
            Logger.getLogger(CityServiceImpl.class.getName());

    private final CityDao cityDao;
    private final Mapper mapper;

    @Autowired
    public CityServiceImpl(CityDao cityDao, Mapper mapper) {
        this.cityDao = cityDao;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public CityDto getCityById(Long id) {

        LOGGER.debug("getCityById() executed");

        if (cityDao.getCityById(id) == null) {
            throw new EntityNotFoundException("City with id = " + id + " is" +
                    " not found");
        }

        LOGGER.info("Found city with id = " + id);

        return mapper.map(cityDao.getCityById(id), CityDto.class);
    }

    @Override
    @Transactional
    public List<CityDto> getAllCities() {

        LOGGER.debug("getAllCities() executed");

        List<City> cities = cityDao.getAllCities();
        return cities.stream()
                .map(city -> mapper.map(city, CityDto.class))
                .collect(Collectors.toList());
    }

}
