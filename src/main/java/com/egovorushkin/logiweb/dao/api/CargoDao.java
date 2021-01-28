package com.egovorushkin.logiweb.dao.api;

import com.egovorushkin.logiweb.entities.Cargo;

import java.util.List;

public interface CargoDao {

    Cargo getCargoById(long id);

    List<Cargo> getAllCargoes();

    void createCargo(Cargo cargo);

    void updateCargo(Cargo cargo);

    void deleteCargo(long id);

    boolean cargoExistsById(long id);

}
