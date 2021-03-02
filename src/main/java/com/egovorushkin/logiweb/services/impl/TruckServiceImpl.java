package com.egovorushkin.logiweb.services.impl;

import com.egovorushkin.logiweb.dao.api.TruckDao;
import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.TruckDto;
import com.egovorushkin.logiweb.dto.TruckStatsDto;
import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.Truck;
import com.egovorushkin.logiweb.entities.enums.DriverStatus;
import com.egovorushkin.logiweb.entities.enums.TruckStatus;
import com.egovorushkin.logiweb.exceptions.EntityNotFoundException;
import com.egovorushkin.logiweb.exceptions.ServiceException;
import com.egovorushkin.logiweb.services.api.DriverService;
import com.egovorushkin.logiweb.services.api.ScoreboardService;
import com.egovorushkin.logiweb.services.api.TruckService;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class with business logics for {@link Truck}
 * and {@link TruckDto}
 * implements {@link TruckService}
 */
@Service
public class TruckServiceImpl implements TruckService {

    private static final Logger LOGGER =
            Logger.getLogger(TruckServiceImpl.class.getName());

    private static final String TRUCK = "Truck with id = ";
    private static final String STATUS_FAULTY = "FAULTY";
    private static final String STATUS_ON_THE_WAY = "ON_THE_WAY";

    private final TruckDao truckDao;
    private final DriverService driverService;
    private final Mapper mapper;
    private final ScoreboardService scoreboardService;

    @Autowired
    public TruckServiceImpl(TruckDao truckDao,
                            @Lazy DriverService driverService,
                            Mapper mapper,
                            ScoreboardService scoreboardService) {
        this.truckDao = truckDao;
        this.driverService = driverService;
        this.mapper = mapper;
        this.scoreboardService = scoreboardService;
    }

    @Override
    @Transactional
    public TruckDto getTruckById(Long id) {

        LOGGER.debug("getTruckById() executed");

        Truck truck = truckDao.getTruckById(id);

        if (truck == null) {
            throw new EntityNotFoundException(TRUCK + id + " is " +
                    "not found");
        }

        LOGGER.info("Found the truck with id = " + id);

        return mapper.map(truck, TruckDto.class);
    }

    @Override
    @Transactional
    public List<TruckDto> getAllTrucks() {

        LOGGER.debug("getAllTrucks() executed");

        List<Truck> trucks = truckDao.getAllTrucks();

        return trucks.stream()
                .map(truck -> mapper.map(truck, TruckDto.class))
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
        truckDao.createTruck(mapper.map(truckDto, Truck.class));

        scoreboardService.updateScoreboard();

        LOGGER.info(TRUCK + truckDto.getId() + " created");
    }

    @Override
    @Transactional
    public void updateTruck(TruckDto truckDto) {

        LOGGER.debug("updateTruck() executed");

        try {
            truckDao.updateTruck(mapper.map(truckDto, Truck.class));
        } catch (NoResultException e) {
            throw new EntityNotFoundException(String.format("Truck with " +
                            "registration number %s does not exist",
                    truckDto.getRegistrationNumber()));
        }

        scoreboardService.updateScoreboard();

        LOGGER.info(TRUCK + truckDto.getId() + " updated");
    }

    @Override
    @Transactional
    public void deleteTruck(Long id) {

        LOGGER.debug("deleteTruck() executed");

        truckDao.deleteTruck(id);

        scoreboardService.updateScoreboard();

        LOGGER.info(TRUCK + id + " deleted");
    }

    /**
     * This method finds available drivers for given truck
     * @param truckDto given truck {@link TruckDto}
     * @return {@link List<DriverDto>} available drivers
     */
    @Override
    public List<DriverDto> findAvailableDriversByTruck(TruckDto truckDto) {

        LOGGER.debug("findAvailableDriversByTruck() executed");

        List<Driver> availableDrivers =
                truckDao.findAvailableDriversByTruck(mapper.map(truckDto
                        , Truck.class));
        return availableDrivers.stream()
                .map(driver -> mapper.map(driver, DriverDto.class))
                .collect(Collectors.toList());
    }

    /**
     * This method returns truck statistics
     * like total, available, busy and faulty trucks
     * @return {@link TruckStatsDto} driver statistic
     */
    @Override
    public TruckStatsDto getStats() {
        TruckStatsDto truckStats = new TruckStatsDto();
        List<Truck> trucks = truckDao.getAllTrucks();

        long total = trucks.size();
        long faulty = getCountByState(trucks);
        long busy = getCountByStatus(trucks);

        truckStats.setTotal(total);
        truckStats.setFaulty(faulty);
        truckStats.setBusy(busy);

        truckStats.setAvailable(total - faulty - busy);

        return truckStats;
    }

    /**
     * This method finds the truck by given registration number
     * @param registrationNumber given registration number
     * @return truck {@link Truck}
     */
    @Override
    public Truck findByRegistrationNumber(String registrationNumber) {

        LOGGER.debug("findByRegistrationNumber() executed");

        return truckDao.findByRegistrationNumber(registrationNumber);
    }

    @Override
    @Transactional
    public void unbindDriver(Long truckId, Long driverId) {
        TruckDto truckDto = getTruckById(truckId);
        DriverDto driverDto = driverService.getDriverById(driverId);

        if (truckDto.getDrivers().size() == 1) {
            truckDto.setStatus(TruckStatus.PARKED);
            truckDto.setBusy(false);

        }
        truckDto.removeDriver(driverDto);

        driverDto.setInShift(false);
        driverDto.setStatus(DriverStatus.RESTING);

        driverService.updateDriver(driverDto);
        updateTruck(truckDto);
    }

    /**
     * Helping method forgetStats method
     * Counts given trucks by given status
     * @param trucks {@link List<Truck>} given trucks
     * @return number of trucks
     */
    private long getCountByStatus(List<Truck> trucks) {
        return trucks.stream()
                .filter(truck -> TruckServiceImpl.STATUS_ON_THE_WAY
                        .equals(truck.getStatus().getTitle()))
                .count();
    }
    private long getCountByState(List<Truck> trucks) {
        return trucks.stream()
                .filter(truck -> TruckServiceImpl.STATUS_FAULTY
                        .equals(truck.getState().getTitle()))
                .count();
    }
}
