package com.egovorushkin.logiweb.services.api;

import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.TruckDto;
import com.egovorushkin.logiweb.dto.TruckStatsDto;
import com.egovorushkin.logiweb.entities.Truck;

import java.util.List;

public interface TruckService {

    TruckDto getTruckById(long id);

    List<TruckDto> getAllTrucks();

    void createTruck(TruckDto truckDto);

    void updateTruck(TruckDto truckDto);

    void deleteTruck(long id);

    List<DriverDto> findCurrentDriversByTruckId(long id);

    List<DriverDto> findAvailableDriversByTruck(TruckDto truckDto);

    TruckStatsDto getStats();

    Truck findByRegistrationNumber(String registrationNumber);
}
