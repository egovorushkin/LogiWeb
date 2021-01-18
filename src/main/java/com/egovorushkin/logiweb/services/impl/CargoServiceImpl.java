package com.egovorushkin.logiweb.services.impl;

import com.egovorushkin.logiweb.dao.api.CargoDao;
import com.egovorushkin.logiweb.dto.CargoDto;
import com.egovorushkin.logiweb.entities.Cargo;
import com.egovorushkin.logiweb.services.api.CargoService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CargoServiceImpl implements CargoService {

    private static final Logger LOGGER =
            Logger.getLogger(CargoServiceImpl.class.getName());


    private final CargoDao cargoDao;
    private final ModelMapper modelMapper;


    @Autowired
    public CargoServiceImpl(CargoDao cargoDao,ModelMapper modelMapper) {
        this.cargoDao = cargoDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public CargoDto getCargoById(long id) {
        return modelMapper.map(cargoDao.getCargoById(id), CargoDto.class);
    }

    @Override
    public List<CargoDto> getAllCargoes() {
        List<Cargo> cargoes = cargoDao.getAllCargoes();
        return cargoes.stream()
                .map(cargo -> modelMapper.map(cargo, CargoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createCargo(CargoDto cargoDto) {
        cargoDao.createCargo(modelMapper.map(cargoDto, Cargo.class));
    }

    @Override
    @Transactional
    public void updateCargo(CargoDto cargoDto) {
        cargoDao.updateCargo(modelMapper.map(cargoDto, Cargo.class));
    }

    @Override
    @Transactional
    public void deleteCargo(long id) {
        cargoDao.deleteCargo(id);
    }

}
