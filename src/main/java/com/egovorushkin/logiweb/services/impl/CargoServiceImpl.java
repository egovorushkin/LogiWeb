package com.egovorushkin.logiweb.services.impl;

import com.egovorushkin.logiweb.dao.api.CargoDao;
import com.egovorushkin.logiweb.dto.CargoDto;
import com.egovorushkin.logiweb.entities.Cargo;
import com.egovorushkin.logiweb.exceptions.EntityNotFoundException;
import com.egovorushkin.logiweb.exceptions.ServiceException;
import com.egovorushkin.logiweb.services.api.CargoService;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class with business logics for {@link Cargo}
 * and {@link CargoDto}
 * implements {@link CargoService}
 */
@Service
public class CargoServiceImpl implements CargoService {

    private static final Logger LOGGER =
            Logger.getLogger(CargoServiceImpl.class.getName());

    private static final String CARGO = "Cargo with id = ";


    private final CargoDao cargoDao;
    private final Mapper mapper;

    public CargoServiceImpl(CargoDao cargoDao, Mapper mapper) {
        this.cargoDao = cargoDao;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public CargoDto getCargoById(Long id) {

        LOGGER.debug("getCargoById() executed");

        if (cargoDao.getCargoById(id) == null) {
            throw new EntityNotFoundException(CARGO + id + " is not found");
        }

        LOGGER.info("Found cargo with id = " + id);

        return mapper.map(cargoDao.getCargoById(id), CargoDto.class);
    }

    @Override
    @Transactional
    public List<CargoDto> getAllCargoes() {

        LOGGER.debug("getAllCargoes() executed");

        List<Cargo> cargoes = cargoDao.getAllCargoes();
        return cargoes.stream()
                .map(cargo -> mapper.map(cargo, CargoDto.class))
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

        cargoDao.createCargo(mapper.map(cargoDto, Cargo.class));

        LOGGER.info(CARGO + cargoDto.getId() + " created");
    }

    @Override
    @Transactional
    public void updateCargo(CargoDto cargoDto) {

        LOGGER.debug("updateCargo() executed");

        try {
            cargoDao.updateCargo(mapper.map(cargoDto, Cargo.class));
        } catch (NoResultException e) {
            throw new EntityNotFoundException(String.format("Cargo with id " +
                    "%s does not exist", cargoDto.getId()));
        }

        LOGGER.info(CARGO + cargoDto.getId() + " updated");
    }

    @Override
    @Transactional
    public void deleteCargo(Long id) {

        LOGGER.debug("deleteCargo() executed");

        cargoDao.deleteCargo(id);

        LOGGER.info(CARGO + id + " deleted");
    }

    @Override
    @Transactional
    public List<CargoDto> findAvailableCargoes() {
        List<Cargo> cargoes = cargoDao.findAvailableCargoes();
        return cargoes.stream()
                .map(cargo -> mapper.map(cargo, CargoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<CargoDto> listAllByPage(int firstResult, int maxResult) {
        List<Cargo> cargoes = cargoDao.listAllByPage(firstResult, maxResult);

        return cargoes.stream()
                .map(cargo -> mapper.map(cargo, CargoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long totalCount() {
        return cargoDao.totalCount();
    }

}
