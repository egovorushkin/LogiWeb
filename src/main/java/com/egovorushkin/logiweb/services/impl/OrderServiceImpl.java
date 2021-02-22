package com.egovorushkin.logiweb.services.impl;

import com.egovorushkin.logiweb.dao.api.DriverDao;
import com.egovorushkin.logiweb.dao.api.OrderDao;
import com.egovorushkin.logiweb.dto.DriverDto;
import com.egovorushkin.logiweb.dto.OrderDto;
import com.egovorushkin.logiweb.dto.TruckDto;
import com.egovorushkin.logiweb.entities.Driver;
import com.egovorushkin.logiweb.entities.Order;
import com.egovorushkin.logiweb.entities.Truck;
import com.egovorushkin.logiweb.entities.enums.CargoStatus;
import com.egovorushkin.logiweb.exceptions.EntityNotFoundException;
import com.egovorushkin.logiweb.exceptions.ServiceException;
import com.egovorushkin.logiweb.services.api.DriverService;
import com.egovorushkin.logiweb.services.api.OrderService;
import com.egovorushkin.logiweb.services.api.ScoreboardService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER =
            Logger.getLogger(OrderServiceImpl.class.getName());

    private static final String ORDER = "Order with id = ";

    private final OrderDao orderDao;
    private final DriverService driverService;
    private final ModelMapper modelMapper;
    private final DriverDao driverDao;
    private final ScoreboardService scoreboardService;

    public OrderServiceImpl(OrderDao orderDao,
                            DriverService driverService,
                            ModelMapper modelMapper,
                            DriverDao driverDao,
                            ScoreboardService scoreboardService) {
        this.orderDao = orderDao;
        this.driverService = driverService;
        this.modelMapper = modelMapper;
        this.driverDao = driverDao;
        this.scoreboardService = scoreboardService;
    }

    @Override
    public OrderDto getOrderById(Long id) {

        LOGGER.debug("getOrderById() executed");

        if (orderDao.getOrderById(id) == null) {
            throw new EntityNotFoundException(ORDER + id + " is" +
                    " not found");
        }

        LOGGER.info("Found order with id = " + id);

        return modelMapper.map(orderDao.getOrderById(id), OrderDto.class);
    }

    @Override
    public List<OrderDto> getAllOrders() {

        LOGGER.debug("getAllOrders() executed");

        List<Order> orders = orderDao.getAllOrders();

        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TruckDto> findAvailableTrucks(OrderDto orderDto) {

        LOGGER.debug("findAvailableTrucks() executed");

        List<Truck> trucks =
                orderDao.findAvailableTrucks(modelMapper.map(orderDto,
                        Order.class));
        List<TruckDto> availableTrucks = trucks.stream()
                .map(truck -> modelMapper.map(truck, TruckDto.class))
                .collect(Collectors.toList());

        if (orderDto.getTruck() != null) {
            return availableTrucks.stream()
                    .filter(availableTruck ->
                            !orderDto.getTruck().getId().equals(availableTruck.getId()))
                    .collect(Collectors.toList());
        }

        LOGGER.info("Available trucks found");

        return availableTrucks;
    }

    @Override
    public List<OrderDto> findCurrentOrdersForTruck(TruckDto truckDto) {

        LOGGER.debug("findCurrentOrdersForTruck() executed");

        if (truckDto == null) {
            return Collections.emptyList();
        }

        List<Order> orders = orderDao.findCurrentOrdersForTruck(truckDto.getId());

        if (orders.isEmpty()) {

            LOGGER.info("Orders for truck with id = " + truckDto.getId() + " not found");

            return Collections.emptyList();
        }

        LOGGER.info("Orders for truck with id = " + truckDto.getId() + " found");

        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DriverDto> findAvailableDriversForOrder(OrderDto orderDto) {

        LOGGER.debug("findAvailableDriversForOrder() executed");

        List<Driver> drivers;
        if (orderDto.getTruck() == null) {
            return Collections.emptyList();
        }
        drivers = orderDao.findAvailableDriversForOrder(modelMapper
                .map(orderDto, Order.class));
        List<DriverDto> availableDrivers = drivers.stream()
                .map(driver -> modelMapper.map(driver, DriverDto.class))
                .collect(Collectors.toList());

        LOGGER.info("Available drivers for order with id = " + orderDto.getId() + " found");

        return availableDrivers.stream()
                .filter(availableDriver ->
                        (availableDriver.getWorkedHoursPerMonth() +
                                orderDto.getDuration() <= 176))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createOrder(OrderDto orderDto) {

        LOGGER.debug("createOrder() executed");

        orderDao.createOrder(modelMapper.map(orderDto, Order.class));

        scoreboardService.updateScoreboard();

        LOGGER.info(ORDER + " created");
    }

    @Override
    @Transactional
    public void updateOrder(OrderDto orderDto) {

        LOGGER.debug("updateOrder() executed");

        try {
            orderDao.updateOrder(modelMapper.map(orderDto, Order.class));
        } catch (NoResultException e) {
            throw new EntityNotFoundException(String.format(ORDER +
                    "%s does not exist", orderDto.getId()));
        }

        scoreboardService.updateScoreboard();

        LOGGER.info(ORDER + orderDto.getId() + " updated");
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {

        LOGGER.debug("deleteOrder() executed");

        orderDao.deleteOrder(id);

        scoreboardService.updateScoreboard();

        LOGGER.info(ORDER + id + " deleted");
    }

    @Override
    @Transactional
    public void updateStatusOfOrder(OrderDto orderDto) {

        LOGGER.debug("updateStatusOfOrder() executed");
        DriverDto driver = driverService.getAuthorizedDriverByUsername();
        DriverDto colleague =
                driverService.findColleagueAuthorizedDriverByUsername();

        OrderDto existingOrder =
                modelMapper.map(orderDao.getOrderById(orderDto.getId()),
                        OrderDto.class);

        existingOrder.setStatus(orderDto.getStatus());
        orderDao.updateOrder(modelMapper.map(existingOrder, Order.class));

        Order order = orderDao.getOrderById(existingOrder.getId());
        order.getCargo().setStatus(CargoStatus.DELIVERED);
        orderDao.updateOrder(order);


        if (orderDto.getDuration() <= 12) {
            driver.setWorkedHoursPerMonth(driver.getWorkedHoursPerMonth() + orderDto.getDuration());
        } else {
            driver.setWorkedHoursPerMonth(driver.getWorkedHoursPerMonth() + orderDto.getDuration());
            colleague.setWorkedHoursPerMonth(colleague.getWorkedHoursPerMonth() + orderDto.getDuration());
        }

        driverDao.updateDriver(modelMapper.map(driver, Driver.class));
        driverDao.updateDriver(modelMapper.map(colleague, Driver.class));

        scoreboardService.updateScoreboard();
    }

    @Override
    public List<OrderDto> getLatestOrders() {
        List<Order> orders = orderDao.getLatestOrders();

        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto findOrderByTruckId(Long id) {
        return modelMapper.map(orderDao.findOrderByTruckId(id), OrderDto.class);
    }
}
