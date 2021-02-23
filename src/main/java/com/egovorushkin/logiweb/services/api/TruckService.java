package com.egovorushkin.logiweb.services.api;

import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.TruckDto;
import com.egovorushkin.logiweb.dto.TruckStatsDto;
import com.egovorushkin.logiweb.entities.Truck;

import java.util.List;

/**
 * Service interface for {@link com.egovorushkin.logiweb.entities.Truck}
 */
public interface TruckService {

    TruckDto getTruckById(Long id);

    List<TruckDto> getAllTrucks();

    void createTruck(TruckDto truckDto);

    void updateTruck(TruckDto truckDto);

    void deleteTruck(Long id);

    /**
     * This method finds current drivers for given truck id
     * @param id Truck id
     * @return {@link List<DriverDto>} current drivers
     */
    List<DriverDto> findCurrentDriversByTruckId(Long id);

    /**
     * This method finds available drivers for given truck
     * @param truckDto given truck {@link TruckDto}
     * @return {@link List<DriverDto>} available drivers
     */
    List<DriverDto> findAvailableDriversByTruck(TruckDto truckDto);

    /**
     * This method returns truck statistics
     * like total, available, busy and faulty trucks
     * @return {@link TruckStatsDto} driver statistic
     */
    TruckStatsDto getStats();

    /**
     * This method finds the truck by given registration number
     * @param registrationNumber given registration number
     * @return truck {@link Truck}
     */
    Truck findByRegistrationNumber(String registrationNumber);
}
