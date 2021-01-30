package com.egovorushkin.logiweb.services.impl;

import com.egovorushkin.logiweb.config.security.IAuthenticationFacade;
import com.egovorushkin.logiweb.dao.api.DriverDao;
import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.TruckDto;
import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.Truck;
import com.egovorushkin.logiweb.entities.enums.DriverStatus;
import com.egovorushkin.logiweb.entities.enums.TruckStatus;
import com.egovorushkin.logiweb.exceptions.EntityNotFoundException;
import com.egovorushkin.logiweb.exceptions.ServiceException;
import com.egovorushkin.logiweb.services.api.DriverService;
import com.egovorushkin.logiweb.services.api.TruckService;
import org.apache.log4j.Logger;
import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverServiceImpl implements DriverService {

    private static final String DRIVER = "Driver with id = ";
    private static final String UPDATE_STATUS = " updated status for ";
    private static final String UPDATE_STATE = " updated state inShift = ";

    private static final Logger LOGGER =
            Logger.getLogger(DriverServiceImpl.class.getName());

    private final DriverDao driverDao;
    private final TruckService truckService;
    private final ModelMapper modelMapper;
    private final IAuthenticationFacade authenticationFacade;

    @Autowired
    public DriverServiceImpl(DriverDao driverDao,
                             TruckService truckService,
                             ModelMapper modelMapper,
                             IAuthenticationFacade authenticationFacade) {
        this.driverDao = driverDao;
        this.truckService = truckService;
        this.modelMapper = modelMapper;
        this.authenticationFacade = authenticationFacade;

        modelMapper.getConfiguration()
                .setPropertyCondition(context ->
                        !(context.getSource() instanceof PersistentCollection));
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

        Authentication authentication =
                authenticationFacade.getAuthentication();

        LOGGER.info("Authorized driver with username = " +
                authentication.getName() + " found");
        return modelMapper
                .map(driverDao.getDriverByUsername(authentication.getName()),
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

    @Override
    @Transactional
    public DriverDto findColleagueAuthorizedDriverByUsername() {

        LOGGER.debug("findColleagueAuthorizedDriverByUsername() executed");

        DriverDto authorizedDriver = getAuthorizedDriverByUsername();

        if (authorizedDriver.getTruck() == null) {
            return null;
        }

        List<DriverDto> currentDrivers = truckService
                .findCurrentDriversByTruckId(authorizedDriver.getTruck().getId());

        DriverDto colleague = currentDrivers.stream()
                .filter(driver -> driver.getId() != authorizedDriver.getId())
                .findFirst().orElse(null);

        if (colleague == null) {
            LOGGER.info("Colleague of Authorized driver is not found");
            return null;
        }
        return colleague;
    }

    @Override
    @Transactional
    public void updateStatus(DriverStatus driverStatus) {

        LOGGER.debug("updateStatus() executed");

        final DriverDto existingDriver = getAuthorizedDriverByUsername();

        DriverDto colleague = findColleagueAuthorizedDriverByUsername();

        switch (driverStatus.getTitle()) {
            case "DRIVING":
                if (existingDriver.getTruck() != null) {
                    existingDriver.setStatus(driverStatus);
                    existingDriver.getTruck().setStatus(TruckStatus.ON_THE_WAY);
                    driverDao.updateDriver(modelMapper.map(existingDriver,
                            Driver.class));
                    truckService.updateTruck(existingDriver.getTruck());

                    LOGGER.info(DRIVER + existingDriver.getId() + UPDATE_STATUS
                            + existingDriver.getStatus().getName());
                    LOGGER.info("Truck with id = " + existingDriver.getTruck().getId() +
                            " update status to " + existingDriver.getTruck().getStatus());
                }


                if (colleague != null) {
                    colleague.setStatus(DriverStatus.SECOND_DRIVER);
                    driverDao.updateDriver(modelMapper.map(colleague,
                            Driver.class));

                    LOGGER.info(DRIVER + colleague.getId() + UPDATE_STATUS
                            + DriverStatus.SECOND_DRIVER.getName());
                }
                break;
            case "SECOND_DRIVER":
                existingDriver.setStatus(driverStatus);
                driverDao.updateDriver(modelMapper.map(existingDriver,
                        Driver.class));

                LOGGER.info(DRIVER + existingDriver.getId() + UPDATE_STATUS
                        + existingDriver.getStatus().getName());

                if (colleague != null) {
                    colleague.setStatus(DriverStatus.DRIVING);
                    driverDao.updateDriver(modelMapper.map(colleague,
                            Driver.class));

                    LOGGER.info(DRIVER + colleague.getId() + UPDATE_STATUS
                            + DriverStatus.DRIVING.getName());
                }
                break;
            case "LOADING_UNLOADING":
                existingDriver.setStatus(driverStatus);
                driverDao.updateDriver(modelMapper.map(existingDriver,
                        Driver.class));

                LOGGER.info(DRIVER + existingDriver.getId() + UPDATE_STATUS
                        + existingDriver.getStatus().getName());

                if (colleague != null) {
                    colleague.setStatus(DriverStatus.LOADING_UNLOADING);
                    driverDao.updateDriver(modelMapper.map(colleague,
                            Driver.class));

                    LOGGER.info(DRIVER + colleague.getId() + UPDATE_STATUS
                            + DriverStatus.LOADING_UNLOADING.getName());
                }
                break;

            case "RESTING":
                if (existingDriver.getTruck() != null) {
                    existingDriver.setStatus(driverStatus);
                    existingDriver.getTruck().setStatus(TruckStatus.PARKED);
                    driverDao.updateDriver(modelMapper.map(existingDriver,
                            Driver.class));
                    truckService.updateTruck(existingDriver.getTruck());

                    LOGGER.info(DRIVER + existingDriver.getId() + UPDATE_STATUS
                            + existingDriver.getStatus().getName());
                    LOGGER.info("Truck with id" + existingDriver.getTruck().getId() +
                            " update status to " + existingDriver.getTruck().getStatus());
                }

                if (colleague != null) {
                    colleague.setStatus(DriverStatus.RESTING);
                    colleague.setInShift(false);
                    driverDao.updateDriver(modelMapper.map(colleague,
                            Driver.class));

                    LOGGER.info(DRIVER + colleague.getId() + UPDATE_STATUS
                            + DriverStatus.RESTING.getName());
                }
                break;

            default:

                break;
        }
    }

    @Override
    @Transactional
    public void updateState(boolean userState) {

        LOGGER.debug("updateState() executed");

        final DriverDto existingDriver = (getAuthorizedDriverByUsername());
        DriverDto colleague = findColleagueAuthorizedDriverByUsername();

        if (!existingDriver.isInShift()) {
            if (findColleagueAuthorizedDriverByUsername() != null) {
                colleague.setInShift(userState);
                colleague.setStatus(DriverStatus.RESTING);
                driverDao.updateDriver(modelMapper.map(colleague,
                        Driver.class));

                LOGGER.info("For " + DRIVER + colleague.getId() + UPDATE_STATUS + colleague.getStatus().getName());
                LOGGER.info("For " + DRIVER + colleague.getId() + UPDATE_STATE + userState);
            }
        } else {
            if (findColleagueAuthorizedDriverByUsername() != null) {
                colleague.setInShift(false);
                colleague.setStatus(DriverStatus.RESTING);
                driverDao.updateDriver(modelMapper.map(colleague,
                        Driver.class));

                LOGGER.info("For " + DRIVER + colleague.getId() + UPDATE_STATE + userState);
                LOGGER.info("For " + DRIVER + colleague.getId() + UPDATE_STATUS + colleague.getStatus().getName());
            }
        }

        existingDriver.setInShift(userState);
        existingDriver.setStatus(DriverStatus.RESTING);
        driverDao.updateDriver(modelMapper.map(existingDriver, Driver.class));

        LOGGER.info("For " + DRIVER + existingDriver.getId() + UPDATE_STATE + userState);
        LOGGER.info("For " + DRIVER + existingDriver.getId() + UPDATE_STATUS + existingDriver.getStatus().getName());
    }
}
