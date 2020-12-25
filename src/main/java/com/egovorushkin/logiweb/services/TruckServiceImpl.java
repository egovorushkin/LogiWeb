package com.egovorushkin.logiweb.services;

import com.egovorushkin.logiweb.dao.TruckDao;
import com.egovorushkin.logiweb.entities.Truck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TruckServiceImpl implements TruckService {

    private final TruckDao truckDao;

    @Autowired
    public TruckServiceImpl(TruckDao truckDao) {
        this.truckDao = truckDao;
    }

    @Override
    public Truck getTruckById(int id) {
        return truckDao.getTruckById(id);
    }

    @Override
    public List listAll() {
        return truckDao.listAll();
    }

    @Override
    public Truck showTruck(int id) {
        return truckDao.showTruck(id);
    }

    @Override
    @Transactional
    public void saveTruck(Truck truck) {
        truckDao.saveTruck(truck);
    }

    @Override
    @Transactional
    public void update(Truck truck) {
        truckDao.update(truck);
    }

    @Override
    @Transactional
    public void delete(int id) {
        truckDao.delete(id);
    }

}
