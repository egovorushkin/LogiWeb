package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.dto.OrderDto;
import com.egovorushkin.logiweb.entities.enums.OrderStatus;
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

public class OrderControllerTest {

    private OrderRequestBuilder requestBuilder;
    private OrderService orderService;

    @BeforeEach
    void configureSystemUnderTest() {
        orderService = mock(OrderService.class);
        CityService cityService = mock(CityService.class);
        CargoService cargoService = mock(CargoService.class);
        TruckService truckService = mock(TruckService.class);
        MapService mapService = mock(MapService.class);

        MockMvc mockMvc =
                MockMvcBuilders.standaloneSetup(
                        new OrderController(orderService,
                                cityService, cargoService, truckService,
                                mapService))
                        .setHandlerExceptionResolvers(exceptionResolver())
                        .setLocaleResolver(fixedLocalResolver())
                        .setViewResolvers(jspViewResolver())
                        .build();
        requestBuilder = new OrderRequestBuilder(mockMvc);
    }

    @Nested
    @DisplayName("Render the HTML view that displays the information of all " +
            "Orders")
    class FindAll {

        @Test
        @DisplayName("Should return the HTTP status code 200")
        void testShouldReturnHttpStatusCodeOk() throws Exception {
            requestBuilder.findAll().andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should render the Order list view")
        void testShouldRenderOrderListView() throws Exception {
            requestBuilder.findAll().andExpect(view().name("manager/order" +
                    "/list"));
        }

        @Nested
        @DisplayName("When no orders is found from the database")
        class WhenNoOrdersIsFoundFromDatabase {

            @BeforeEach
            void serviceReturnsEmptyList() {
                given(orderService.getAllOrders()).willReturn(new ArrayList<>());
            }

            @Test
            @DisplayName("Should display zero orders")
            void testShouldDisplayZeroTrucks() throws Exception {
                requestBuilder.findAll().andExpect(model().attribute("orders"
                        , hasSize(0)));
            }
        }

        @Nested
        @DisplayName("When two trucks are found from the database")
        class WhenTwoTrucksAreFoundFromTheDatabase {

            private final Long ORDER_ONE_ID = 1L;
            private final OrderStatus STATUS_COMPLETED = OrderStatus.COMPLETED;
            private final int ORDER_ONE_DISTANCE = 2000;

            private final Long ORDER_TWO_ID = 2L;
            private final OrderStatus STATUS_NOT_COMPLETED =
                    OrderStatus.NOT_COMPLETED;
            private final int ORDER_TWO_DISTANCE = 1000;

            @BeforeEach
            void orderServiceReturnTwoOrders() {
                OrderDto first = new OrderDto();
                first.setId(ORDER_ONE_ID);
                first.setStatus(STATUS_COMPLETED);
                first.setDistance(ORDER_ONE_DISTANCE);

                OrderDto second = new OrderDto();
                second.setId(ORDER_TWO_ID);
                second.setStatus(STATUS_NOT_COMPLETED);
                second.setDistance(ORDER_TWO_DISTANCE);

                given(orderService.getAllOrders()).willReturn(Arrays.asList(first, second));
            }

            @Test
            @DisplayName("Should display two orders")
            void testShouldDisplayTwoOrders() throws Exception {
                requestBuilder.findAll().andExpect(model().attribute("orders"
                        , hasSize(2)));
            }

            /**
             * These two tests ensure that the list view displays the
             * information of the found orders but they don't guarantee that
             * orders are displayed in the correct order
             */
            @Test
            @DisplayName("Should display the information of the first order")
            void testShouldDisplayInformationOfFirstOrder() throws Exception {
                requestBuilder.findAll()
                        .andExpect(
                                model().attribute("orders",
                                        hasItem(allOf(
                                                hasProperty("id",
                                                        equalTo(ORDER_ONE_ID)),
                                                hasProperty(
                                                        "status",
                                                        equalTo(STATUS_COMPLETED)),
                                                hasProperty("distance",
                                                        equalTo(ORDER_ONE_DISTANCE))
                                        )))
                        );
            }

            @Test
            @DisplayName("Should display the information of the second truck")
            void testShouldDisplayInformationOfSecondTruck() throws Exception {
                requestBuilder.findAll()
                        .andExpect(
                                model().attribute("orders",
                                        hasItem(allOf(
                                                hasProperty("id",
                                                        equalTo(ORDER_TWO_ID)),
                                                hasProperty(
                                                        "status",
                                                        equalTo(STATUS_NOT_COMPLETED)),
                                                hasProperty("distance",
                                                        equalTo(ORDER_TWO_DISTANCE))
                                        )))
                        );
            }

            /**
             * This test ensures that the list view displays the information
             * of the found orders in the correct order.
             */
            @Test
            @DisplayName("Should display the information of the first and " +
                    "second trucks in the correct order")
            void testShouldDisplayFirstAndSecondTrucksInCorrectOrder()
                    throws Exception {
                requestBuilder.findAll()
                        .andExpect(
                                model().attribute("orders",
                                        contains(
                                                allOf(
                                                        hasProperty("id",
                                                                equalTo(ORDER_ONE_ID)),
                                                        hasProperty(
                                                                "status",
                                                                equalTo(STATUS_COMPLETED)),
                                                        hasProperty("distance",
                                                                equalTo(ORDER_ONE_DISTANCE))
                                                ),
                                                allOf(
                                                        hasProperty("id",
                                                                equalTo(ORDER_TWO_ID)),
                                                        hasProperty(
                                                                "status",
                                                                equalTo(STATUS_NOT_COMPLETED)),
                                                        hasProperty("distance",
                                                                equalTo(ORDER_TWO_DISTANCE))
                                                )
                                        ))
                        );
            }
        }

    }
}
