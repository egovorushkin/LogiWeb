package com.egovorushkin.logiweb.dao.api;

import com.egovorushkin.logiweb.entities.Cargo;

import java.util.List;

/**
 * This interface represent some methods to operate on a {@link Cargo}
 * (retrieve from and save to database)
 */
public interface CargoDao {

    Cargo getCargoById(Long id);

    List<Cargo> getAllCargoes();

    void createCargo(Cargo cargo);

    void updateCargo(Cargo cargo);

    void deleteCargo(Long id);

    boolean cargoExistsById(Long id);

    List<Cargo> findAvailableCargoes();

    List<Cargo> listAllByPage(int firstResult, int maxResult);

    Long totalCount();

}
