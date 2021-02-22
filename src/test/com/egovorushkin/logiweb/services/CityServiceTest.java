package com.egovorushkin.logiweb.services;

import com.egovorushkin.logiweb.dao.api.CityDao;
import com.egovorushkin.logiweb.dto.CityDto;
import com.egovorushkin.logiweb.entities.City;
import com.egovorushkin.logiweb.exceptions.EntityNotFoundException;
import com.egovorushkin.logiweb.services.api.CityService;
import com.egovorushkin.logiweb.services.impl.CityServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    private static final Long CITY_ONE_ID = 1L;
    private static final String CITY_ONE_NAME = "Saint-Petersburg";

    private static final Long CITY_TWO_ID = 2L;
    private static final String CITY_TWO_NAME = "Moscow";

    CityService cityService;

    @Mock
    private CityDao cityDao;
    private final City cityOne = new City();
    private final City cityTwo = new City();
    CityDto cityDto = new CityDto();
    ModelMapper modelMapper;

    @BeforeEach
    public void init() {
        modelMapper = new ModelMapper();
        cityService = new CityServiceImpl(cityDao, modelMapper);

        cityOne.setId(CITY_ONE_ID);
        cityOne.setName(CITY_ONE_NAME);

        cityTwo.setId(CITY_TWO_ID);
        cityTwo.setName(CITY_TWO_NAME);
    }

    @Test
    @DisplayName("Test getCityById success")
    void testGetCityByIdSuccess() {
        when(cityDao.getCityById(CITY_ONE_ID)).thenReturn(cityOne);
        cityDto = cityService.getCityById(CITY_ONE_ID);
        Assertions.assertEquals(cityOne.getId(), cityDto.getId());
        Assertions.assertEquals(modelMapper.map(cityOne, CityDto.class),
                cityDto);

    }

    @Test
    @DisplayName("Test getCityById failed")
    void testGetCityByIdFailed() {
        when(cityDao.getCityById(CITY_ONE_ID)).thenReturn(null);
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> cityService.getCityById(CITY_ONE_ID));
    }

    @Test
    @DisplayName("Test getAllCities")
    void testGetAllCities() {
        List<City> expectedCities = new ArrayList<>();
        expectedCities.add(cityOne);
        expectedCities.add(cityTwo);
        when(cityDao.getAllCities()).thenReturn(expectedCities);
        List<CityDto> expectedCitiesDto = expectedCities.stream()
                .map(city -> modelMapper.map(city, CityDto.class))
                .collect(Collectors.toList());
        List<CityDto> actualCities = cityService.getAllCities();
        Assertions.assertEquals(expectedCitiesDto, actualCities);
    }
}
