package com.egovorushkin.logiweb.services.impl;

import com.egovorushkin.logiweb.dao.api.TruckDao;
import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.TruckDto;
import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.Truck;
import com.egovorushkin.logiweb.exceptions.EntityNotFoundException;
import com.egovorushkin.logiweb.exceptions.ServiceException;
import com.egovorushkin.logiweb.services.api.TruckService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
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

        LOGGER.debug("getTruckById() executed");

        Truck truck = truckDao.getTruckById(id);

        if (truck == null) {
            throw new EntityNotFoundException("Truck with id = " + id + " is " +
                    "not found");
        }

        LOGGER.info("Found the truck with id = " + id);

        return modelMapper.map(truck, TruckDto.class);
    }

    @Override
    @Transactional
    public List<TruckDto> getAllTrucks() {

        LOGGER.debug("getAllTrucks() executed");

        List<Truck> trucks = truckDao.getAllTrucks();

        return trucks.stream()
                .map(truck -> modelMapper.map(truck, TruckDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createTruck(TruckDto truckDto) {

        LOGGER.debug("createTruck() executed");

        if (truckDao.truckExistsByRegistrationNumber(truckDto.getRegistrationNumber())) {
            throw new ServiceException(String.format("Truck with registration" +
                    " number %s already exists",
                    truckDto.getRegistrationNumber()));
        }
        truckDao.createTruck(modelMapper.map(truckDto, Truck.class));

        LOGGER.info("Truck with id = " + truckDto.getId() + " created");
    }

    @Override
    @Transactional
    public void updateTruck(TruckDto truckDto) {

        LOGGER.debug("updateTruck() executed");

        try {
            truckDao.updateTruck(modelMapper.map(truckDto, Truck.class));
        } catch (NoResultException e) {
            throw new EntityNotFoundException(String.format("Truck with registration" +
                    " number %s does not exist",
                    truckDto.getRegistrationNumber()));
        }

        LOGGER.info("Truck with id = " + truckDto.getId() + " updated");
    }

    @Override
    @Transactional
    public void deleteTruck(long id) {

        LOGGER.debug("deleteTruck() executed");

        truckDao.deleteTruck(id);

        LOGGER.info("Truck with id = " + id + " deleted");
    }

    @Override
    @Transactional
    public List<DriverDto> findCurrentDriversByTruckId(long id) {

        LOGGER.debug("findCurrentDriversByTruckId() executed");

        List<Driver> currentDrivers = truckDao.findCurrentDriversByTruckId(id);

        return currentDrivers.stream()
                .map(driver -> modelMapper.map(driver, DriverDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DriverDto> findAvailableDriversByTruck(TruckDto truckDto) {

        LOGGER.debug("findAvailableDriversByTruck() executed");

        List<Driver> availableDrivers =
                truckDao.findAvailableDriversByTruck(modelMapper.map(truckDto
                        , Truck.class));
        return availableDrivers.stream()
                .map(driver -> modelMapper.map(driver, DriverDto.class))
                .collect(Collectors.toList());
    }

}
