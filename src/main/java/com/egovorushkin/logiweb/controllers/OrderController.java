package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.entities.Order;
import com.egovorushkin.logiweb.entities.enums.OrderStatus;
import com.egovorushkin.logiweb.services.api.CargoService;
import com.egovorushkin.logiweb.services.api.CityService;
import com.egovorushkin.logiweb.services.api.OrderService;
import com.egovorushkin.logiweb.services.api.TruckService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private static final Logger logger = Logger.getLogger(OrderController.class.getName());

    private final OrderService orderService;
    private final CityService cityService;
    private final CargoService cargoService;
    private final TruckService truckService;

    @Autowired
    public OrderController(OrderService orderService,
                           CityService cityService,
                           CargoService cargoService,
                           TruckService truckService) {
        this.orderService = orderService;
        this.cityService = cityService;
        this.cargoService = cargoService;
        this.truckService = truckService;
    }

    @GetMapping(value = "/list")
    public String showAllOrders(Model model) {

        if (logger.isDebugEnabled()){
            logger.debug("showAllOrders is executed");
        }

        model.addAttribute("orders", orderService.listAll());
        return "manager/order/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {

        if (logger.isDebugEnabled()){
            logger.debug("show is executed");
        }

        model.addAttribute("order", orderService.showOrder(id));
        model.addAttribute("trucks", truckService.listAll());
        return "manager/order/show";
    }

    @GetMapping(value = "/create")
    public String createOrderForm(Model model) {

        if (logger.isDebugEnabled()){
            logger.debug("createOrderForm is executed");
        }

        model.addAttribute("order", new Order());
        model.addAttribute("cities", cityService.listAll());
        model.addAttribute("cargoes", cargoService.listAll());
        model.addAttribute("trucks", truckService.listAll());
        return "manager/order/create";
    }

    @PostMapping(value = "/save")
    public String saveOrder(@ModelAttribute("order") @Valid Order order,
                            BindingResult bindingResult, Model model) {

        if (logger.isDebugEnabled()){
            logger.debug("saveOrder is executed");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("cities", cityService.listAll());
            model.addAttribute("cargoes", cargoService.listAll());
            model.addAttribute("trucks", truckService.listAll());
            return "manager/order/create";
        }
        orderService.saveOrder(order);
        return "redirect:/orders/list";
    }

    @GetMapping(value = "/edit")
    public String editOrderForm(@RequestParam("orderId") int id, Model model) {

        if (logger.isDebugEnabled()){
            logger.debug("editOrderForm is executed");
        }

        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        model.addAttribute("statuses", OrderStatus.values());
        model.addAttribute("cities", cityService.listAll());
        model.addAttribute("cargoes", cargoService.listAll());
        model.addAttribute("trucks", truckService.listAll());
        model.addAttribute("availableTrucks", orderService.findAvailableTrucks(orderService.getOrderById(id)));
        return "manager/order/edit";
    }

    @PostMapping("/update")
    public String updateOrder(@ModelAttribute("order") @Valid Order order,
                              BindingResult bindingResult) {

        if (logger.isDebugEnabled()){
            logger.debug("updateOrder is executed");
        }

        if (bindingResult.hasErrors()) {
            return "manager/order/edit";
        }
        orderService.update(order);
        return "redirect:/orders/list";
    }

    @GetMapping(value = "/delete")
    public String deleteOrder(@RequestParam("orderId") int id) {

        if (logger.isDebugEnabled()){
            logger.debug("deleteOrder is executed");
        }

        orderService.delete(id);
        return "redirect:/orders/list";
    }
}
