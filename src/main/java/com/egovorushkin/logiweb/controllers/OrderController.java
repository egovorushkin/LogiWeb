package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.dto.OrderDto;
import com.egovorushkin.logiweb.dto.TruckDto;
import com.egovorushkin.logiweb.entities.enums.CargoStatus;
import com.egovorushkin.logiweb.entities.enums.OrderStatus;
import com.egovorushkin.logiweb.services.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/orders")
public class OrderController {

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

    @GetMapping("/list")
    public String showAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "manager/order/list";
    }

    @GetMapping("/{id}")
    public String showOrder(@PathVariable("id") long id, Model model) {
        model.addAttribute("order", orderService.getOrderById(id));
        return "manager/order/show";
    }

    @GetMapping("/create")
    public String showCreateOrderForm(Model model) {
        model.addAttribute("order", new OrderDto());
        model.addAttribute("statuses", OrderStatus.values());
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("cargoes", cargoService.getAllCargoes());
        model.addAttribute("trucks", truckService.getAllTrucks());
        return "manager/order/create";
    }

    @PostMapping("/save")
    public String createOrder(@ModelAttribute("order") @Valid OrderDto orderDto,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("cities", cityService.getAllCities());
            model.addAttribute("cargoes", cargoService.getAllCargoes());
            model.addAttribute("trucks", truckService.getAllTrucks());
            model.addAttribute("statuses", OrderStatus.values());
            return "manager/order/create";
        }
        orderService.createOrder(orderDto);
        return "redirect:/orders/list";
    }

    @GetMapping("/edit")
    public String showEditOrderFormForAdmin(@RequestParam("orderId") long id,
                                            Model model) {
        model.addAttribute("order", orderService.getOrderById(id));
        model.addAttribute("statuses", OrderStatus.values());
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("cargoes", cargoService.getAllCargoes());
        model.addAttribute("trucks", truckService.getAllTrucks());
        model.addAttribute("availableTrucks",
                orderService.findAvailableTrucks(orderService.getOrderById(id)));
        model.addAttribute("availableDrivers",
                orderService.findAvailableDriversForOrder(orderService.getOrderById(id)));

        return "manager/order/edit";
    }

    @GetMapping("/edit-user-order")
    public String showEditOrderFormForUser(@RequestParam("orderId") long id,
                                           Model model) {
        model.addAttribute("userOrder", orderService.getOrderById(id));
        model.addAttribute("orderStatuses", OrderStatus.values());
        model.addAttribute("cargoStatuses", CargoStatus.values());
        return "user/edit-order";
    }

    @PostMapping("/update")
    public String updateOrder(@ModelAttribute("order") @Valid OrderDto orderDto,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "manager/order/edit";
        }
        orderService.updateOrder(orderDto);
        return "redirect:/orders/list";
    }

    @GetMapping("/delete")
    public String deleteOrder(@RequestParam("orderId") long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders/list";
    }

    @GetMapping("/bind-truck")
    public String bindTruckForOrder(@RequestParam("truckId") long truckId,
                                    @RequestParam("orderId") long orderId,
                                    RedirectAttributes redirectAttributes) {
        TruckDto truck = truckService.getTruckById(truckId);
        OrderDto order = orderService.getOrderById(orderId);

        truck.setBusy(true);

        order.setTruck(truck);
        orderService.updateOrder(order);

        redirectAttributes.addAttribute("orderId", orderId);
        return "redirect:{orderId}";
    }

    @GetMapping("/unbind-truck")
    public String unbindTruckForOrder(@RequestParam("truckId") long truckId,
                                      @RequestParam("orderId") long orderId,
                                      RedirectAttributes redirectAttributes) {
        OrderDto order = orderService.getOrderById(orderId);
        if (order.getTruck() != null) {
            order.setTruck(null);
            orderService.updateOrder(order);
        }

        redirectAttributes.addAttribute("orderId", orderId);
        return "redirect:{orderId}";
    }

}
