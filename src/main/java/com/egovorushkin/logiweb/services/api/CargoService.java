package com.egovorushkin.logiweb.services.api;

import com.egovorushkin.logiweb.entities.Cargo;

import java.util.List;

public interface CargoService {

    Cargo getCargoById(int id);

    List<Cargo> listAll();

    void saveCargo(Cargo cargo);

    void update(Cargo cargo);

    void delete(int id);

    Cargo showCargo(int id);
}
