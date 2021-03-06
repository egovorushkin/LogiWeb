package com.egovorushkin.logiweb.services.api;

import com.egovorushkin.logiweb.dto.CargoDto;

import java.util.List;

/**
 * Service interface for {@link com.egovorushkin.logiweb.entities.Cargo}
 */
public interface CargoService {

    CargoDto getCargoById(Long id);

    List<CargoDto> getAllCargoes();

    void createCargo(CargoDto cargoDto);

    void updateCargo(CargoDto cargoDto);

    void deleteCargo(Long id);

    List<CargoDto> findAvailableCargoes();

    List<CargoDto> listAllByPage(int firstResult, int maxResult);

    Long totalCount();

}
