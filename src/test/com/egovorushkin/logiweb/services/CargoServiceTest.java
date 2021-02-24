package com.egovorushkin.logiweb.services;

import com.egovorushkin.logiweb.dao.api.CargoDao;
import com.egovorushkin.logiweb.dto.CargoDto;
import com.egovorushkin.logiweb.entities.Cargo;
import com.egovorushkin.logiweb.entities.enums.CargoStatus;
import com.egovorushkin.logiweb.exceptions.EntityNotFoundException;
import com.egovorushkin.logiweb.exceptions.ServiceException;
import com.egovorushkin.logiweb.services.api.CargoService;
import com.egovorushkin.logiweb.services.impl.CargoServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CargoServiceTest {

    private static final Long CARGO_ONE_ID = 1L;
    private static final String CARGO_ONE_NAME = "Laptops";
    private static final int CARGO_ONE_WEIGHT = 5000;
    private static final CargoStatus CARGO_ONE_STATUS_PREPARED =
            CargoStatus.PREPARED;

    private static final Long CARGO_TWO_ID = 2L;
    private static final String CARGO_TWO_NAME = "TV";
    private static final int CARGO_TWO_WEIGHT = 10000;
    private static final CargoStatus CARGO_TWO_STATUS_SHIPPED =
            CargoStatus.SHIPPED;

    CargoService cargoService;

    @Mock
    private CargoDao cargoDao;
    private final Cargo cargoOne = new Cargo();
    private final Cargo cargoTwo = new Cargo();
    CargoDto cargoDto = new CargoDto();
    ModelMapper modelMapper;

    @BeforeEach
    public void init() {
        modelMapper = new ModelMapper();
        cargoService = new CargoServiceImpl(cargoDao, modelMapper);

        cargoOne.setId(CARGO_ONE_ID);
        cargoOne.setName(CARGO_ONE_NAME);
        cargoOne.setWeight(CARGO_ONE_WEIGHT);
        cargoOne.setStatus(CARGO_ONE_STATUS_PREPARED);

        cargoTwo.setId(CARGO_TWO_ID);
        cargoTwo.setName(CARGO_TWO_NAME);
        cargoTwo.setWeight(CARGO_TWO_WEIGHT);
        cargoTwo.setStatus(CARGO_TWO_STATUS_SHIPPED);
    }

    @Test
    @DisplayName("Test get cargo by id success")
    void testGetCargoByIdSuccess() {
        when(cargoDao.getCargoById(CARGO_ONE_ID)).thenReturn(cargoOne);

        cargoDto = cargoService.getCargoById(CARGO_ONE_ID);

        Assertions.assertEquals(modelMapper.map(cargoOne, CargoDto.class),
                cargoDto);
    }

    @Test
    @DisplayName("Test get cargo by id failed")
    void testGetCargoByIdFailed() {
        when(cargoDao.getCargoById(CARGO_ONE_ID)).thenReturn(null);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> cargoService.getCargoById(CARGO_ONE_ID));
    }

    @Test
    @DisplayName("Test get all cargoes")
    void testGetAllCargoes() {
        List<Cargo> expectedCargoes = new ArrayList<>();

        expectedCargoes.add(cargoOne);
        expectedCargoes.add(cargoTwo);

        when(cargoDao.getAllCargoes()).thenReturn(expectedCargoes);

        List<CargoDto> expectedCargoesDto = expectedCargoes.stream()
                .map(cargo -> modelMapper.map(cargo, CargoDto.class))
                .collect(Collectors.toList());
        List<CargoDto> actualCargoes = cargoService.getAllCargoes();

        Assertions.assertEquals(expectedCargoesDto, actualCargoes);
    }

    @Test
    @DisplayName("Test create cargo success")
    void testCreateCargoSuccess() {
        cargoService.createCargo(modelMapper.map(cargoOne, CargoDto.class));

        verify(cargoDao, times(1))
                .createCargo(any(Cargo.class));
    }

    @Test
    @DisplayName("Test create cargo failed")
    void testCreateCargoFailed() {
        when(cargoDao.cargoExistsById(CARGO_ONE_ID)).thenReturn(true);

        CargoDto newCargoDto = modelMapper.map(cargoOne,
                CargoDto.class);

        Assertions.assertThrows(ServiceException.class,
                () -> cargoService.createCargo(newCargoDto));
    }

    @Test
    @DisplayName("Test update cargo success")
    void testUpdateCargoSuccess() {
        cargoService.updateCargo(modelMapper.map(cargoOne, CargoDto.class));

        verify(cargoDao, times(1))
                .updateCargo(any(Cargo.class));
    }

    @Test
    @DisplayName("Test update cargo failed")
    void testUpdateCargoFailed() {

        doThrow(new NoResultException()).when(cargoDao).updateCargo(cargoOne);

        CargoDto existingCargoDto = modelMapper.map(cargoOne,
                CargoDto.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> cargoService.updateCargo(existingCargoDto));
    }

    @Test
    @DisplayName("Test delete cargo success")
    void testDeleteCargoSuccess() {
        cargoService.deleteCargo(CARGO_ONE_ID);

        verify(cargoDao, times(1))
                .deleteCargo(CARGO_ONE_ID);
    }

}
