package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.entities.Order;
import com.egovorushkin.logiweb.entities.status.OrderStatus;
import com.egovorushkin.logiweb.services.api.OrderService;
import com.egovorushkin.logiweb.services.api.TruckService;
import com.egovorushkin.logiweb.services.api.WaypointListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    private final WaypointListService waypointListService;

    private final TruckService truckService;

    @Autowired
    public OrderController(OrderService orderService, WaypointListService waypointListService, TruckService truckService) {
        this.orderService = orderService;
        this.waypointListService = waypointListService;
        this.truckService = truckService;
    }

    @GetMapping(value = "/list")
    public String showAllOrders(Model model) {
        List<Order> orders = orderService.listAll();
        model.addAttribute("orders", orders);
        return "order/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("order", orderService.showOrder(id));
        model.addAttribute("waypointLists", waypointListService.listAll());
        model.addAttribute("trucks", truckService.listAll());
        return "order/show";
    }

    @GetMapping(value = "/create")
    public String createOrderForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("waypointLists", waypointListService.listAll());
        model.addAttribute("trucks", truckService.listAll());
        return "/order/create";
    }

    @PostMapping(value = "/save")
    public String saveOrder(@ModelAttribute("order") @Valid Order order, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("waypointLists", waypointListService.listAll());
            model.addAttribute("trucks", truckService.listAll());
            return "order/create";
        }
        orderService.saveOrder(order);
        return "redirect:/orders/list";
    }

    @GetMapping(value = "/edit")
    public String editOrderForm(@RequestParam("orderId") int id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        model.addAttribute("waypointLists", waypointListService.listAll());
        model.addAttribute("trucks", truckService.listAll());
        model.addAttribute("statuses", OrderStatus.values());
        return "order/edit";
    }

    @PostMapping("/update")
    public String updateOrder(@ModelAttribute("order") @Valid Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "order/edit";
        }
        orderService.update(order);
        return "redirect:/orders/list";
    }

    @GetMapping(value = "/delete")
    public String deleteOrder(@RequestParam("orderId") int id) {
        orderService.delete(id);
        return "redirect:/orders/list";
    }
}
