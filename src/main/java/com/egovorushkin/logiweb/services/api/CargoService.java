package com.egovorushkin.logiweb.services.api;

import com.egovorushkin.logiweb.dto.CargoDto;

import java.util.List;

public interface CargoService {

    CargoDto getCargoById(long id);

    List<CargoDto> getAllCargoes();

    void createCargo(CargoDto cargoDto);

    void updateCargo(CargoDto cargoDto);

    void deleteCargo(long id);

}
