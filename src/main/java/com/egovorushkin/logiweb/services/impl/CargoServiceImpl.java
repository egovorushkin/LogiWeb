package com.egovorushkin.logiweb.services.impl;

import com.egovorushkin.logiweb.dao.api.CargoDao;
import com.egovorushkin.logiweb.dto.CargoDto;
import com.egovorushkin.logiweb.entities.Cargo;
import com.egovorushkin.logiweb.exceptions.EntityNotFoundException;
import com.egovorushkin.logiweb.exceptions.ServiceException;
import com.egovorushkin.logiweb.services.api.CargoService;
import org.apache.log4j.Logger;
import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CargoServiceImpl implements CargoService {

    private static final Logger LOGGER =
            Logger.getLogger(CargoServiceImpl.class.getName());
    private static final String CARGO = "Cargo with id = ";


    private final CargoDao cargoDao;
    private final ModelMapper modelMapper;

    @Autowired
    public CargoServiceImpl(CargoDao cargoDao, ModelMapper modelMapper) {
        this.cargoDao = cargoDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public CargoDto getCargoById(long id) {

        LOGGER.debug("getCargoById() executed");

        if (cargoDao.getCargoById(id) == null) {
            throw new EntityNotFoundException(CARGO + id + " is" +
                    " not found");
        }

        LOGGER.info("Found cargo with id = " + id);

        return modelMapper.map(cargoDao.getCargoById(id), CargoDto.class);
    }

    @Override
    public List<CargoDto> getAllCargoes() {

        LOGGER.debug("getAllCargoes() executed");

        List<Cargo> cargoes = cargoDao.getAllCargoes();
        return cargoes.stream()
                .map(cargo -> modelMapper.map(cargo, CargoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createCargo(CargoDto cargoDto) {

        LOGGER.debug("createCargo() executed");

        if (cargoDao.cargoExistsById(cargoDto.getId())) {
            throw new ServiceException(String.format("Cargo with id" +
                    " %s already exists", cargoDto.getId()));
        }

        cargoDao.createCargo(modelMapper.map(cargoDto, Cargo.class));

        LOGGER.info(CARGO + cargoDto.getId() + " created");
    }

    @Override
    @Transactional
    public void updateCargo(CargoDto cargoDto) {

        LOGGER.debug("updateCargo() executed");

        try {
            cargoDao.updateCargo(modelMapper.map(cargoDto, Cargo.class));
        } catch (NoResultException e) {
            throw new EntityNotFoundException(String.format("Cargo with id " +
                    "%s does not exist", cargoDto.getId()));
        }

        LOGGER.info(CARGO + cargoDto.getId() + " updated");
    }

    @Override
    @Transactional
    public void deleteCargo(long id) {

        LOGGER.debug("deleteCargo() executed");

        cargoDao.deleteCargo(id);

        LOGGER.info(CARGO + id + " deleted");
    }

}
