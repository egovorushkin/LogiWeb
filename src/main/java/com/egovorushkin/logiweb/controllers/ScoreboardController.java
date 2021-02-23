package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.dto.DriverStatsDto;
import com.egovorushkin.logiweb.dto.OrderDto;
import com.egovorushkin.logiweb.dto.TruckStatsDto;
import com.egovorushkin.logiweb.services.api.DriverService;
import com.egovorushkin.logiweb.services.api.OrderService;
import com.egovorushkin.logiweb.services.api.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scoreboard")
public class ScoreboardController {

    private final TruckService truckService;
    private final DriverService driverService;
    private final OrderService orderService;

    @Autowired
    public ScoreboardController(TruckService truckService,
                                DriverService driverService,
                                OrderService orderService) {
        this.truckService = truckService;
        this.driverService = driverService;
        this.orderService = orderService;
    }

    @RequestMapping("/orders")
    public List<OrderDto> getLatestOrders() {
        return orderService.getLatestOrders();
    }

    /**
     * This method return JSON with
     * truck statistic
     * @return {@link List<TruckStatsDto>} truck statistic
     */
    @RequestMapping("/trucks")
    public TruckStatsDto getTruckStats() {
        return truckService.getStats();
    }

    /**
     * This method return JSON with
     * driver statistic
     * @return {@link List<DriverStatsDto>} driver statistic
     */
    @RequestMapping("/drivers")
    public DriverStatsDto getDriverStats() {
        return driverService.getStats();
    }

}
