package com.egovorushkin.logiweb.services.api;

import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.OrderDto;
import com.egovorushkin.logiweb.dto.TruckDto;
import java.util.List;

public interface OrderService {

    OrderDto getOrderById(long id);

    List<OrderDto> getAllOrders();

    void createOrder(OrderDto orderDto);

    void updateOrder(OrderDto orderDto);

    void deleteOrder(long id);

    List<TruckDto> findAvailableTrucks(OrderDto orderDto);

    List<OrderDto> findCurrentOrdersForTruck(TruckDto truckDto);

    List<DriverDto> findAvailableDriversForOrder(OrderDto orderDto);

    void mergeWithExistingAndUpdate(OrderDto orderDto);

    List<OrderDto> getLatestOrders();
}
