package com.egovorushkin.logiweb.services;

import com.egovorushkin.logiweb.dao.DriverDao;
import com.egovorushkin.logiweb.entities.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverDao driverDao;

    @Autowired
    public DriverServiceImpl(DriverDao driverDao) {
        this.driverDao = driverDao;
    }

    @Override
    @Transactional
    public Driver getDriverById(int id) {
        return driverDao.getDriverById(id);
    }

    @Override
    @Transactional
    public List<Driver> listAll() {
        return driverDao.listAll();
    }

    @Override
    @Transactional
    public void saveDriver(Driver driver) {
        driverDao.saveDriver(driver);
    }

    @Override
    @Transactional
    public void update(Driver driver) {
        driverDao.update(driver);
    }

    @Override
    @Transactional
    public void delete(int id) {
        driverDao.delete(id);
    }

    @Override
    public Driver showDriver(int id) {
        return driverDao.showDriver(id);
    }
}
