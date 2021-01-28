package com.egovorushkin.logiweb.services.api;

import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.TruckDto;
import java.util.List;

public interface DriverService {

    DriverDto getDriverById(long id);

    List<DriverDto> getAllDrivers();

    void createDriver(DriverDto driverDto);

    void updateDriver(DriverDto driverDto);

    void deleteDriver(long id);

    DriverDto getAuthorizedDriverByUsername();

    List<TruckDto> findAvailableTrucksByDriver(DriverDto driverDto);

//    List<DriverDto> findColleaguesAuthorizedDriverByUsername();

    DriverDto findColleagueAuthorizedDriverByUsername();

    void mergeWithExistingAndUpdate(DriverDto driverDto);
}
