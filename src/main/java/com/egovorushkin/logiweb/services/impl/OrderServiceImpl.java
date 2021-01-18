package com.egovorushkin.logiweb.services.impl;

import com.egovorushkin.logiweb.config.security.IAuthenticationFacade;
import com.egovorushkin.logiweb.dao.api.OrderDao;
import com.egovorushkin.logiweb.dto.OrderDto;
import com.egovorushkin.logiweb.dto.TruckDto;
import com.egovorushkin.logiweb.entities.Order;
import com.egovorushkin.logiweb.entities.Truck;
import com.egovorushkin.logiweb.services.api.OrderService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER =
            Logger.getLogger(OrderServiceImpl.class.getName());

    private final OrderDao orderDao;
    private final ModelMapper modelMapper;
    private final IAuthenticationFacade authenticationFacade;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, ModelMapper modelMapper,
                            IAuthenticationFacade authenticationFacade) {
        this.orderDao = orderDao;
        this.modelMapper = modelMapper;
        this.authenticationFacade = authenticationFacade;
    }

    @Override
    public OrderDto getOrderById(long id) {
        return modelMapper.map(orderDao.getOrderById(id), OrderDto.class);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderDao.getAllOrders();
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TruckDto> findAvailableTrucks(OrderDto orderDto) {
        List<Truck> trucks = orderDao.findAvailableTrucks(modelMapper.map(orderDto, Order.class));
        return trucks.stream()
                .map(truck -> modelMapper.map(truck, TruckDto.class))
                .collect(Collectors.toList());
    }

    // TODO Complete this method
    @Override
    public List<OrderDto> findCurrentOrders(long id) {
        Authentication authentication = authenticationFacade.getAuthentication();

        return null;
    }

    @Override
    @Transactional
    public void createOrder(OrderDto orderDto) {
        orderDao.createOrder(modelMapper.map(orderDto, Order.class));
    }

    @Override
    @Transactional
    public void updateOrder(OrderDto orderDto) {
        orderDao.updateOrder(modelMapper.map(orderDto, Order.class));
    }

    @Override
    @Transactional
    public void deleteOrder(long id) {
        orderDao.deleteOrder(id);
    }

}
