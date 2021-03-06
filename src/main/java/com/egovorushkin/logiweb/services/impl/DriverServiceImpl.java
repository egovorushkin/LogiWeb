package com.egovorushkin.logiweb.services.impl;

import com.egovorushkin.logiweb.config.security.IAuthenticationFacade;
import com.egovorushkin.logiweb.dao.api.DriverDao;
import com.egovorushkin.logiweb.dao.api.OrderDao;
import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.DriverStatsDto;
import com.egovorushkin.logiweb.dto.TruckDto;
import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.Order;
import com.egovorushkin.logiweb.entities.Truck;
import com.egovorushkin.logiweb.entities.enums.CargoStatus;
import com.egovorushkin.logiweb.entities.enums.DriverStatus;
import com.egovorushkin.logiweb.entities.enums.TruckStatus;
import com.egovorushkin.logiweb.exceptions.EntityNotFoundException;
import com.egovorushkin.logiweb.exceptions.ServiceException;
import com.egovorushkin.logiweb.services.api.DriverService;
import com.egovorushkin.logiweb.services.api.ScoreboardService;
import com.egovorushkin.logiweb.services.api.TruckService;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class with business logics for {@link Driver}
 * and {@link DriverDto}
 * implements {@link DriverService}
 */
@Service
public class DriverServiceImpl implements DriverService {

    private static final Logger LOGGER =
            Logger.getLogger(DriverServiceImpl.class.getName());

    private static final String DRIVER = "Driver with id = ";
    private static final String UPDATE_STATUS = " updated status for ";
    private static final String UPDATE_STATE = " updated state inShift = ";

    private final DriverDao driverDao;
    private final TruckService truckService;
    private final Mapper mapper;
    private final IAuthenticationFacade authenticationFacade;
    private final ScoreboardService scoreboardService;
    private final OrderDao orderDao;

    public DriverServiceImpl(DriverDao driverDao,
                             TruckService truckService,
                             Mapper mapper,
                             IAuthenticationFacade authenticationFacade,
                             ScoreboardService scoreboardService,
                             OrderDao orderDao) {
        this.driverDao = driverDao;
        this.truckService = truckService;
        this.mapper = mapper;
        this.authenticationFacade = authenticationFacade;
        this.scoreboardService = scoreboardService;
        this.orderDao = orderDao;
    }

    @Override
    @Transactional
    public DriverDto getDriverById(Long id) {

        LOGGER.debug("getDriverById() executed");

        if (driverDao.getDriverById(id) == null) {
            throw new EntityNotFoundException(DRIVER + id + " is" +
                    " not found");
        }

        LOGGER.info("Found driver with id = " + id);

        return mapper.map(driverDao.getDriverById(id), DriverDto.class);
    }

    @Override
    @Transactional
    public List<DriverDto> getAllDrivers() {

        LOGGER.debug("getAllDrivers() executed");

        List<Driver> drivers = driverDao.getAllDrivers();
        return drivers.stream()
                .map(driver -> mapper.map(driver, DriverDto.class))
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
        driverDao.saveDriver(mapper.map(driverDto, Driver.class));

        scoreboardService.updateScoreboard();

        LOGGER.info(DRIVER + driverDto.getId() + " created");
    }

    @Override
    @Transactional
    public void updateDriver(DriverDto driverDto) {

        LOGGER.debug("updateDriver() executed");

        try {
            driverDao.updateDriver(mapper.map(driverDto, Driver.class));
        } catch (NoResultException e) {
            throw new EntityNotFoundException(String.format(DRIVER +
                    "%s does not exist", driverDto.getId()));
        }

        scoreboardService.updateScoreboard();

        LOGGER.info(DRIVER + driverDto.getId() + " updated");
    }

    @Override
    @Transactional
    public void deleteDriver(Long id) {

        LOGGER.debug("deleteDriver() executed");

        driverDao.deleteDriver(id);

        scoreboardService.updateScoreboard();

        LOGGER.info(DRIVER + id + " deleted");
    }

    /**
     * This method returns the currently logged driver
     * @return {@link DriverDto} currently logged driver
     */
    @Override
    @Transactional
    public DriverDto getAuthorizedDriverByUsername() {

        LOGGER.debug("getAuthorizedDriverByUsername() executed");

        Authentication authentication =
                authenticationFacade.getAuthentication();

        String authorizedDriver = authentication.getName();

        LOGGER.info("Authorized driver with username = " +
                authorizedDriver + " found");

        return mapper
                .map(driverDao.getDriverByUsername(authorizedDriver),
                        DriverDto.class);
    }

    /**
     * This method finds available trucks for given driver
     * @param driverDto this is given driver
     * @return {@link List<TruckDto>} available trucks
     */
    @Override
    @Transactional
    public List<TruckDto> findAvailableTrucksByDriver(DriverDto driverDto) {

        LOGGER.debug("findAvailableTrucksByDriver() executed");

        List<Truck> availableTrucks =
                driverDao.findAvailableTrucksByDriver(mapper.map(driverDto
                        , Driver.class));

        LOGGER.info("Available trucks found");

        return availableTrucks.stream()
                .map(truck -> mapper.map(truck, TruckDto.class))
                .collect(Collectors.toList());
    }

    /**
     * This method finds a colleague of currently logged driver
     * @return {@link DriverDto} colleague
     */
    @Override
    @Transactional
    public DriverDto findColleagueAuthorizedDriverByUsername() {

        LOGGER.debug("findColleagueAuthorizedDriverByUsername() executed");

        DriverDto authorizedDriver = getAuthorizedDriverByUsername();

        if (authorizedDriver.getTruck() == null) {
            return null;
        }

        Set<DriverDto> currentDrivers = authorizedDriver.getTruck().getDrivers();

        DriverDto colleague = currentDrivers.stream()
                .filter(driver -> !driver.getId().equals(authorizedDriver.getId()))
                .findFirst().orElse(null);

        if (colleague == null) {
            LOGGER.info("Colleague of Authorized driver is not found");
            return null;
        }
        return colleague;
    }

