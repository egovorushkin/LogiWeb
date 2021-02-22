package com.egovorushkin.logiweb.services;

import com.egovorushkin.logiweb.dao.api.DriverDao;
import com.egovorushkin.logiweb.dao.api.OrderDao;
import com.egovorushkin.logiweb.dto.OrderDto;
import com.egovorushkin.logiweb.entities.Cargo;
import com.egovorushkin.logiweb.entities.Order;
import com.egovorushkin.logiweb.entities.Truck;
import com.egovorushkin.logiweb.entities.enums.OrderStatus;
import com.egovorushkin.logiweb.exceptions.EntityNotFoundException;
import com.egovorushkin.logiweb.services.api.DriverService;
import com.egovorushkin.logiweb.services.api.OrderService;
import com.egovorushkin.logiweb.services.api.ScoreboardService;
import com.egovorushkin.logiweb.services.impl.OrderServiceImpl;
import com.egovorushkin.logiweb.services.impl.ScoreboardServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    OrderService orderService;

    @Mock
    private  OrderDao orderDao;
    private  DriverService driverService;
    private  ModelMapper modelMapper;
    private  DriverDao driverDao;
    private final Order orderOne = new Order();
    private final Order orderTwo = new Order();

    OrderDto orderDto = new OrderDto();

    OrderServiceTest() {
    }

    @BeforeEach
    public void init() {
        Cargo cargo = Mockito.mock(Cargo.class);
        Truck truck = Mockito.mock(Truck.class);
        ScoreboardService scoreboardService =
                Mockito.mock(ScoreboardServiceImpl.class);
        modelMapper = new ModelMapper();
        orderService = new OrderServiceImpl(orderDao, driverService,
                modelMapper, driverDao, scoreboardService);

        orderOne.setId(ORDER_ONE_ID);
        orderOne.setFromCity(ORDER_ONE_FROM_CITY);
        orderOne.setToCity(ORDER_ONE_TO_CITY);
//        orderOne.setCargo(cargo);
        orderOne.setDistance(ORDER_ONE_DISTANCE);
        orderOne.setDuration(ORDER_ONE_DURATION);
//        orderOne.setTruck(truck);
        orderOne.setStatus(ORDER_ONE_STATUS_COMPLETED);

        orderOne.setId(ORDER_TWO_ID);
        orderOne.setFromCity(ORDER_TWO_FROM_CITY);
        orderOne.setToCity(ORDER_TWO_TO_CITY);
//        orderOne.setCargo(cargo);
        orderOne.setDistance(ORDER_TWO_DISTANCE);
        orderOne.setDuration(ORDER_TWO_DURATION);
//        orderOne.setTruck(truck);
        orderOne.setStatus(ORDER_TWO_STATUS_NOT_COMPLETED);

    }

    @Test
    @DisplayName("Test getOrderById success")
    void testGetOrderByIdSuccess() {
        when(orderDao.getOrderById(ORDER_ONE_ID)).thenReturn(orderOne);
        orderDto = orderService.getOrderById(ORDER_ONE_ID);
        Assertions.assertEquals(orderOne.getId(), orderDto.getId());
        Assertions.assertEquals(modelMapper.map(orderOne, OrderDto.class),
                orderDto);
    }

    @Test
    @DisplayName("Test getOrderById failed")
    void testGetOrderByIdFailed() {
        when(orderDao.getOrderById(ORDER_ONE_ID)).thenReturn(null);
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> orderService.getOrderById(ORDER_ONE_ID));
    }

    @Test
    @DisplayName("Test getAllOrders")
    void testGetAllOrders() {
        List<Order> expectedOrders = new ArrayList<>();
        expectedOrders.add(orderOne);
        expectedOrders.add(orderTwo);
        when(orderDao.getAllOrders()).thenReturn(expectedOrders);
        List<OrderDto> expectedOrdersDto = expectedOrders.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
        List<OrderDto> actualOrders = orderService.getAllOrders();
        Assertions.assertEquals(expectedOrdersDto, actualOrders);
    }

    @Test
    @DisplayName("Test createOrder success")
    void createOrderSuccess() {
        orderService.createOrder(modelMapper.map(orderOne, OrderDto.class));
        verify(orderDao, times(1))
                .createOrder(any(Order.class));
    }

    @Test
    @DisplayName("Test updateOrder success")
    void updateOrderSuccess() {
        orderService.updateOrder(modelMapper.map(orderOne, OrderDto.class));
        verify(orderDao, times(1))
                .updateOrder(any(Order.class));
    }

    @Test
    @DisplayName("Test updateOrder failed")
    void updateOrderFailed() {

        doThrow(new NoResultException()).when(orderDao).updateOrder(orderOne);

        OrderDto existingOrderDto = modelMapper.map(orderOne,
                OrderDto.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> orderService.updateOrder(existingOrderDto));
    }

    @Test
    @DisplayName("Test deleteOrder success")
    void deleteOrderSuccess() {
        orderService.deleteOrder(ORDER_ONE_ID);
        verify(orderDao, times(1))
                .deleteOrder(ORDER_ONE_ID);
    }

//    @Test
//    @DisplayName("Test findAvailableTrucks success")
//    void findAvailableTrucksSuccess() {
//        when(orderDao.findAvailableTrucks(orderOne)).thenReturn(new ArrayList<>());
//    }
}
