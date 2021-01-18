package com.egovorushkin.logiweb.dao.api;

import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.Truck;

import java.util.List;

public interface TruckDao {

    Truck getTruckById(long id);

    List<Truck> getAllTrucks();

    void createTruck(Truck truck);

    void updateTruck(Truck truck);

    void deleteTruck(long id);

    List<Driver> findCurrentDriversByTruckId(long id);

    List<Driver> findAvailableDriversByTruck(Truck truck);

}
