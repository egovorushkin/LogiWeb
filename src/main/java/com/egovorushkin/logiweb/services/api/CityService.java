package com.egovorushkin.logiweb.services.api;

import com.egovorushkin.logiweb.dto.CityDto;
import java.util.List;

public interface CityService {

    CityDto getCityById(long id);

    List<CityDto> getAllCities();

}
