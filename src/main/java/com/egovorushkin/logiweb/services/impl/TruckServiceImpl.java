package com.egovorushkin.logiweb.services.impl;

import com.egovorushkin.logiweb.config.security.IAuthenticationFacade;
import com.egovorushkin.logiweb.dao.api.TruckDao;
import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.TruckDto;
import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.Truck;
import com.egovorushkin.logiweb.services.api.TruckService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TruckServiceImpl implements TruckService {

    private static final Logger LOGGER =
            Logger.getLogger(TruckServiceImpl.class.getName());

    private final TruckDao truckDao;
    private final ModelMapper modelMapper;

    @Autowired
    public TruckServiceImpl(TruckDao truckDao, ModelMapper modelMapper) {
        this.truckDao = truckDao;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public TruckDto getTruckById(long id) {
        return modelMapper.map(truckDao.getTruckById(id), TruckDto.class);
    }

    @Override
    @Transactional
    public List<TruckDto> getAllTrucks() {
        List<Truck> trucks = truckDao.getAllTrucks();
        return trucks.stream()
                .map(truck -> modelMapper.map(truck, TruckDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createTruck(TruckDto truckDto) {
        truckDao.createTruck(modelMapper.map(truckDto, Truck.class));
    }

    @Override
    @Transactional
    public void updateTruck(TruckDto truckDto) {
        truckDao.updateTruck(modelMapper.map(truckDto, Truck.class));
    }

    @Override
    @Transactional
    public void deleteTruck(long id) {
        truckDao.deleteTruck(id);
    }

    @Override
    @Transactional
    public List<DriverDto> findCurrentDriversByTruckId(long id) {
        List<Driver> currentDrivers = truckDao.findCurrentDriversByTruckId(id);
        return currentDrivers.stream()
                .map(driver -> modelMapper.map(driver, DriverDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DriverDto> findAvailableDriversByTruck(TruckDto truckDto) {
        List<Driver> availableDrivers =
                truckDao.findAvailableDriversByTruck(modelMapper.map(truckDto
                        , Truck.class));
        return availableDrivers.stream()
                .map(driver -> modelMapper.map(driver, DriverDto.class))
                .collect(Collectors.toList());
    }

}
