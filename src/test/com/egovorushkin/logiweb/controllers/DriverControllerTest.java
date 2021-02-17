package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.TruckDto;
import com.egovorushkin.logiweb.entities.enums.DriverStatus;
import com.egovorushkin.logiweb.entities.enums.TruckState;
import com.egovorushkin.logiweb.entities.enums.TruckStatus;
import com.egovorushkin.logiweb.exceptions.EntityNotFoundException;
import com.egovorushkin.logiweb.services.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;

import static com.egovorushkin.logiweb.config.WebTestConfig.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DriverControllerTest {

    private DriverRequestBuilder requestBuilder;
    private DriverService driverService;

    @BeforeEach
    void configureSystemUnderTest() {
        driverService = mock(DriverService.class);
        TruckService truckService = mock(TruckService.class);
        CityService cityService = mock(CityService.class);
        UserService userService = mock(UserService.class);
        ScoreboardService scoreboardService = mock(ScoreboardService.class);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(
                new DriverController(driverService, truckService, cityService,
                        userService, scoreboardService))
                .setHandlerExceptionResolvers(exceptionResolver())
                .setLocaleResolver(fixedLocalResolver())
                .setViewResolvers(jspViewResolver())
                .build();
        requestBuilder = new DriverRequestBuilder(mockMvc);
    }

    @Nested
    @DisplayName("Render the HTML view that displays the information of all Drivers")
    class FindAll {

        @Test
        @DisplayName("Should return the HTTP status code 200")
        void shouldReturnHttpStatusCodeOk() throws Exception {
            requestBuilder.findAll().andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should render the Driver list view")
        void shouldRenderDriverListView() throws Exception {
            requestBuilder.findAll().andExpect(view().name("manager/driver/list"));
        }

        @Nested
        @DisplayName("When no drivers is found from the database")
        class WhenNoDriversIsFoundFromDatabase {

            @BeforeEach
            void serviceReturnsEmptyList() {
                given(driverService.getAllDrivers()).willReturn(new ArrayList<>());
            }

            @Test
            @DisplayName("Should display zero drivers")
            void shouldDisplayZeroDrivers() throws Exception {
                requestBuilder.findAll().andExpect(model().attribute("drivers", hasSize(0)));
            }
        }

        @Nested
        @DisplayName("When two drivers are found from the database")
        class WhenTwoDriversAreFoundFromTheDatabase {

            private final long DRIVER_ONE_ID = 1L;
            private final String DRIVER_ONE_USERNAME = "driver1";
            private final String DRIVER_ONE_FIRST_NAME = "Ivan";
            private final String DRIVER_ONE_LAST_NAME = "Ivanov";
            private final int DRIVER_ONE_WORKED_HOURS = 100;
            private final DriverStatus STATUS_DRIVING = DriverStatus.DRIVING;

            private final long DRIVER_TWO_ID = 3L;
            private final String DRIVER_TWO_USERNAME = "driver2";
            private final String DRIVER_TWO_FIRST_NAME = "Alex";
            private final String DRIVER_TWO_LAST_NAME = "Alexeev";
            private final int DRIVER_TWO_WORKED_HOURS = 150;
            private final DriverStatus STATUS_RESTING = DriverStatus.RESTING;

            @BeforeEach
            void driverServiceReturnTwoDrivers() {
                DriverDto first = new DriverDto();
                first.setId(DRIVER_ONE_ID);
                first.setUsername(DRIVER_ONE_USERNAME);
                first.setFirstName(DRIVER_ONE_FIRST_NAME);
                first.setLastName(DRIVER_ONE_LAST_NAME);
                first.setWorkedHoursPerMonth(DRIVER_ONE_WORKED_HOURS);
                first.setStatus(STATUS_DRIVING);

                DriverDto second = new DriverDto();
                second.setId(DRIVER_TWO_ID);
                second.setUsername(DRIVER_TWO_USERNAME);
                second.setFirstName(DRIVER_TWO_FIRST_NAME);
                second.setLastName(DRIVER_TWO_LAST_NAME);
                second.setWorkedHoursPerMonth(DRIVER_TWO_WORKED_HOURS);
                second.setStatus(STATUS_RESTING);


                given(driverService.getAllDrivers()).willReturn(Arrays.asList(first, second));
            }

            @Test
            @DisplayName("Should display two drivers")
            void shouldDisplayTwoDrivers() throws Exception {
                requestBuilder.findAll().andExpect(model().attribute("drivers", hasSize(2)));
            }

            /**
             * These two tests ensure that the list view displays the information
             * of the found drivers but they don't guarantee that drivers
             * are displayed in the correct order
             */
            @Test
            @DisplayName("Should display the information of the first driver")
            void shouldDisplayInformationOfFirstDriver() throws Exception {
                requestBuilder.findAll()
                        .andExpect(
                                model().attribute("drivers",
                                hasItem(allOf(
                                        hasProperty("id", equalTo(DRIVER_ONE_ID)),
                                        hasProperty("username", equalTo(DRIVER_ONE_USERNAME)),
                                        hasProperty("firstName", equalTo(DRIVER_ONE_FIRST_NAME)),
                                        hasProperty("lastName", equalTo(DRIVER_ONE_LAST_NAME)),
                                        hasProperty("workedHoursPerMonth", equalTo(DRIVER_ONE_WORKED_HOURS)),
                                        hasProperty("status", equalTo(STATUS_DRIVING))
                                        )))
                        );
            }

            @Test
            @DisplayName("Should display the information of the second driver")
            void shouldDisplayInformationOfSecondDriver() throws Exception {
                requestBuilder.findAll()
                        .andExpect(
                                model().attribute("drivers",
                                        hasItem(allOf(
                                                hasProperty("id", equalTo(DRIVER_TWO_ID)),
                                                hasProperty("username", equalTo(DRIVER_TWO_USERNAME)),
                                                hasProperty("firstName", equalTo(DRIVER_TWO_FIRST_NAME)),
                                                hasProperty("lastName", equalTo(DRIVER_TWO_LAST_NAME)),
                                                hasProperty("workedHoursPerMonth", equalTo(DRIVER_TWO_WORKED_HOURS)),
                                                hasProperty("status", equalTo(STATUS_RESTING))
                                        )))
                        );
            }

            /**
             * This test ensures that the list view displays the information
             * of the found drivers in the correct order.
             */
            @Test
            @DisplayName("Should display the information of the first and second drivers in the correct order")
            void shouldDisplayFirstAndSecondDriversInCorrectOrder() throws Exception {
                requestBuilder.findAll()
                        .andExpect(
                                model().attribute("drivers",
                                        contains(
                                                allOf(
                                                        hasProperty("id", equalTo(DRIVER_ONE_ID)),
                                                        hasProperty("username", equalTo(DRIVER_ONE_USERNAME)),
                                                        hasProperty("firstName", equalTo(DRIVER_ONE_FIRST_NAME)),
                                                        hasProperty("lastName", equalTo(DRIVER_ONE_LAST_NAME)),
                                                        hasProperty("workedHoursPerMonth", equalTo(DRIVER_ONE_WORKED_HOURS)),
                                                        hasProperty("status", equalTo(STATUS_DRIVING))
                                                ),
                                                allOf(
                                                        hasProperty("id", equalTo(DRIVER_TWO_ID)),
                                                        hasProperty("username", equalTo(DRIVER_TWO_USERNAME)),
                                                        hasProperty("firstName", equalTo(DRIVER_TWO_FIRST_NAME)),
                                                        hasProperty("lastName", equalTo(DRIVER_TWO_LAST_NAME)),
                                                        hasProperty("workedHoursPerMonth", equalTo(DRIVER_TWO_WORKED_HOURS)),
                                                        hasProperty("status", equalTo(STATUS_RESTING))
                                                )
                                        ))
                        );
            }
        }

        @Nested
        @DisplayName("Render the HTML view that displays the information of the requested driver")
        class FindById {

            private final long DRIVER_ID = 88888L;

            @Nested
            @DisplayName("When the requested driver isn't found from the database")
            class WhenRequestedDriverIsNotFound {

                @BeforeEach
                void driverServiceThrowsNotFoundException() {
                    given(driverService.getDriverById(DRIVER_ID)).willThrow(new EntityNotFoundException(""));
                }

                @Test
                @DisplayName("Should return HTTP status code 404")
                void shouldReturnHttpStatusCodeNotFound() throws Exception {
                    requestBuilder.findById(DRIVER_ID).andExpect(status().isNotFound());
                }

                @Test
                @DisplayName("Should render the 404 view")
                void shouldRender404View() throws Exception {
                    requestBuilder.findById(DRIVER_ID).andExpect(view().name("error/404"));
                }
            }

            @Nested
            @DisplayName("When the requested driver is found from the database")
            class WhenRequestedDriverIsFound {

                private final long DRIVER_ID = 10L;
                private final String DRIVER_FIRST_NAME = "Alex";
                private final String DRIVER_LAST_NAME = "Alexeev";
                private final String DRIVER_USERNAMME = "driver";
                private final int DRIVER_WORKED_HOURS = 100;
                private final DriverStatus DRIVER_RESTING = DriverStatus.RESTING;

                private final long TRUCK_ID = 10L;
                private final String REGISTRATION_NUMBER = "XX00000";
                private final int TEAM_SIZE = 2;
                private final int CAPACITY = 25000;
                private final TruckStatus STATUS_PARKED = TruckStatus.PARKED;
                private final TruckState STATE_SERVICEABLE = TruckState.SERVICEABLE;

                @BeforeEach
                void driverServiceReturnsRestingDriverWithTruck() {
                    DriverDto driver = new DriverDto();
                    driver.setId(DRIVER_ID);
                    driver.setFirstName(DRIVER_FIRST_NAME);
                    driver.setLastName(DRIVER_LAST_NAME);
                    driver.setUsername(DRIVER_USERNAMME);
                    driver.setWorkedHoursPerMonth(DRIVER_WORKED_HOURS);
                    driver.setStatus(DRIVER_RESTING);

                    TruckDto truck = new TruckDto();
                    truck.setId(TRUCK_ID);
                    truck.setRegistrationNumber(REGISTRATION_NUMBER);
                    truck.setTeamSize(TEAM_SIZE);
                    truck.setCapacity(CAPACITY);
                    truck.setStatus(STATUS_PARKED);
                    truck.setState(STATE_SERVICEABLE);

                    driver.setTruck(truck);

                    given(driverService.getDriverById(DRIVER_ID)).willReturn(driver);
                }

                @Test
                @DisplayName("Should return the HTTP status code 200")
                void shouldReturnHttpStatusCodeOk() throws Exception {
                    requestBuilder.findById(DRIVER_ID).andExpect(status().isOk());
                }

                @Test
                @DisplayName("Should render the view driver")
                void shouldRenderViewDriverView() throws Exception {
                    requestBuilder.findById(DRIVER_ID).andExpect(view().name("manager/driver/show"));
                }

                @Test
                @DisplayName("Should display the information of the correct driver")
                void
                shouldDisplayInformationOfCorrectDriver() throws Exception {
                    requestBuilder.findById(DRIVER_ID)
                            .andExpect(model().attribute(
                                    "driver",
                                    hasProperty("id", equalTo(DRIVER_ID))
                            ));
                }

                @Test
                @DisplayName("Should display the correct information of driver")
                void shouldDisplayCorrectInformationOfDriver() throws Exception {
                    requestBuilder.findById(DRIVER_ID)
                            .andExpect(model().attribute(
                                    "driver",
                                    allOf(
                                            hasProperty("id", equalTo(DRIVER_ID)),
                                            hasProperty("username", equalTo(DRIVER_USERNAMME)),
                                            hasProperty("firstName", equalTo(DRIVER_FIRST_NAME)),
                                            hasProperty("lastName", equalTo(DRIVER_LAST_NAME)),
                                            hasProperty("workedHoursPerMonth", equalTo(DRIVER_WORKED_HOURS)),
                                            hasProperty("status", equalTo(DRIVER_RESTING))
                                    )
                            ));
                }

                @Test
                @DisplayName("Should display a resting driver")
                void shouldDisplayParkedDriver() throws Exception {
                    requestBuilder.findById(DRIVER_ID)
                            .andExpect(model().attribute(
                                    "driver",
                                    hasProperty("status", equalTo(DRIVER_RESTING))
                            ));
                }
            }
        }
    }
}
