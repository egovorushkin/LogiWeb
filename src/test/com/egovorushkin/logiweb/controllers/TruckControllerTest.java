package com.egovorushkin.logiweb.controllers;


import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.TruckDto;
import com.egovorushkin.logiweb.entities.enums.DriverStatus;
import com.egovorushkin.logiweb.entities.enums.TruckState;
import com.egovorushkin.logiweb.entities.enums.TruckStatus;
import com.egovorushkin.logiweb.exceptions.EntityNotFoundException;
import com.egovorushkin.logiweb.services.api.CityService;
import com.egovorushkin.logiweb.services.api.DriverService;
import com.egovorushkin.logiweb.services.api.TruckService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static com.egovorushkin.logiweb.config.WebTestConfig.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TruckControllerTest {

    private TruckRequestBuilder requestBuilder;
    private TruckService truckService;

    @BeforeEach
    void configureSystemUnderTest() {
        truckService = mock(TruckService.class);
        DriverService driverService = mock(DriverService.class);
        CityService cityService = mock(CityService.class);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new TruckController(truckService, cityService, driverService))
                .setHandlerExceptionResolvers(exceptionResolver())
                .setLocaleResolver(fixedLocalResolver())
                .setViewResolvers(jspViewResolver())
                .build();
        requestBuilder = new TruckRequestBuilder(mockMvc);
    }

    @Nested
    @DisplayName("Render the HTML view that displays the information of all Trucks")
    class FindAll {

        @Test
        @DisplayName("Should return the HTTP status code 200")
        void testShouldReturnHttpStatusCodeOk() throws Exception {
            requestBuilder.findAll().andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should render the Truck list view")
        void testShouldRenderTruckListView() throws Exception {
            requestBuilder.findAll().andExpect(view().name("manager/truck/list"));
        }

        @Nested
        @DisplayName("When no trucks is found from the database")
        class WhenNoTrucksIsFoundFromDatabase {

            @BeforeEach
            void serviceReturnsEmptyList() {
                given(truckService.getAllTrucks()).willReturn(new ArrayList<>());
            }

            @Test
            @DisplayName("Should display zero trucks")
            void testShouldDisplayZeroTrucks() throws Exception {
                requestBuilder.findAll().andExpect(model().attribute("trucks", hasSize(0)));
            }
        }

        @Nested
        @DisplayName("When two trucks are found from the database")
        class WhenTwoTrucksAreFoundFromTheDatabase {

            private final Long TRUCK_ONE_ID = 1L;
            private final String TRUCK_ONE_REGISTRATION_NUMBER = "AB12345";
            private final int TRUCK_ONE_TEAM_SIZE = 2;
            private final int TRUCK_ONE_CAPACITY = 30000;
            private final int TRUCK_ONE_CURRENT_NUMBER_OF_DRIVERS = 0;
            private final TruckStatus STATUS_PARKED = TruckStatus.PARKED;
            private final TruckState STATE_SERVICEABLE = TruckState.SERVICEABLE;

            private final Long TRUCK_TWO_ID = 2L;
            private final String TRUCK_TWO_REGISTRATION_NUMBER = "NB00432";
            private final int TRUCK_TWO_TEAM_SIZE = 2;
            private final int TRUCK_TWO_CAPACITY = 25000;
            private final int TRUCK_TWO_CURRENT_NUMBER_OF_DRIVERS = 0;
            private final TruckStatus STATUS_ON_THE_WAY = TruckStatus.ON_THE_WAY;
            private final TruckState STATE_FAULTY = TruckState.FAULTY;

            @BeforeEach
            void truckServiceReturnTwoTrucks() {
                TruckDto first = new TruckDto();
                first.setId(TRUCK_ONE_ID);
                first.setRegistrationNumber(TRUCK_ONE_REGISTRATION_NUMBER);
                first.setTeamSize(TRUCK_ONE_TEAM_SIZE);
                first.setCapacity(TRUCK_ONE_CAPACITY);
                first.setStatus(STATUS_PARKED);
                first.setState(STATE_SERVICEABLE);

                TruckDto second = new TruckDto();
                second.setId(TRUCK_TWO_ID);
                second.setRegistrationNumber(TRUCK_TWO_REGISTRATION_NUMBER);
                second.setTeamSize(TRUCK_TWO_TEAM_SIZE);
                second.setCapacity(TRUCK_TWO_CAPACITY);
                second.setStatus(STATUS_ON_THE_WAY);
                second.setState(STATE_FAULTY);

                given(truckService.getAllTrucks()).willReturn(Arrays.asList(first, second));
            }

            @Test
            @DisplayName("Should display two trucks")
            void testShouldDisplayTwoTrucks() throws Exception {
                requestBuilder.findAll().andExpect(model().attribute("trucks", hasSize(2)));
            }

            /**
             * These two tests ensure that the list view displays the information
             * of the found trucks but they don't guarantee that trucks
             * are displayed in the correct order
             */
            @Test
            @DisplayName("Should display the information of the first truck")
            void testShouldDisplayInformationOfFirstTruck() throws Exception {
                requestBuilder.findAll()
                        .andExpect(
                                model().attribute("trucks",
                                hasItem(allOf(
                                        hasProperty("id", equalTo(TRUCK_ONE_ID)),
                                        hasProperty("registrationNumber", equalTo(TRUCK_ONE_REGISTRATION_NUMBER)),
                                        hasProperty("teamSize", equalTo(TRUCK_ONE_TEAM_SIZE)),
                                        hasProperty("capacity", equalTo(TRUCK_ONE_CAPACITY)),
                                        hasProperty("currentNumberOfDrivers", equalTo(TRUCK_ONE_CURRENT_NUMBER_OF_DRIVERS)),
                                        hasProperty("status", equalTo(STATUS_PARKED)),
                                        hasProperty("state", equalTo(STATE_SERVICEABLE))
                                        )))
                        );
            }

            @Test
            @DisplayName("Should display the information of the second truck")
            void testShouldDisplayInformationOfSecondTruck() throws Exception {
                requestBuilder.findAll()
                        .andExpect(
                                model().attribute("trucks",
                                        hasItem(allOf(
                                                hasProperty("id", equalTo(TRUCK_TWO_ID)),
                                                hasProperty("registrationNumber", equalTo(TRUCK_TWO_REGISTRATION_NUMBER)),
                                                hasProperty("teamSize", equalTo(TRUCK_TWO_TEAM_SIZE)),
                                                hasProperty("capacity", equalTo(TRUCK_TWO_CAPACITY)),
                                                hasProperty("currentNumberOfDrivers", equalTo(TRUCK_TWO_CURRENT_NUMBER_OF_DRIVERS)),
                                                hasProperty("status", equalTo(STATUS_ON_THE_WAY)),
                                                hasProperty("state", equalTo(STATE_FAULTY))
                                        )))
                        );
            }

            /**
             * This test ensures that the list view displays the information
             * of the found trucks in the correct order.
             */
            @Test
            @DisplayName("Should display the information of the first and second trucks in the correct order")
            void testShouldDisplayFirstAndSecondTrucksInCorrectOrder() throws Exception {
                requestBuilder.findAll()
                        .andExpect(
                                model().attribute("trucks",
                                        contains(
                                                allOf(
                                                        hasProperty("id", equalTo(TRUCK_ONE_ID)),
                                                        hasProperty("registrationNumber", equalTo(TRUCK_ONE_REGISTRATION_NUMBER)),
                                                        hasProperty("teamSize", equalTo(TRUCK_ONE_TEAM_SIZE)),
                                                        hasProperty("capacity", equalTo(TRUCK_ONE_CAPACITY)),
                                                        hasProperty("currentNumberOfDrivers", equalTo(TRUCK_ONE_CURRENT_NUMBER_OF_DRIVERS)),
                                                        hasProperty("status", equalTo(STATUS_PARKED)),
                                                        hasProperty("state", equalTo(STATE_SERVICEABLE))
                                                ),
                                                allOf(
                                                        hasProperty("id", equalTo(TRUCK_TWO_ID)),
                                                        hasProperty("registrationNumber", equalTo(TRUCK_TWO_REGISTRATION_NUMBER)),
                                                        hasProperty("teamSize", equalTo(TRUCK_TWO_TEAM_SIZE)),
                                                        hasProperty("capacity", equalTo(TRUCK_TWO_CAPACITY)),
                                                        hasProperty("currentNumberOfDrivers", equalTo(TRUCK_TWO_CURRENT_NUMBER_OF_DRIVERS)),
                                                        hasProperty("status", equalTo(STATUS_ON_THE_WAY)),
                                                        hasProperty("state", equalTo(STATE_FAULTY))
                                                )
                                        ))
                        );
            }
        }

        @Nested
        @DisplayName("Render the HTML view that displays the information of the requested truck")
        class FindById {

            private final Long TRUCK_ID = 99999L;

            @Nested
            @DisplayName("When the requested truck isn't found from the database")
            class WhenRequestedTruckIsNotFound {

                @BeforeEach
                void truckServiceThrowsNotFoundException() {
                    given(truckService.getTruckById(TRUCK_ID)).willThrow(new EntityNotFoundException(""));
                }

                @Test
                @DisplayName("Should return HTTP status code 404")
                void testShouldReturnHttpStatusCodeNotFound() throws Exception {
                    requestBuilder.findById(TRUCK_ID).andExpect(status().isNotFound());
                }

                @Test
                @DisplayName("Should render the 404 view")
                void testShouldRRender404View() throws Exception {
                    requestBuilder.findById(TRUCK_ID).andExpect(view().name("error/404"));
                }
            }

            @Nested
            @DisplayName("When the requested truck is found from the database")
            class WhenRequestedTruckIsFound {

                private final String REGISTRATION_NUMBER = "XX00000";
                private final int TEAM_SIZE = 2;
                private final int CAPACITY = 25000;
                private final TruckStatus STATUS_PARKED = TruckStatus.PARKED;
                private final TruckState STATE_SERVICEABLE = TruckState.SERVICEABLE;

                private final Long DRIVER_ID = 10L;
                private final String DRIVER_FIRST_NAME = "Alex";
                private final String DRIVER_LAST_NAME = "Alexeev";
                private final int DRIVER_WORKED_HOURS = 100;
                private final DriverStatus DRIVER_RESTING = DriverStatus.RESTING;

                @BeforeEach
                void truckServiceReturnsParkedAndServiceableTruckWithOneDriver() {
                    TruckDto truck = new TruckDto();
                    truck.setId(TRUCK_ID);
                    truck.setRegistrationNumber(REGISTRATION_NUMBER);
                    truck.setTeamSize(TEAM_SIZE);
                    truck.setCapacity(CAPACITY);
                    truck.setStatus(STATUS_PARKED);
                    truck.setState(STATE_SERVICEABLE);

                    DriverDto driver = new DriverDto();
                    driver.setId(DRIVER_ID);
                    driver.setFirstName(DRIVER_FIRST_NAME);
                    driver.setLastName(DRIVER_LAST_NAME);
                    driver.setWorkedHoursPerMonth(DRIVER_WORKED_HOURS);
                    driver.setStatus(DRIVER_RESTING);

                    truck.setDrivers(Collections.singleton(driver));

                    given(truckService.getTruckById(TRUCK_ID)).willReturn(truck);
                }

                @Test
                @DisplayName("Should return the HTTP status code 200")
                void testShouldReturnHttpStatusCodeOk() throws Exception {
                    requestBuilder.findById(TRUCK_ID).andExpect(status().isOk());
                }

                @Test
                @DisplayName("Should render the view truck")
                void testShouldRenderViewTruckView() throws Exception {
                    requestBuilder.findById(TRUCK_ID).andExpect(view()
                            .name("manager/truck/show"));
                }

                @Test
                @DisplayName("Should display the information of the correct truck")
                void testShouldDisplayInformationOfCorrectTruck() throws Exception {
                    requestBuilder.findById(TRUCK_ID)
                            .andExpect(model().attribute(
                                    "truckDto",
                                    hasProperty("id", equalTo(TRUCK_ID))
                            ));
                }

                @Test
                @DisplayName("Should display the correct information of truck")
                void testShouldDisplayCorrectInformationOfTruck() throws Exception {
                    requestBuilder.findById(TRUCK_ID)
                            .andExpect(model().attribute(
                                    "truckDto",
                                    allOf(
                                            hasProperty("registrationNumber", equalTo(REGISTRATION_NUMBER)),
                                            hasProperty("teamSize", equalTo(TEAM_SIZE)),
                                            hasProperty("capacity", equalTo(CAPACITY)),
                                            hasProperty("status", equalTo(STATUS_PARKED)),
                                            hasProperty("state", equalTo(STATE_SERVICEABLE))
                                    )
                            ));
                }

                @Test
                @DisplayName("Should display a parked truck")
                void testShouldDisplayParkedTruck() throws Exception {
                    requestBuilder.findById(TRUCK_ID)
                            .andExpect(model().attribute(
                                    "truckDto",
                                    hasProperty("status", equalTo(STATUS_PARKED))
                            ));
                }

                @Test
                @DisplayName("Should display a truck that has one driver")
                void testShouldDisplayTruckThanHasOneDriver() throws Exception {
                    requestBuilder.findById(TRUCK_ID)
                            .andExpect(model().attribute(
                                    "truckDto",
                                    hasProperty("currentDrivers", hasSize(1))
                            ));
                }

                @Test
                @DisplayName("Should display the information of the found driver")
                void testShouldDisplayInformationOfFoundDriver() throws Exception {
                    requestBuilder.findById(TRUCK_ID)
                            .andExpect(model().attribute(
                                    "truckDto",
                                    hasProperty("currentDrivers", hasItem(
                                            allOf(
                                                    hasProperty("id", equalTo(DRIVER_ID)),
                                                    hasProperty("firstName", equalTo(DRIVER_FIRST_NAME)),
                                                    hasProperty("lastName", equalTo(DRIVER_LAST_NAME)),
                                                    hasProperty("workedHoursPerMonth", equalTo(DRIVER_WORKED_HOURS)),
                                                    hasProperty("status", equalTo(DRIVER_RESTING))
                                            )
                                    ))
                            ));
                }
            }
        }
    }
}
