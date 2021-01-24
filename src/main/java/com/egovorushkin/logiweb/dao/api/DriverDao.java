package com.egovorushkin.logiweb.dao.api;

import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.Truck;

import java.util.List;

public interface DriverDao {

    Driver getDriverById(long id);

    List<Driver> getAllDrivers();

    void saveDriver(Driver driver);

    void updateDriver(Driver driver);

    void deleteDriver(long id);

    Driver getDriverByUsername(String username);

    List<Truck> findAvailableTrucksByDriver(Driver driver);
}
