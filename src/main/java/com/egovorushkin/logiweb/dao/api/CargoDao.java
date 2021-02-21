package com.egovorushkin.logiweb.dao.api;

import com.egovorushkin.logiweb.entities.Cargo;

import java.util.List;

public interface CargoDao {

    Cargo getCargoById(Long id);

    List<Cargo> getAllCargoes();

    void createCargo(Cargo cargo);

    void updateCargo(Cargo cargo);

    void deleteCargo(Long id);

    boolean cargoExistsById(Long id);

}
