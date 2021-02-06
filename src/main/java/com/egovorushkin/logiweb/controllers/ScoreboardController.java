package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.dto.OrderDto;
import com.egovorushkin.logiweb.dto.OrderDtoT;
import com.egovorushkin.logiweb.services.api.DriverService;
import com.egovorushkin.logiweb.services.api.OrderService;
import com.egovorushkin.logiweb.services.api.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
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
    public List<OrderDtoT> getLatestOrders() {
        return orderService.getLatestOrders();
    }

    @RequestMapping("/students")
    public List<Student> getStudents() {

        List<Student> students = new ArrayList<>();

        students.add(new Student("A", "A"));
        students.add(new Student("B", "B"));
        students.add(new Student("C", "C"));
        students.add(new Student("D", "D"));
        students.add(new Student("E", "E"));

        for (Student s : students) {
            System.out.println(s);
        }

        return students;
    }

}
