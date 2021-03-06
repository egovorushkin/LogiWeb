package com.egovorushkin.logiweb.services;

import com.egovorushkin.logiweb.dao.api.TruckDao;
import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.TruckDto;
import com.egovorushkin.logiweb.dto.TruckStatsDto;
import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.Truck;
import com.egovorushkin.logiweb.entities.enums.DriverStatus;
import com.egovorushkin.logiweb.entities.enums.TruckState;
import com.egovorushkin.logiweb.entities.enums.TruckStatus;
import com.egovorushkin.logiweb.exceptions.EntityNotFoundException;
import com.egovorushkin.logiweb.exceptions.ServiceException;
import com.egovorushkin.logiweb.services.api.DriverService;
import com.egovorushkin.logiweb.services.api.ScoreboardService;
import com.egovorushkin.logiweb.services.api.TruckService;
import com.egovorushkin.logiweb.services.impl.TruckServiceImpl;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TruckServiceTest {

    private static final Long TRUCK_ONE_ID = 1L;
    private static final String TRUCK_ONE_REG_NUMBER = "AB12345";
    private static final int TRUCK_ONE_TEAM_SIZE = 2;
    private static final int TRUCK_ONE_CAPACITY = 30000;
    private static final TruckStatus STATUS_PARKED = TruckStatus.PARKED;
    private static final TruckState STATE_SERVICEABLE = TruckState.SERVICEABLE;

    private static final Long TRUCK_TWO_ID = 2L;
    private static final String TRUCK_TWO_REG_NUMBER = "NB00432";
    private static final int TRUCK_TWO_TEAM_SIZE = 2;
    private static final int TRUCK_TWO_CAPACITY = 25000;
    private static final TruckStatus STATUS_ON_THE_WAY = TruckStatus.ON_THE_WAY;
    private static final TruckState STATE_FAULTY = TruckState.FAULTY;

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

    private static final long TOTAL = 2;
    private static final long AVAILABLE = 0;
    private static final long BUSY = 1;
    private static final long FAULTY = 1;

    TruckService truckService;

    @Mock
    private TruckDao truckDao;
    private final Truck truckOne = new Truck();
    private final Truck truckTwo = new Truck();
    private final Driver driverOne = new Driver();
    private final Driver driverTwo = new Driver();
    private final List<Driver> expectedDrivers = new ArrayList<>();
    private final List<Truck> expectedTrucks = new ArrayList<>();
    private final TruckStatsDto expectedTruckStatsDto = new TruckStatsDto();
    TruckDto truckDto = new TruckDto();
    Mapper mapper;

    @BeforeEach
    public void init() {
        ScoreboardService scoreboardService =
                Mockito.mock(ScoreboardService.class);
        DriverService driverService = Mockito.mock(DriverService.class);
        mapper = new DozerBeanMapper();
        truckService = new TruckServiceImpl(truckDao, driverService, mapper,
                scoreboardService);

        truckOne.setId(TRUCK_ONE_ID);
        truckOne.setRegistrationNumber(TRUCK_ONE_REG_NUMBER);
        truckOne.setTeamSize(TRUCK_ONE_TEAM_SIZE);
        truckOne.setCapacity(TRUCK_ONE_CAPACITY);
        truckOne.setStatus(STATUS_PARKED);
        truckOne.setState(STATE_SERVICEABLE);

        truckTwo.setId(TRUCK_TWO_ID);
        truckTwo.setRegistrationNumber(TRUCK_TWO_REG_NUMBER);
        truckTwo.setTeamSize(TRUCK_TWO_TEAM_SIZE);
        truckTwo.setCapacity(TRUCK_TWO_CAPACITY);
        truckTwo.setStatus(STATUS_ON_THE_WAY);
        truckTwo.setState(STATE_FAULTY);

        driverOne.setId(DRIVER_ONE_ID);
        driverOne.setUsername(DRIVER_ONE_USERNAME);
        driverOne.setFirstName(DRIVER_ONE_FIRST_NAME);
        driverOne.setLastName(DRIVER_ONE_LAST_NAME);
        driverOne.setWorkedHoursPerMonth(DRIVER_ONE_WORKED_HOURS);
        driverOne.setStatus(STATUS_DRIVING);

        driverTwo.setId(DRIVER_TWO_ID);
        driverTwo.setUsername(DRIVER_TWO_USERNAME);
        driverTwo.setFirstName(DRIVER_TWO_FIRST_NAME);
        driverTwo.setLastName(DRIVER_TWO_LAST_NAME);
        driverTwo.setWorkedHoursPerMonth(DRIVER_TWO_WORKED_HOURS);
        driverTwo.setStatus(STATUS_RESTING);

        expectedTruckStatsDto.setTotal(TOTAL);
        expectedTruckStatsDto.setAvailable(AVAILABLE);
        expectedTruckStatsDto.setBusy(BUSY);
        expectedTruckStatsDto.setFaulty(FAULTY);

        expectedDrivers.add(driverOne);
        expectedDrivers.add(driverTwo);

        expectedTrucks.add(truckOne);
        expectedTrucks.add(truckTwo);
    }

    @Test
    @DisplayName("Test get truck by id success")
    void testGetTruckByIdSuccess() {
        when(truckDao.getTruckById(TRUCK_ONE_ID)).thenReturn(truckOne);

        truckDto = truckService.getTruckById(TRUCK_ONE_ID);

        Assertions.assertEquals(mapper.map(truckOne, TruckDto.class),
                truckDto);
    }

    @Test
    @DisplayName("Test get truck by id failed")
    void testGetTruckByIdFailed() {
        when(truckDao.getTruckById(TRUCK_ONE_ID)).thenReturn(null);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> truckService.getTruckById(TRUCK_ONE_ID));
    }

    @Test
    @DisplayName("Test get all trucks")
    void testGetAllTrucks() {
        List<Truck> expectedTrucks = new ArrayList<>();

        expectedTrucks.add(truckOne);
        expectedTrucks.add(truckTwo);

        when(truckDao.getAllTrucks()).thenReturn(expectedTrucks);

        List<TruckDto> expectedTrucksDto = expectedTrucks.stream()
                .map(truck -> mapper.map(truck, TruckDto.class))
                .collect(Collectors.toList());
        List<TruckDto> actualTrucks = truckService.getAllTrucks();

        Assertions.assertEquals(expectedTrucksDto, actualTrucks);
    }

    @Test
    @DisplayName("Test create truck success")
    void testCreateTruckSuccess() {
        truckService.createTruck(mapper.map(truckOne, TruckDto.class));

        verify(truckDao, times(1))
                .createTruck(any(Truck.class));
    }

    @Test
    @DisplayName("Test create truck failed")
    void testCreateTruckFailed() {
        when(truckDao.truckExistsByRegistrationNumber(TRUCK_ONE_REG_NUMBER))
                .thenReturn(true);

        TruckDto newTruckDto = mapper.map(truckOne, TruckDto.class);

        Assertions.assertThrows(ServiceException.class,
                () -> truckService.createTruck(newTruckDto));
    }

    @Test
    @DisplayName("Test update truck success")
    void testUpdateTruckSuccess() {
        truckService.updateTruck(mapper.map(truckOne, TruckDto.class));

        verify(truckDao, times(1))
                .updateTruck(any(Truck.class));
    }

    @Test
    @DisplayName("Test update truck failed")
    void testUpdateTruckFailed() {
        doThrow(new NoResultException()).when(truckDao).updateTruck(truckOne);

        TruckDto existingTruckDto = mapper.map(truckOne, TruckDto.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> truckService.updateTruck(existingTruckDto));
    }

    @Test
    @DisplayName("Test delete truck success")
    void testDeleteTruckSuccess() {
        truckService.deleteTruck(TRUCK_ONE_ID);

        verify(truckDao, times(1))
                .deleteTruck(TRUCK_ONE_ID);
    }

    @Test
    @DisplayName("Test find available drivers by truck success")
    void testFindAvailableDriversByTruckSuccess() {
        when(truckDao.findAvailableDriversByTruck(truckOne))
                .thenReturn(expectedDrivers);

        truckDto = mapper.map(truckOne, TruckDto.class);
        List<DriverDto> expectedDriversDto = expectedDrivers.stream()
                .map(driver -> mapper.map(driver, DriverDto.class))
                .collect(Collectors.toList());
        List<DriverDto> actualDriversDto =
                truckService.findAvailableDriversByTruck(truckDto);

        Assertions.assertEquals(expectedDriversDto, actualDriversDto);
    }

    @Test
    @DisplayName("Test find truck by registration number success")
    void testFindTruckByRegistrationNumberSuccess() {
        when(truckDao.findByRegistrationNumber(TRUCK_TWO_REG_NUMBER))
                .thenReturn(truckTwo);

        Truck expectedTruck =
                truckService.findByRegistrationNumber(TRUCK_TWO_REG_NUMBER);

        Assertions.assertEquals(expectedTruck, truckTwo);
    }

    @Test
    @DisplayName("Test get stats success")
    void testGetStatsSuccess() {
        when(truckDao.getAllTrucks()).thenReturn(expectedTrucks );

        TruckStatsDto actualTruckStatsDto = truckService.getStats();

        Assertions.assertEquals(expectedTruckStatsDto, actualTruckStatsDto);
    }

}
