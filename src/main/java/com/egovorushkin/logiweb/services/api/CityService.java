package com.egovorushkin.logiweb.services.api;

import com.egovorushkin.logiweb.dto.CityDto;
import java.util.List;

/**
 * Service interface for {@link com.egovorushkin.logiweb.entities.City}
 */
public interface CityService {

    CityDto getCityById(Long id);

    List<CityDto> getAllCities();

}
