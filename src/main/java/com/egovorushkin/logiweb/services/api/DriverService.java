package com.egovorushkin.logiweb.services.api;

import com.egovorushkin.logiweb.dto.DriverDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DriverService {

    DriverDto getDriverById(long id);

    List<DriverDto> getAllDrivers();

    void createDriver(DriverDto driverDto);

    void updateDriver(DriverDto driverDto);

    void deleteDriver(long id);

    DriverDto getDriverByUsername();
}
