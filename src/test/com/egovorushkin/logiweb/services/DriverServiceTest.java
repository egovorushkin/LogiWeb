package com.egovorushkin.logiweb.services;

import com.egovorushkin.logiweb.config.security.IAuthenticationFacade;
import com.egovorushkin.logiweb.dao.api.DriverDao;
import com.egovorushkin.logiweb.dao.api.OrderDao;
import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.DriverStatsDto;
import com.egovorushkin.logiweb.dto.TruckDto;
import com.egovorushkin.logiweb.entities.Cargo;
import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.Order;
import com.egovorushkin.logiweb.entities.Truck;
import com.egovorushkin.logiweb.entities.enums.*;
import com.egovorushkin.logiweb.exceptions.EntityNotFoundException;
import com.egovorushkin.logiweb.exceptions.ServiceException;
import com.egovorushkin.logiweb.services.api.ScoreboardService;
import com.egovorushkin.logiweb.services.api.DriverService;
import com.egovorushkin.logiweb.services.api.TruckService;
import com.egovorushkin.logiweb.services.impl.DriverServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DriverServiceTest {

    private static final Long DRIVER_ONE_ID = 6L;
    private static final String DRIVER_ONE_USERNAME = "driver1";
    private static final String DRIVER_ONE_FIRST_NAME = "Ivan";
    private static final String DRIVER_ONE_LAST_NAME = "Ivanov";
    private static final int DRIVER_ONE_WORKED_HOURS = 100;
    private static final DriverStatus STATUS_DRIVING = DriverStatus.DRIVING;

    private static final Long DRIVER_TWO_ID = 9L;
    private static final String DRIVER_TWO_USERNAME = "driver2";
    private static final String DRIVER_TWO_FIRST_NAME = "Alex";
    private static final String DRIVER_TWO_LAST_NAME = "Alexeev";
    private static final int DRIVER_TWO_WORKED_HOURS = 150;
    private static final DriverStatus STATUS_RESTING = DriverStatus.RESTING;

    private static final Long TRUCK_ONE_ID = 1L;
    private static final String TRUCK_ONE_REGISTRATION_NUMBER = "AB12345";
    private static final int TRUCK_ONE_TEAM_SIZE = 2;
    private static final int TRUCK_ONE_CAPACITY = 30000;
    private static final TruckStatus STATUS_PARKED = TruckStatus.PARKED;
    private static final TruckState STATE_SERVICEABLE = TruckState.SERVICEABLE;

    private static final Long TRUCK_TWO_ID = 2L;
    private static final String TRUCK_TWO_REGISTRATION_NUMBER = "NB00432";
    private static final int TRUCK_TWO_TEAM_SIZE = 2;
    private static final int TRUCK_TWO_CAPACITY = 25000;
    private static final TruckStatus STATUS_ON_THE_WAY = TruckStatus.ON_THE_WAY;
    private static final TruckState STATE_FAULTY = TruckState.FAULTY;

    private static final Long ORDER_ID = 2L;
    private static final String ORDER_FROM_CITY = "Moscow";
    private static final String ORDER_TO_CITY = "Orel";
    private static final Integer ORDER_DISTANCE = 364;
    private static final Integer ORDER_DURATION = 5;
    private static final OrderStatus ORDER_STATUS_NOT_COMPLETED =
            OrderStatus.NOT_COMPLETED;

    private static final Long CARGO_ONE_ID = 1L;
    private static final String CARGO_ONE_NAME = "Laptops";
    private static final int CARGO_ONE_WEIGHT = 5000;
    private static final CargoStatus CARGO_ONE_STATUS_PREPARED =
            CargoStatus.PREPARED;

    private static final long TOTAL = 2;
    private static final long AVAILABLE = 2;
    private static final long NOT_AVAILABLE = 0;

    DriverService driverService;

    @Mock
    private DriverDao driverDao;
    private OrderDao orderDao;
    private final Driver driverOne = new Driver();
    private final Driver driverTwo = new Driver();
    private final Truck truckOne = new Truck();
    private final Truck truckTwo = new Truck();
    private final Cargo cargoOne = new Cargo();
    private final Order order = new Order();
    DriverDto driverDto = new DriverDto();
    private final List<Driver> expectedDrivers = new ArrayList<>();
    private final List<Truck> expectedTrucks = new ArrayList<>();
    private final DriverStatsDto expectedDriverStatsDto = new DriverStatsDto();
    ModelMapper modelMapper;
    Authentication authentication;
    IAuthenticationFacade authenticationFacade;
    TruckService truckService;


    @BeforeEach
    public void init() {
        ScoreboardService scoreboardService =
                Mockito.mock(ScoreboardService.class);
        orderDao = Mockito.mock(OrderDao.class);
        truckService = Mockito.mock(TruckService.class);
        authenticationFacade =
                Mockito.mock(IAuthenticationFacade.class);
        authentication = Mockito.mock(Authentication.class);
        modelMapper = new ModelMapper();

        driverService = new DriverServiceImpl(driverDao, truckService,
                modelMapper, authenticationFacade, scoreboardService, orderDao);

        driverOne.setId(DRIVER_ONE_ID);
        driverOne.setUsername(DRIVER_ONE_USERNAME);
        driverOne.setFirstName(DRIVER_ONE_FIRST_NAME);
        driverOne.setLastName(DRIVER_ONE_LAST_NAME);
        driverOne.setWorkedHoursPerMonth(DRIVER_ONE_WORKED_HOURS);
        driverOne.setTruck(truckOne);
        driverOne.setStatus(STATUS_DRIVING);

        driverTwo.setId(DRIVER_TWO_ID);
        driverTwo.setUsername(DRIVER_TWO_USERNAME);
        driverTwo.setFirstName(DRIVER_TWO_FIRST_NAME);
        driverTwo.setLastName(DRIVER_TWO_LAST_NAME);
        driverTwo.setWorkedHoursPerMonth(DRIVER_TWO_WORKED_HOURS);
        driverTwo.setStatus(STATUS_RESTING);

        truckOne.setId(TRUCK_ONE_ID);
        truckOne.setRegistrationNumber(TRUCK_ONE_REGISTRATION_NUMBER);
        truckOne.setTeamSize(TRUCK_ONE_TEAM_SIZE);
        truckOne.setCapacity(TRUCK_ONE_CAPACITY);
        truckOne.setStatus(STATUS_PARKED);
        truckOne.setState(STATE_SERVICEABLE);

        truckTwo.setId(TRUCK_TWO_ID);
        truckTwo.setRegistrationNumber(TRUCK_TWO_REGISTRATION_NUMBER);
        truckTwo.setTeamSize(TRUCK_TWO_TEAM_SIZE);
        truckTwo.setCapacity(TRUCK_TWO_CAPACITY);
        truckTwo.setStatus(STATUS_ON_THE_WAY);
        truckTwo.setState(STATE_FAULTY);

        order.setId(ORDER_ID);
        order.setFromCity(ORDER_FROM_CITY);
        order.setToCity(ORDER_TO_CITY);
        order.setDistance(ORDER_DISTANCE);
        order.setDuration(ORDER_DURATION);
        order.setTruck(truckOne);
        order.setCargo(cargoOne);
        order.setStatus(ORDER_STATUS_NOT_COMPLETED);

        cargoOne.setId(CARGO_ONE_ID);
        cargoOne.setName(CARGO_ONE_NAME);
        cargoOne.setWeight(CARGO_ONE_WEIGHT);
        cargoOne.setStatus(CARGO_ONE_STATUS_PREPARED);

        expectedDrivers.add(driverOne);
        expectedDrivers.add(driverTwo);

        expectedTrucks.add(truckOne);
        expectedTrucks.add(truckTwo);

        expectedDriverStatsDto.setTotal(TOTAL);
        expectedDriverStatsDto.setAvailable(AVAILABLE);
        expectedDriverStatsDto.setNotAvailable(NOT_AVAILABLE);
    }

    @Test
    @DisplayName("Test get driver by id success")
    void testGetDriverByIdSuccess() {
        when(driverDao.getDriverById(DRIVER_ONE_ID)).thenReturn(driverOne);

        driverDto = driverService.getDriverById(DRIVER_ONE_ID);

        Assertions.assertEquals(modelMapper.map(driverOne, DriverDto.class),
                driverDto);
    }

    @Test
    @DisplayName("Test get driver by id failed")
    void testGetDriverByIdFailed() {
        when(driverDao.getDriverById(DRIVER_ONE_ID)).thenReturn(null);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> driverService.getDriverById(DRIVER_ONE_ID));
    }

    @Test
    @DisplayName("Test get all drivers")
    void testGetAllDrivers() {
        List<Driver> expectedDrivers = new ArrayList<>();

        expectedDrivers.add(driverOne);
        expectedDrivers.add(driverTwo);

        when(driverDao.getAllDrivers()).thenReturn(expectedDrivers);

        List<DriverDto> expectedDriversDto = expectedDrivers.stream()
                .map(driver -> modelMapper.map(driver, DriverDto.class))
                .collect(Collectors.toList());
        List<DriverDto> actualDrivers = driverService.getAllDrivers();

        Assertions.assertEquals(expectedDriversDto, actualDrivers);
    }

    @Test
    @DisplayName("Test save driver success")
    void testSaveDriverSuccess() {
        driverService.createDriver(modelMapper.map(driverOne, DriverDto.class));

        verify(driverDao, times(1))
                .saveDriver(any(Driver.class));
    }

    @Test
    @DisplayName("Test create driver failed")
    void testCreateDriverFailed() {
        when(driverDao.driverExistsById(DRIVER_ONE_ID)).thenReturn(true);

        DriverDto newDriverDto = modelMapper.map(driverOne, DriverDto.class);

        Assertions.assertThrows(ServiceException.class,
                () -> driverService.createDriver(newDriverDto));
    }

    @Test
    @DisplayName("Test update driver success")
    void testUpdateDriverSuccess() {
        driverService.updateDriver(modelMapper.map(driverOne, DriverDto.class));

        verify(driverDao, times(1))
                .updateDriver(any(Driver.class));
    }

    @Test
    @DisplayName("Test update driver failed")
    void testUpdateDriverFailed() {
        doThrow(new NoResultException()).when(driverDao).updateDriver(driverOne);

        DriverDto existingDriverDto = modelMapper.map(driverOne,
                DriverDto.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> driverService.updateDriver(existingDriverDto));
    }

    @Test
    @DisplayName("Test delete driver success")
    void testDeleteDriverSuccess() {
        driverService.deleteDriver(DRIVER_ONE_ID);

        verify(driverDao, times(1))
                .deleteDriver(DRIVER_ONE_ID);
    }

    @Test
    @DisplayName("Test find available trucks by driver success")
    void testFindAvailableTrucksByDriverSuccess() {
        when(driverDao.findAvailableTrucksByDriver(driverOne))
                .thenReturn(expectedTrucks);

        driverDto = modelMapper.map(driverOne, DriverDto.class);
        List<TruckDto> expectedTrucksDto = expectedTrucks.stream()
                .map(truck -> modelMapper.map(truck, TruckDto.class))
                .collect(Collectors.toList());
        List<TruckDto> actualTrucksDto = driverService
                .findAvailableTrucksByDriver(driverDto);

        Assertions.assertEquals(expectedTrucksDto, actualTrucksDto);
    }

    @Test
    @DisplayName("Test get stats success")
    void testGetStatsSuccess() {
        when(driverDao.getAllDrivers()).thenReturn(expectedDrivers);

        DriverStatsDto actualDriverStatsDto = driverService.getStats();

        Assertions.assertEquals(expectedDriverStatsDto, actualDriverStatsDto);
    }


    @Test
    @DisplayName("Test update status \"DRIVING\", \"SECOND DRIVER\", \"LOADING" +
            " UNLOADING\" and \"RESTING\" if colleague is null success")
    void testUpdateStatusDrivingSecondDriverLoadingUnloadingRestingIfColleagueIsNullSuccess() {
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(driverOne.getUsername());
        when(driverDao.getDriverByUsername(driverOne.getUsername()))
                .thenReturn(driverOne);

        when(orderDao.findOrderByTruckId(TRUCK_ONE_ID)).thenReturn(order);

        driverService.updateStatus(DriverStatus.DRIVING);
        driverService.updateStatus(DriverStatus.SECOND_DRIVER);
        driverService.updateStatus(DriverStatus.LOADING_UNLOADING);
        driverService.updateStatus(DriverStatus.RESTING);

        verify(driverDao, times(4))
                .updateDriver(driverOne);
    }

    @Test
    @DisplayName("Test get authorized driver by username success")
    void testGetAuthorizedDriverByUsername() {
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(driverOne.getUsername());
        when(driverDao.getDriverByUsername(driverOne.getUsername()))
                .thenReturn(driverOne);

        DriverDto expectedDriver = modelMapper.map(driverOne, DriverDto.class);
        DriverDto actualDriver = driverService.getAuthorizedDriverByUsername();

        Assertions.assertEquals(expectedDriver, actualDriver);
    }

    // TODO: need to finish the test
//    @Test
//    @DisplayName("Test find colleague authorized driver by username success")
//    void testFindColleagueAuthorizedDriverByUsername() {
//        when(driverService.getAuthorizedDriverByUsername())
//                .thenReturn(modelMapper.map(driverTwo, DriverDto.class));
//    }
}
