package com.egovorushkin.logiweb.services.api;

import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.OrderDto;
import com.egovorushkin.logiweb.dto.TruckDto;

import java.util.List;

/**
 * Service interface for {@link com.egovorushkin.logiweb.entities.Order}
 */
public interface OrderService {

    OrderDto getOrderById(Long id);

    List<OrderDto> getAllOrders();

    void createOrder(OrderDto orderDto);

    void updateOrder(OrderDto orderDto);

    void deleteOrder(Long id);

    List<TruckDto> findAvailableTrucks(OrderDto orderDto);

    List<OrderDto> findCurrentOrdersForTruck(TruckDto truckDto);

    List<DriverDto> findAvailableDriversForOrder(OrderDto orderDto);

    void updateStatusOfOrder(OrderDto orderDto);

    List<OrderDto> getLatestOrders();

    OrderDto findOrderByTruckId(Long id);

    List<OrderDto> listAllByPage(int firstResult, int maxResult);

    Long totalCount();
}
