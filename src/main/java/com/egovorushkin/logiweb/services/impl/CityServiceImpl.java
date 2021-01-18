package com.egovorushkin.logiweb.services.impl;

import com.egovorushkin.logiweb.dao.api.CityDao;
import com.egovorushkin.logiweb.dto.CityDto;
import com.egovorushkin.logiweb.entities.City;
import com.egovorushkin.logiweb.services.api.CityService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {

    private static final Logger LOGGER =
            Logger.getLogger(CityServiceImpl.class.getName());

    private final CityDao cityDao;
    private final ModelMapper modelMapper;

    @Autowired
    public CityServiceImpl(CityDao cityDao, ModelMapper modelMapper) {
        this.cityDao = cityDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public CityDto getCityById(long id) {
        return modelMapper.map(cityDao.getCityById(id), CityDto.class);
    }

    @Override
    public List<CityDto> getAllCities() {
        List<City> cities = cityDao.getAllCities();
        return cities.stream()
                .map(city -> modelMapper.map(city, CityDto.class))
                .collect(Collectors.toList());
    }

}
