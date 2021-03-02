package com.egovorushkin.logiweb.services;

import com.egovorushkin.logiweb.dao.api.DriverDao;
import com.egovorushkin.logiweb.dao.api.OrderDao;
import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.OrderDto;
import com.egovorushkin.logiweb.dto.TruckDto;
import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.Order;
import com.egovorushkin.logiweb.entities.Truck;
import com.egovorushkin.logiweb.entities.enums.DriverStatus;
import com.egovorushkin.logiweb.entities.enums.OrderStatus;
import com.egovorushkin.logiweb.entities.enums.TruckState;
import com.egovorushkin.logiweb.entities.enums.TruckStatus;
import com.egovorushkin.logiweb.exceptions.EntityNotFoundException;
import com.egovorushkin.logiweb.services.api.DriverService;
import com.egovorushkin.logiweb.services.api.OrderService;
import com.egovorushkin.logiweb.services.api.ScoreboardService;
import com.egovorushkin.logiweb.services.impl.OrderServiceImpl;
import com.egovorushkin.logiweb.services.impl.ScoreboardServiceImpl;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    private static final Long ORDER_ONE_ID = 1L;
    private static final String ORDER_ONE_FROM_CITY = "Samara";
    private static final String ORDER_ONE_TO_CITY = "Omsk";
    private static final Integer ORDER_ONE_DISTANCE = 1790;
    private static final Integer ORDER_ONE_DURATION = 24;
    private static final OrderStatus ORDER_ONE_STATUS_COMPLETED =
            OrderStatus.COMPLETED;

    private static final Long ORDER_TWO_ID = 2L;
    private static final String ORDER_TWO_FROM_CITY = "Moscow";
    private static final String ORDER_TWO_TO_CITY = "Orel";
    private static final Integer ORDER_TWO_DISTANCE = 364;
    private static final Integer ORDER_TWO_DURATION = 5;
    private static final OrderStatus ORDER_TWO_STATUS_NOT_COMPLETED =
            OrderStatus.NOT_COMPLETED;

    private static final Long TRUCK_ONE_ID = 1L;
    private static final String TRUCK_ONE_REGISTRATION_NUMBER = "AB12345";
    private static final int TRUCK_ONE_TEAM_SIZE = 2;
    private static final int TRUCK_ONE_CAPACITY = 30000;
    private static final TruckStatus STATUS_PARKED = TruckStatus.PARKED;
    private static final TruckState STATE_SERVICEABLE = TruckState.SERVICEABLE;

    private static final Long TRUCK_TWO_ID = 2L;
    private static final String TRUCK_TWO_REGISTRATION_NUMBER = "NB00432";
    private static final int TRUCK_TWO_TEAM_SIZE = 2;
    private static final int TRUCK_TWO_CAPACITY = 25000;
    private static final TruckStatus STATUS_ON_THE_WAY = TruckStatus.ON_THE_WAY;
    private static final TruckState STATE_FAULTY = TruckState.FAULTY;

    private static final Long DRIVER_ONE_ID = 6L;
    private static final String DRIVER_ONE_USERNAME = "driver1";
    private static final String DRIVER_ONE_FIRST_NAME = "Ivan";
    private static final String DRIVER_ONE_LAST_NAME = "Ivanov";
    private static final int DRIVER_ONE_WORKED_HOURS = 100;
    private static final DriverStatus STATUS_DRIVING = DriverStatus.DRIVING;

    private static final Long DRIVER_TWO_ID = 9L;
    private static final String DRIVER_TWO_USERNAME = "driver2";
    private static final String DRIVER_TWO_FIRST_NAME = "Alex";
    private static final String DRIVER_TWO_LAST_NAME = "Alexeev";
    private static final int DRIVER_TWO_WORKED_HOURS = 150;
    private static final DriverStatus STATUS_RESTING = DriverStatus.RESTING;

    private final Mapper mapper = new DozerBeanMapper();

    OrderService orderService;

    @Mock
    private  OrderDao orderDao;
    private DriverService driverService;
    private DriverDao driverDao;
    private final Order orderOne = new Order();
    private final Order orderTwo = new Order();
    private final Truck truckOne = new Truck();
    private final Truck truckTwo = new Truck();
    private final Driver driverOne = new Driver();
    private final Driver driverTwo = new Driver();
    OrderDto orderDto = new OrderDto();

    @BeforeEach
    public void init() {
        ScoreboardService scoreboardService =
                Mockito.mock(ScoreboardServiceImpl.class);
        orderService = new OrderServiceImpl(orderDao, driverService,
                mapper, driverDao, scoreboardService);

        orderOne.setId(ORDER_ONE_ID);
        orderOne.setFromCity(ORDER_ONE_FROM_CITY);
        orderOne.setToCity(ORDER_ONE_TO_CITY);
        orderOne.setDistance(ORDER_ONE_DISTANCE);
        orderOne.setDuration(ORDER_ONE_DURATION);
        orderOne.setTruck(truckOne);
        orderOne.setStatus(ORDER_ONE_STATUS_COMPLETED);

        orderOne.setId(ORDER_TWO_ID);
        orderOne.setFromCity(ORDER_TWO_FROM_CITY);
        orderOne.setToCity(ORDER_TWO_TO_CITY);
        orderOne.setDistance(ORDER_TWO_DISTANCE);
        orderOne.setDuration(ORDER_TWO_DURATION);
        orderTwo.setTruck(truckTwo);
        orderOne.setStatus(ORDER_TWO_STATUS_NOT_COMPLETED);

        truckOne.setId(TRUCK_ONE_ID);
        truckOne.setRegistrationNumber(TRUCK_ONE_REGISTRATION_NUMBER);
        truckOne.setTeamSize(TRUCK_ONE_TEAM_SIZE);
        truckOne.setCapacity(TRUCK_ONE_CAPACITY);
        truckOne.setStatus(STATUS_PARKED);
        truckOne.setState(STATE_SERVICEABLE);

        truckTwo.setId(TRUCK_TWO_ID);
        truckTwo.setRegistrationNumber(TRUCK_TWO_REGISTRATION_NUMBER);
        truckTwo.setTeamSize(TRUCK_TWO_TEAM_SIZE);
        truckTwo.setCapacity(TRUCK_TWO_CAPACITY);
        truckTwo.setStatus(STATUS_ON_THE_WAY);
        truckTwo.setState(STATE_FAULTY);

        driverOne.setId(DRIVER_ONE_ID);
        driverOne.setUsername(DRIVER_ONE_USERNAME);
        driverOne.setFirstName(DRIVER_ONE_FIRST_NAME);
        driverOne.setLastName(DRIVER_ONE_LAST_NAME);
        driverOne.setWorkedHoursPerMonth(DRIVER_ONE_WORKED_HOURS);
        driverOne.setStatus(STATUS_DRIVING);

        driverTwo.setId(DRIVER_TWO_ID);
        driverTwo.setUsername(DRIVER_TWO_USERNAME);
        driverTwo.setFirstName(DRIVER_TWO_FIRST_NAME);
        driverTwo.setLastName(DRIVER_TWO_LAST_NAME);
        driverTwo.setWorkedHoursPerMonth(DRIVER_TWO_WORKED_HOURS);
        driverTwo.setStatus(STATUS_RESTING);
    }

    @Test
    @DisplayName("Test get order by id success")
    void testGetOrderByIdSuccess() {
        when(orderDao.getOrderById(ORDER_ONE_ID)).thenReturn(orderOne);

        orderDto = orderService.getOrderById(ORDER_ONE_ID);

        Assertions.assertEquals(mapper.map(orderOne, OrderDto.class),
                orderDto);
    }

    @Test
    @DisplayName("Test get order by id failed")
    void testGetOrderByIdFailed() {
        when(orderDao.getOrderById(ORDER_ONE_ID)).thenReturn(null);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> orderService.getOrderById(ORDER_ONE_ID));
    }

    @Test
    @DisplayName("Test get all orders success")
    void testGetAllOrders() {
        List<Order> expectedOrders = new ArrayList<>();

        expectedOrders.add(orderOne);
        expectedOrders.add(orderTwo);

        when(orderDao.getAllOrders()).thenReturn(expectedOrders);

        List<OrderDto> expectedOrdersDto = expectedOrders.stream()
                .map(order -> mapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
        List<OrderDto> actualOrders = orderService.getAllOrders();

        Assertions.assertEquals(expectedOrdersDto, actualOrders);
    }

    @Test
    @DisplayName("Test create order success")
    void testCreateOrderSuccess() {
        orderService.createOrder(mapper.map(orderOne, OrderDto.class));

        verify(orderDao, times(1))
                .createOrder(any(Order.class));
    }

    @Test
    @DisplayName("Test update order success")
    void testUpdateOrderSuccess() {
        orderService.updateOrder(mapper.map(orderOne, OrderDto.class));

        verify(orderDao, times(1))
                .updateOrder(any(Order.class));
    }

    @Test
    @DisplayName("Test update order failed")
    void testUpdateOrderFailed() {

        doThrow(new NoResultException()).when(orderDao).updateOrder(orderOne);

        OrderDto existingOrderDto = mapper.map(orderOne,
                OrderDto.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> orderService.updateOrder(existingOrderDto));
    }

    @Test
    @DisplayName("Test delete order success")
    void testDeleteOrderSuccess() {
        orderService.deleteOrder(ORDER_ONE_ID);

        verify(orderDao, times(1))
                .deleteOrder(ORDER_ONE_ID);
    }

    @Test
    @DisplayName("Test find available trucks when order.getTruck() not null success")
    void testFindAvailableTrucksWhenOrderGetTruckNotNullSuccess() {
        when(orderDao.findAvailableTrucks(orderOne))
                .thenReturn(Collections.singletonList(truckTwo));

        List<TruckDto> expectedTrucks = Stream.of(truckTwo)
                .map(truck -> mapper.map(truck, TruckDto.class))
                .collect(Collectors.toList());
        List<TruckDto> actualTrucks = orderService
                .findAvailableTrucks(mapper.map(orderOne, OrderDto.class));

        Assertions.assertEquals(expectedTrucks, actualTrucks);
    }

    @Test
    @DisplayName("Test find available trucks when order.getTruck() is null success")
    void testFindAvailableTrucksWhenOrderGetTruckIsNullSuccess() {
        orderOne.setTruck(null);

        when(orderDao.findAvailableTrucks(orderOne))
                .thenReturn(Collections.singletonList(truckTwo));

        List<TruckDto> expectedTrucks = Stream.of(truckTwo)
                .map(truck -> mapper.map(truck, TruckDto.class))
                .collect(Collectors.toList());
        List<TruckDto> actualTrucks = orderService
                .findAvailableTrucks(mapper.map(orderOne, OrderDto.class));

        Assertions.assertEquals(expectedTrucks, actualTrucks);
    }

    @Test
    @DisplayName("Test find current orders for truck if truck is null success")
    void testFindCurrentOrdersForTruckIfTruckNullSuccess() {
        List<OrderDto> expectedOrders = Collections.emptyList();
        List<OrderDto> actualOrders =
                orderService.findCurrentOrdersForTruck(null);

        Assertions.assertEquals(expectedOrders, actualOrders);
    }

    @Test
    @DisplayName("Test find current orders for truck if orders is empty and truck" +
            " not null success")
    void testFindCurrentOrdersForTruckIfOrdersEmptyAndTruckNotNullSuccess() {
        when(orderDao.findCurrentOrdersForTruck(TRUCK_ONE_ID))
                .thenReturn(Collections.emptyList());

        List<Order> expectedOrders =
                orderDao.findCurrentOrdersForTruck(TRUCK_ONE_ID);

        List<OrderDto> actualOrders = orderService
                .findCurrentOrdersForTruck(mapper.map(truckOne, TruckDto.class));

        Assertions.assertEquals(actualOrders.size(), expectedOrders.size());
    }

    @Test
    @DisplayName("Test find current orders for truck if orders is no empty and " +
            "truck is not null success")
    void testFindCurrentOrdersForTruckIfOrdersNotEmptyAndTruckNotNullSuccess() {
        when(orderDao.findCurrentOrdersForTruck(TRUCK_ONE_ID))
                .thenReturn(Collections.singletonList(orderOne));

        List<OrderDto> expectedOrders = orderDao
                .findCurrentOrdersForTruck(TRUCK_ONE_ID)
                .stream()
                .map(order -> mapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
        List<OrderDto> actualOrders = orderService
                .findCurrentOrdersForTruck(mapper.map(truckOne, TruckDto.class));

        Assertions.assertEquals(actualOrders.size(), expectedOrders.size());
    }

    @Test
    @DisplayName("Test find available drivers for order if truck is null success")
    void testFindAvailableDriversForOrderIfTruckNullSuccess() {
        orderOne.setTruck(null);

        OrderDto orderDto = mapper.map(orderOne, OrderDto.class);
        List<DriverDto> expectedDrivers = Collections.emptyList();
        List<DriverDto> actualDrivers = orderService.findAvailableDriversForOrder(orderDto);

        Assertions.assertEquals(expectedDrivers, actualDrivers);
    }

    @Test
    @DisplayName("Test find available drivers for order if truck is not null success")
    void testFindAvailableDriversForOrderIfTruckNotNullSuccess() {
        OrderDto orderDtoOne = mapper.map(orderOne, OrderDto.class);
        DriverDto driverDtoOne = mapper.map(driverOne, DriverDto.class);

        when(orderDao.findAvailableDriversForOrder(orderOne))
                .thenReturn(Collections.singletonList(driverOne));

        List<DriverDto> expectedDrivers = Collections.singletonList(driverDtoOne);
        List<DriverDto> actualDrivers = orderService.findAvailableDriversForOrder(orderDtoOne);

        Assertions.assertEquals(expectedDrivers, actualDrivers);
    }

    @Test
    @DisplayName("Test get latest orders success")
    void testGetLatestOrdersSuccess() {
        when(orderDao.getLatestOrders())
                .thenReturn(Collections.singletonList(orderTwo));

        List<OrderDto> expectedOrders = Stream.of(orderTwo)
                .map(order -> mapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
        List<OrderDto> actualOrders = orderService.getLatestOrders();

        Assertions.assertEquals(expectedOrders, actualOrders);
    }

    @Test
    @DisplayName("Test find order by truck id success")
    void testFindOrderByTruckId() {
        when(orderDao.findOrderByTruckId(TRUCK_TWO_ID)).thenReturn(orderTwo);

        OrderDto expectedOrder = mapper.map(orderTwo, OrderDto.class);
        OrderDto actualOrder = orderService.findOrderByTruckId(TRUCK_TWO_ID);

        Assertions.assertEquals(expectedOrder, actualOrder);
    }
}
