package com.egovorushkin.logiweb.services.impl;

import com.egovorushkin.logiweb.dao.api.CargoDao;
import com.egovorushkin.logiweb.entities.Cargo;
import com.egovorushkin.logiweb.services.api.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CargoServiceImpl implements CargoService {

    private final CargoDao cargoDao;

    @Autowired
    public CargoServiceImpl(CargoDao cargoDao) {
        this.cargoDao = cargoDao;
    }

    @Override
    public Cargo getCargoById(int id) {
        return cargoDao.getCargoById(id);
    }

    @Override
    public List listAll() {
        return cargoDao.listAll();
    }

    @Override
    public Cargo showCargo(int id) {
        return cargoDao.showCargo(id);
    }

    @Override
    @Transactional
    public void saveCargo(Cargo cargo) {
        cargoDao.saveCargo(cargo);
    }

    @Override
    @Transactional
    public void update(Cargo cargo) {
        cargoDao.update(cargo);
    }

    @Override
    @Transactional
    public void delete(int id) {
        cargoDao.delete(id);
    }

}
