package com.egovorushkin.logiweb.dao;

import com.egovorushkin.logiweb.entities.Driver;

import java.util.List;

public interface DriverDao {

    Driver getDriverById(int id);

    List<Driver> listAll();

    void saveDriver(Driver driver);

    void update(Driver driver);

    void delete(int id);

    Driver showDriver(int id);
}
