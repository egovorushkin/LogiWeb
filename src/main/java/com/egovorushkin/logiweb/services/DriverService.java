package com.egovorushkin.logiweb.services;

import com.egovorushkin.logiweb.entities.Driver;

import java.util.List;

public interface DriverService {

    Driver getDriverById(int id);

    List<Driver> listAll();

    void saveDriver(Driver driver);

    void update(Driver driver);

    void delete(int id);

    Driver showDriver(int id);
}
