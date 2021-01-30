package com.egovorushkin.logiweb.services.impl;

import com.egovorushkin.logiweb.config.security.IAuthenticationFacade;
import com.egovorushkin.logiweb.dao.api.DriverDao;
import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.TruckDto;
import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.Truck;
import com.egovorushkin.logiweb.entities.enums.DriverStatus;
import com.egovorushkin.logiweb.exceptions.EntityNotFoundException;
import com.egovorushkin.logiweb.exceptions.ServiceException;
import com.egovorushkin.logiweb.services.api.DriverService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverServiceImpl implements DriverService {

    private static final String DRIVER = "Driver with id = ";
    private static final String CHANGE_STATUS = "change status for ";

    private static final Logger LOGGER =
            Logger.getLogger(DriverServiceImpl.class.getName());

    private final DriverDao driverDao;
    private final ModelMapper modelMapper;
    private final IAuthenticationFacade authenticationFacade;
    private Authentication authentication;

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

        if (driverDao.getDriverById(id) == null) {
            throw new EntityNotFoundException(DRIVER + id + " is" +
                    " not found");
        }

        LOGGER.info("Found driver with id = " + id);

        return modelMapper.map(driverDao.getDriverById(id), DriverDto.class);
    }

    @Override
    @Transactional
    public List<DriverDto> getAllDrivers() {

        LOGGER.debug("getAllDrivers() executed");

        List<Driver> drivers = driverDao.getAllDrivers();
        return drivers.stream()
                .map(driver -> modelMapper.map(driver, DriverDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createDriver(DriverDto driverDto) {

        LOGGER.debug("createDriver() executed");

        if (driverDao.driverExistsById(driverDto.getId())) {
            throw new ServiceException(String.format("Driver with id" +
                    " %s already exists", driverDto.getId()));
        }
        driverDao.saveDriver(modelMapper.map(driverDto, Driver.class));

        LOGGER.info(DRIVER + driverDto.getId() + " created");
    }

    @Override
    @Transactional
    public void updateDriver(DriverDto driverDto) {

        LOGGER.debug("updateDriver() executed");

        try {
            driverDao.updateDriver(modelMapper.map(driverDto, Driver.class));
        } catch (NoResultException e) {
            throw new EntityNotFoundException(String.format(DRIVER +
                    "%s does not exist", driverDto.getId()));
        }

        LOGGER.info(DRIVER + driverDto.getId() + " updated");
    }

    @Override
    @Transactional
    public void deleteDriver(long id) {

        LOGGER.debug("deleteDriver() executed");

        driverDao.deleteDriver(id);

        LOGGER.info(DRIVER + id + " deleted");
    }

    @Override
    @Transactional
    public DriverDto getAuthorizedDriverByUsername() {

        LOGGER.debug("getAuthorizedDriverByUsername() executed");

        authentication = authenticationFacade.getAuthentication();

        LOGGER.info("Authorized driver with username = " +
                authentication.getName() + " found");
        return modelMapper
                .map(driverDao
                                .getDriverByUsername(authentication.getName()),
                        DriverDto.class);
    }

    @Override
    @Transactional
    public List<TruckDto> findAvailableTrucksByDriver(DriverDto driverDto) {

        LOGGER.debug("findAvailableTrucksByDriver() executed");

        List<Truck> availableTrucks =
                driverDao.findAvailableTrucksByDriver(modelMapper.map(driverDto
                        , Driver.class));

        LOGGER.info("Available trucks found");

        return availableTrucks.stream()
                .map(truck -> modelMapper.map(truck, TruckDto.class))
                .collect(Collectors.toList());
    }

    // TODO need to complete this method if colleague not found
    @Override
    @Transactional
    public DriverDto findColleagueAuthorizedDriverByUsername() {

        LOGGER.debug("findColleagueAuthorizedDriverByUsername() executed");

        authentication = authenticationFacade.getAuthentication();

        DriverDto authorizedDriver = getAuthorizedDriverByUsername();

        if (authorizedDriver.getTruck() == null) {
            return new DriverDto();
        }
        List<DriverDto> listOfColleagues =
                new ArrayList<>(authorizedDriver.getTruck().getCurrentDrivers());
        DriverDto colleague = listOfColleagues.stream()
                .filter(driver -> driver.getId() != authorizedDriver.getId())
                .findFirst().orElse(null);

        LOGGER.info("Colleague of Authorized driver found");

        return colleague;

    }

    @Override
    @Transactional
    public void updateStatus(DriverDto driverDto) {

        LOGGER.debug("updateStatus() executed");

        final DriverDto existingDriver =
                modelMapper.map(driverDao.getDriverById(driverDto.getId()),
                        DriverDto.class);

        DriverDto colleague = findColleagueAuthorizedDriverByUsername();

        switch (driverDto.getStatus().getName()) {
            case "DRIVING":
                existingDriver.setStatus(driverDto.getStatus());
                driverDao.updateDriver(modelMapper.map(existingDriver, Driver.class));

                LOGGER.info(DRIVER + driverDto.getId() + CHANGE_STATUS
                        + driverDto.getStatus().getTitle());

                if (colleague != null) {
                    colleague.setStatus(DriverStatus.SECOND_DRIVER);
                    driverDao.updateDriver(modelMapper.map(colleague, Driver.class));

                    LOGGER.info(DRIVER + colleague.getId() + CHANGE_STATUS
                            + DriverStatus.SECOND_DRIVER);
                }
                break;
            case "SECOND_DRIVER":
                existingDriver.setStatus(driverDto.getStatus());
                driverDao.updateDriver(modelMapper.map(existingDriver, Driver.class));

                LOGGER.info(DRIVER + driverDto.getId() + CHANGE_STATUS
                        + driverDto.getStatus().getTitle());

                if (colleague != null) {
                    colleague.setStatus(DriverStatus.DRIVING);
                    driverDao.updateDriver(modelMapper.map(colleague, Driver.class));

                    LOGGER.info(DRIVER + colleague.getId() + CHANGE_STATUS
                            + DriverStatus.DRIVING);
                }
                break;
            case "LOADING_UNLOADING":
                existingDriver.setStatus(driverDto.getStatus());
                driverDao.updateDriver(modelMapper.map(existingDriver, Driver.class));

                LOGGER.info(DRIVER + driverDto.getId() + CHANGE_STATUS
                        + driverDto.getStatus().getTitle());

                if (colleague != null) {
                    colleague.setStatus(DriverStatus.LOADING_UNLOADING);
                    driverDao.updateDriver(modelMapper.map(colleague, Driver.class));

                    LOGGER.info(DRIVER + colleague.getId() + CHANGE_STATUS
                            + DriverStatus.LOADING_UNLOADING);
                }
                break;
            default:
                // for "RESTING" status
                existingDriver.setStatus(driverDto.getStatus());
                driverDao.updateDriver(modelMapper.map(existingDriver, Driver.class));

                LOGGER.info(DRIVER + driverDto.getId() + CHANGE_STATUS
                        + driverDto.getStatus().getTitle());

                if (colleague != null) {
                    colleague.setStatus(DriverStatus.RESTING);
                    driverDao.updateDriver(modelMapper.map(colleague, Driver.class));

                    LOGGER.info(DRIVER + colleague.getId() + CHANGE_STATUS
                            + DriverStatus.RESTING);
                }
                break;
        }
    }

    @Override
    @Transactional
    public void updateState(DriverDto driverDto) {

        LOGGER.debug("updateState() executed");

        final DriverDto existingDriver =
                modelMapper.map(driverDao.getDriverById(driverDto.getId()),
                        DriverDto.class);
        DriverDto colleague = findColleagueAuthorizedDriverByUsername();

        if (!existingDriver.getTruck().getCurrentOrders().isEmpty()) {
            if (colleague != null) {
                colleague.setInShift(true);
            }
            existingDriver.setInShift(true);
        }

        driverDao.updateDriver(modelMapper.map(existingDriver, Driver.class));

        LOGGER.info("For " + DRIVER + driverDto.getId() + "updated status = "
                + driverDto.getStatus().getName());

    }

}
