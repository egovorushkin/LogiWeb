package com.egovorushkin.logiweb.dao.api;

import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.Truck;

import java.util.List;

public interface TruckDao {

    Truck getTruckById(int id);

    List<Truck> listAll();

    void saveTruck(Truck truck);

    void update(Truck truck);

    void delete(int id);

    Truck showTruck(int id);

    List<Driver> findCurrentDrivers(int id) throws Exception;
}
