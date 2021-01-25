package com.egovorushkin.logiweb.services.impl;

import com.egovorushkin.logiweb.config.security.IAuthenticationFacade;
import com.egovorushkin.logiweb.dao.api.DriverDao;
import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.TruckDto;
import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.Truck;
import com.egovorushkin.logiweb.exceptions.EntityNotFoundException;
import com.egovorushkin.logiweb.services.api.DriverService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverServiceImpl implements DriverService {

    private static final Logger LOGGER =
            Logger.getLogger(DriverServiceImpl.class.getName());

    private final DriverDao driverDao;
    private final ModelMapper modelMapper;
    private final IAuthenticationFacade authenticationFacade;

    @Autowired
    public DriverServiceImpl(DriverDao driverDao, ModelMapper modelMapper,
                             IAuthenticationFacade authenticationFacade) {
        this.driverDao = driverDao;
        this.modelMapper = modelMapper;
        this.authenticationFacade = authenticationFacade;
    }

    @Override
    @Transactional
    public DriverDto getDriverById(long id) {

        LOGGER.debug("getDriverById() executed");

        Driver driver = driverDao.getDriverById(id);

        if (driver == null) {
            throw new EntityNotFoundException("Driver with id = " + id + " is" +
                    " not found");
        }
        return modelMapper.map(driverDao.getDriverById(id), DriverDto.class);
    }

    @Override
    @Transactional
    public List<DriverDto> getAllDrivers() {
        List<Driver> drivers = driverDao.getAllDrivers();
        return drivers.stream()
                .map(driver -> modelMapper.map(driver, DriverDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createDriver(DriverDto driverDto) {
        driverDao.saveDriver(modelMapper.map(driverDto, Driver.class));
    }

    @Override
    @Transactional
    public void updateDriver(DriverDto driverDto) {
        driverDao.updateDriver(modelMapper.map(driverDto, Driver.class));
    }

    @Override
    @Transactional
    public void deleteDriver(long id) {
        driverDao.deleteDriver(id);
    }

    @Override
    @Transactional
    public DriverDto getDriverByUsername() {
        Authentication authentication = authenticationFacade.getAuthentication();
        return modelMapper
                .map(driverDao
                        .getDriverByUsername(authentication.getName()), DriverDto.class);
    }

    @Override
    @Transactional
    public List<TruckDto> findAvailableTrucksByDriver(DriverDto driverDto) {
        List<Truck> availableTrucks =
                driverDao.findAvailableTrucksByDriver(modelMapper.map(driverDto
                        , Driver.class));
        return availableTrucks.stream()
                .map(truck -> modelMapper.map(truck, TruckDto.class))
                .collect(Collectors.toList());
    }


}
