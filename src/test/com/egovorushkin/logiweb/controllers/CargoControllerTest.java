package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.dto.CargoDto;
import com.egovorushkin.logiweb.entities.enums.CargoStatus;
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

public class CargoControllerTest {

    private CargoRequestBuilder requestBuilder;
    private CargoService cargoService;

    @BeforeEach
    void configureSystemUnderTest() {
        cargoService = mock(CargoService.class);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new CargoController(cargoService))
                .setHandlerExceptionResolvers(exceptionResolver())
                .setLocaleResolver(fixedLocalResolver())
                .setViewResolvers(jspViewResolver())
                .build();
        requestBuilder = new CargoRequestBuilder(mockMvc);
    }

    @Nested
    @DisplayName("Render the HTML view that displays the information of all Cargoes")
    class FindAll {

        @Test
        @DisplayName("Should return the HTTP status code 200")
        void shouldReturnHttpStatusCodeOk() throws Exception {
            requestBuilder.findAll().andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should render the Cargo list view")
        void shouldRenderCargoListView() throws Exception {
            requestBuilder.findAll().andExpect(view().name("manager/cargo/list"));
        }

        @Test
        @DisplayName("Should render the Cargo create view")
        void shouldRenderCargoCreateView() throws Exception {
            requestBuilder.create().andExpect(view().name("manager/cargo/create"));
        }

        @Nested
        @DisplayName("When no cargoes is found from the database")
        class WhenNoCargoesIsFoundFromDatabase {

            @BeforeEach
            void serviceReturnsEmptyList() {
                given(cargoService.getAllCargoes()).willReturn(new ArrayList<>());
            }

            @Test
            @DisplayName("Should display zero cargoes")
            void shouldDisplayZeroCargoes() throws Exception {
                requestBuilder.findAll().andExpect(model().attribute("cargoes", hasSize(0)));
            }
        }

        @Nested
        @DisplayName("When two cargoes are found from the database")
        class WhenTwoDriversAreFoundFromTheDatabase {

            private final Long CARGO_ONE_ID = 1L;
            private final String CARGO_ONE_NAME = "PC";
            private final int CARGO_ONE_WEIGHT = 5000;
            private final CargoStatus CARGO_PREPARED = CargoStatus.PREPARED;

            private final Long CARGO_TWO_ID = 2L;
            private final String CARGO_TWO_NAME = "Wood";
            private final int CARGO_TWO_WEIGHT = 20000;
            private final CargoStatus CARGO_DELIVERED = CargoStatus.DELIVERED;

            @BeforeEach
            void cargoServiceReturnTwoCargoes() {
                CargoDto first = new CargoDto();
                first.setId(CARGO_ONE_ID);
                first.setName(CARGO_ONE_NAME);
                first.setWeight(CARGO_ONE_WEIGHT);
                first.setStatus(CARGO_PREPARED);

                CargoDto second = new CargoDto();
                second.setId(CARGO_TWO_ID);
                second.setName(CARGO_TWO_NAME);
                second.setWeight(CARGO_TWO_WEIGHT);
                second.setStatus(CARGO_DELIVERED);


                given(cargoService.getAllCargoes()).willReturn(Arrays.asList(first, second));
            }

            @Test
            @DisplayName("Should display two cargoes")
            void shouldDisplayTwoCargoes() throws Exception {
                requestBuilder.findAll().andExpect(model().attribute("cargoes", hasSize(2)));
            }

            /**
             * These two tests ensure that the list view displays the information
             * of the found cargoes but they don't guarantee that cargoes
             * are displayed in the correct order
             */
            @Test
            @DisplayName("Should display the information of the first cargo")
            void shouldDisplayInformationOfFirstCargo() throws Exception {
                requestBuilder.findAll()
                        .andExpect(
                                model().attribute("cargoes",
                                hasItem(allOf(
                                        hasProperty("id", equalTo(CARGO_ONE_ID)),
                                        hasProperty("name", equalTo(CARGO_ONE_NAME)),
                                        hasProperty("weight", equalTo(CARGO_ONE_WEIGHT)),
                                        hasProperty("status", equalTo(CARGO_PREPARED))
                                        )))
                        );
            }

            @Test
            @DisplayName("Should display the information of the second cargo")
            void shouldDisplayInformationOfSecondCargo() throws Exception {
                requestBuilder.findAll()
                        .andExpect(
                                model().attribute("cargoes",
                                        hasItem(allOf(
                                                hasProperty("id", equalTo(CARGO_TWO_ID)),
                                                hasProperty("name", equalTo(CARGO_TWO_NAME)),
                                                hasProperty("weight", equalTo(CARGO_TWO_WEIGHT)),
                                                hasProperty("status", equalTo(CARGO_DELIVERED))
                                        )))
                        );
            }

            /**
             * This test ensures that the list view displays the information
             * of the found cargoes in the correct order.
             */
            @Test
            @DisplayName("Should display the information of the first and second cargoes in the correct order")
            void shouldDisplayFirstAndSecondCargoesInCorrectOrder() throws Exception {
                requestBuilder.findAll()
                        .andExpect(
                                model().attribute("cargoes",
                                        contains(
                                                allOf(
                                                        hasProperty("id", equalTo(CARGO_ONE_ID)),
                                                        hasProperty("name", equalTo(CARGO_ONE_NAME)),
                                                        hasProperty("weight", equalTo(CARGO_ONE_WEIGHT)),
                                                        hasProperty("status", equalTo(CARGO_PREPARED))
                                                ),
                                                allOf(
                                                        hasProperty("id", equalTo(CARGO_TWO_ID)),
                                                        hasProperty("name", equalTo(CARGO_TWO_NAME)),
                                                        hasProperty("weight", equalTo(CARGO_TWO_WEIGHT)),
                                                        hasProperty("status", equalTo(CARGO_DELIVERED))
                                                )
                                        ))
                        );
            }
        }

        @Nested
        @DisplayName("Render the HTML view that displays the information of the requested cargo")
        class FindById {

            private final Long CARGO_ID = 9999L;

            @Nested
            @DisplayName("When the requested cargo isn't found from the database")
            class WhenRequestedCargoIsNotFound {

                @BeforeEach
                void cargoServiceThrowsNotFoundException() {
                    given(cargoService.getCargoById(CARGO_ID)).willThrow(new EntityNotFoundException(""));
                }

                @Test
                @DisplayName("Should return HTTP status code 404")
                void shouldReturnHttpStatusCodeNotFound() throws Exception {
                    requestBuilder.findById(CARGO_ID).andExpect(status().isNotFound());
                }

                @Test
                @DisplayName("Should render the 404 view")
                void shouldRender404View() throws Exception {
                    requestBuilder.findById(CARGO_ID).andExpect(view().name("error/404"));
                }
            }

            @Nested
            @DisplayName("When the requested cargo is found from the database")
            class WhenRequestedDriverIsFound {

                private final Long CARGO_ID = 11L;
                private final String CARGO_NAME = "Laptops";
                private final int CARGO_WEIGHT = 8000;
                private final CargoStatus CARGO_SHIPPED = CargoStatus.SHIPPED;

                @BeforeEach
                void cargoServiceReturnsShippedCargo() {

                    CargoDto cargo = new CargoDto();
                    cargo.setId(CARGO_ID);
                    cargo.setName(CARGO_NAME);
                    cargo.setWeight(CARGO_WEIGHT);
                    cargo.setStatus(CARGO_SHIPPED);

                    given(cargoService.getCargoById(CARGO_ID)).willReturn(cargo);
                }

                @Test
                @DisplayName("Should return the HTTP status code 200")
                void shouldReturnHttpStatusCodeOk() throws Exception {
                    requestBuilder.findById(CARGO_ID).andExpect(status().isOk());
                }

                @Test
                @DisplayName("Should render the view cargo")
                void shouldRenderViewCargoView() throws Exception {
                    requestBuilder.findById(CARGO_ID).andExpect(view().name("manager/cargo/show"));
                }

                @Test
                @DisplayName("Should display the information of the correct cargo")
                void shouldDisplayInformationOfCorrectCargo() throws Exception {
                    requestBuilder.findById(CARGO_ID)
                            .andExpect(model().attribute(
                                    "cargo",
                                    hasProperty("id", equalTo(CARGO_ID))
                            ));
                }

                @Test
                @DisplayName("Should display the correct information of cargo")
                void shouldDisplayCorrectInformationOfCargo() throws Exception {
                    requestBuilder.findById(CARGO_ID)
                            .andExpect(model().attribute(
                                    "cargo",
                                    allOf(
                                            hasProperty("id", equalTo(CARGO_ID)),
                                            hasProperty("name", equalTo(CARGO_NAME)),
                                            hasProperty("weight", equalTo(CARGO_WEIGHT)),
                                            hasProperty("status", equalTo(CARGO_SHIPPED))
                                    )
                            ));
                }

                @Test
                @DisplayName("Should display a shipped cargo")
                void shouldDisplayShippedCargo() throws Exception {
                    requestBuilder.findById(CARGO_ID)
                            .andExpect(model().attribute(
                                    "cargo",
                                    hasProperty("status", equalTo(CARGO_SHIPPED))
                            ));
                }
            }
        }
    }
}
