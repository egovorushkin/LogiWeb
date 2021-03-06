package com.egovorushkin.logiweb.services.api;

import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.DriverStatsDto;
import com.egovorushkin.logiweb.dto.TruckDto;
import com.egovorushkin.logiweb.entities.enums.DriverStatus;

import java.util.List;

/**
 * Service interface for {@link com.egovorushkin.logiweb.entities.Driver}
 */
public interface DriverService {

    DriverDto getDriverById(Long id);

    List<DriverDto> getAllDrivers();

    void createDriver(DriverDto driverDto);

    void updateDriver(DriverDto driverDto);

    void deleteDriver(Long id);

    /**
     * This method returns the currently logged driver
     * @return {@link DriverDto} currently logged driver
     */
    DriverDto getAuthorizedDriverByUsername();

    /**
     * This method finds available trucks for given driver
     * @param driverDto this is given driver
     * @return {@link List<TruckDto>} available trucks
     */
    List<TruckDto> findAvailableTrucksByDriver(DriverDto driverDto);

    /**
     * This method finds a colleague of currently logged driver
     * @return {@link DriverDto} colleague
     */
    DriverDto findColleagueAuthorizedDriverByUsername();

    void updateStatus(DriverStatus driverStatus);

    void updateState(boolean userState);

    /**
     * This method returns drivers statistics
     * like total, available and not available drivers
     * @return {@link DriverStatsDto} driver statistic
     */
    DriverStatsDto getStats();

    List<DriverDto> listAllByPage(int firstResult, int maxResult);

    Long totalCount();
}
