package com.egovorushkin.logiweb.services.impl;

import com.egovorushkin.logiweb.dao.api.TruckDao;
import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.TruckDto;
import com.egovorushkin.logiweb.dto.TruckStatsDto;
import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.Truck;
import com.egovorushkin.logiweb.exceptions.EntityNotFoundException;
import com.egovorushkin.logiweb.exceptions.ServiceException;
import com.egovorushkin.logiweb.services.api.ScoreboardService;
import com.egovorushkin.logiweb.services.api.TruckService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TruckServiceImpl implements TruckService {

    private static final Logger LOGGER =
            Logger.getLogger(TruckServiceImpl.class.getName());
    private static final String TRUCK = "Truck with id = ";

    private final TruckDao truckDao;
    private final ModelMapper modelMapper;
    private final ScoreboardService scoreboardService;

    @Autowired
    public TruckServiceImpl(TruckDao truckDao,
                            ModelMapper modelMapper, ScoreboardService scoreboardService) {
        this.truckDao = truckDao;
        this.modelMapper = modelMapper;
        this.scoreboardService = scoreboardService;
    }

    @Override
    @Transactional
    public TruckDto getTruckById(long id) {

        LOGGER.debug("getTruckById() executed");

        Truck truck = truckDao.getTruckById(id);

        if (truck == null) {
            throw new EntityNotFoundException(TRUCK + id + " is " +
                    "not found");
        }

        LOGGER.info("Found the truck with id = " + id);

        return modelMapper.map(truckDao.getTruckById(id), TruckDto.class);
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

        scoreboardService.updateScoreboard();

        LOGGER.info(TRUCK + truckDto.getId() + " created");
    }

    @Override
    @Transactional
    public void updateTruck(TruckDto truckDto) {

        LOGGER.debug("updateTruck() executed");

        try {
            truckDao.updateTruck(modelMapper.map(truckDto, Truck.class));
        } catch (NoResultException e) {
            throw new EntityNotFoundException(String.format("Truck with " +
                            "registration" +
                            " number %s does not exist",
                    truckDto.getRegistrationNumber()));
        }

        scoreboardService.updateScoreboard();

        LOGGER.info(TRUCK + truckDto.getId() + " updated");
    }

    @Override
    @Transactional
    public void deleteTruck(long id) {

        LOGGER.debug("deleteTruck() executed");

        truckDao.deleteTruck(id);

        scoreboardService.updateScoreboard();

        LOGGER.info(TRUCK + id + " deleted");
    }

    @Override
    @Transactional
    public List<DriverDto> findCurrentDriversByTruckId(long id) {

        LOGGER.debug("findCurrentDriversByTruckId() executed");

        List<Driver> currentDrivers = truckDao.findCurrentDriversByTruckId(id);

        if (currentDrivers == null) {
            return Collections.emptyList();
        }

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

    /*
    This method returns truck statistics
    like total, available, busy and faulty trucks
     */
    @Override
    public TruckStatsDto getStats() {
        TruckStatsDto truckStats = new TruckStatsDto();

        truckStats.setTotal(truckDao.getAllTrucks().size());
        truckStats.setFaulty(truckDao.getAllTrucks()
                .stream()
                .filter(truck ->
                        truck.getState().getTitle().equals("FAULTY")).count());
        truckStats.setBusy(truckDao.getAllTrucks()
                .stream().filter(truck -> truck.getStatus().getTitle().equals("ON_THE_WAY")).count());

        truckStats.setAvailable(truckStats.getTotal() - truckStats.getBusy()
                - truckStats.getFaulty());
        return truckStats;
    }

    @Override
    public Truck findByRegistrationNumber(String registrationNumber) {

        LOGGER.debug("findByRegistrationNumber() executed");

        return truckDao.findByRegistrationNumber(registrationNumber);
    }

}