    // TODO: refactor this method
    /**
     * This method updates status depending on the input status of driver
     * @param driverStatus input status of driver
     */
    @Override
    @Transactional
    public void updateStatus(DriverStatus driverStatus) {

        LOGGER.debug("updateStatus() executed");

        final DriverDto existingDriver = getAuthorizedDriverByUsername();

        DriverDto colleague = findColleagueAuthorizedDriverByUsername();

        Order order = orderDao.findOrderByTruckId(existingDriver.getTruck().getId());

        switch (driverStatus.getTitle()) {
            case "DRIVING":
                if (existingDriver.getTruck() != null) {
                    updateStatusForDriver(existingDriver, driverStatus,
                            existingDriver.isInShift());
                    updateStatusAndStateForTruck(existingDriver.getTruck(),
                            TruckStatus.ON_THE_WAY, true);

                    if (order != null) {
                        order.getCargo().setStatus(CargoStatus.SHIPPED);
                        orderDao.updateOrder(order);
                    }
                }

                if (colleague != null) {
                    updateStatusForDriver(colleague, DriverStatus.SECOND_DRIVER,
                            colleague.isInShift());
                }
                break;
            case "SECOND_DRIVER":
                updateStatusForDriver(existingDriver, driverStatus,
                        existingDriver.isInShift());

                if (colleague != null) {
                    updateStatusForDriver(colleague, DriverStatus.DRIVING,
                            colleague.isInShift());
                }
                break;
            case "LOADING_UNLOADING":
                updateStatusForDriver(existingDriver, driverStatus,
                        existingDriver.isInShift());

                if (colleague != null) {
                    updateStatusForDriver(colleague,
                            DriverStatus.LOADING_UNLOADING,
                            colleague.isInShift());
                }
                break;

            case "RESTING":
                if (existingDriver.getTruck() != null) {
                    updateStatusForDriver(existingDriver, driverStatus,
                            existingDriver.isInShift());
                    updateStatusAndStateForTruck(existingDriver.getTruck(),
                            TruckStatus.PARKED,
                            existingDriver.getTruck().isBusy());
                }

                if (colleague != null) {
                    updateStatusForDriver(colleague, DriverStatus.RESTING,
                            true);
                }
                break;
            default:
                break;
        }
    }
    private void updateStatusAndStateForTruck(TruckDto truck,
                                              TruckStatus status,
                                              boolean truckIsBusy) {
        truck.setStatus(status);
        truck.setBusy(truckIsBusy);
        truckService.updateTruck(truck);

        scoreboardService.updateScoreboard();

        LOGGER.info("Truck with id" + truck.getId() +
                " update status to " + truck.getStatus());
    }

    private void updateStatusForDriver(DriverDto colleague,
                                          DriverStatus status,
                                          boolean inShift) {
        colleague.setStatus(status);
        colleague.setInShift(inShift);
        driverDao.updateDriver(mapper.map(colleague,
                Driver.class));

        scoreboardService.updateScoreboard();

        LOGGER.info(DRIVER + colleague.getId() + UPDATE_STATUS
                + status.getName());
    }

    /**
     * This method updates the state of the driver
     * and his colleague (if exists)
     * @param userState input state
     */
    @Override
    @Transactional
    public void updateState(boolean userState) {

        LOGGER.debug("updateState() executed");

        final DriverDto existingDriver = (getAuthorizedDriverByUsername());
        DriverDto colleague = findColleagueAuthorizedDriverByUsername();

        if (!existingDriver.isInShift()) {
            if (findColleagueAuthorizedDriverByUsername() != null) {
                updateStateAndStatusForDriver(colleague, userState);
            }
        } else {
            if (findColleagueAuthorizedDriverByUsername() != null) {
                updateStateAndStatusForDriver(colleague, false);
            }
        }
        updateStateAndStatusForDriver(existingDriver, userState);
    }


    /**
     * This method returns drivers statistics
     * like total, available and not available drivers
     * @return {@link DriverStatsDto} driver statistic
     */
    @Override
    @Transactional
    public DriverStatsDto getStats() {
        DriverStatsDto driverStats = new DriverStatsDto();
        List<Driver> drivers = driverDao.getAllDrivers();

        long total = drivers.size();
        long available = drivers.stream().filter(driver -> !driver.isInShift())
                .count();

        driverStats.setTotal(total);
        driverStats.setAvailable(available);
        driverStats.setNotAvailable(total - available);

        return driverStats;
    }


    @Override
    @Transactional
    public List<DriverDto> listAllByPage(int firstResult, int maxResult) {
        List<Driver> drivers = driverDao.listAllByPage(firstResult, maxResult);

        return drivers.stream()
                .map(driver -> mapper.map(driver, DriverDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long totalCount() {
        return driverDao.totalCount();
    }

    /**
     * Helping method for updateStatus method
     * @param driver input driver
     * @param state input state
     *
     */
    private void updateStateAndStatusForDriver(DriverDto driver, boolean state) {
        driver.setInShift(state);
        driver.setStatus(DriverStatus.RESTING);
        driverDao.updateDriver(mapper.map(driver, Driver.class));

        scoreboardService.updateScoreboard();

        LOGGER.info("For " + DRIVER + driver.getId() + UPDATE_STATUS
                + driver.getStatus().getName());
        LOGGER.info("For " + DRIVER + driver.getId() + UPDATE_STATE + state);
    }
}
