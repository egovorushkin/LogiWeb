package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.entities.Order;
import com.egovorushkin.logiweb.entities.status.OrderStatus;
import com.egovorushkin.logiweb.services.api.CityService;
import com.egovorushkin.logiweb.services.api.OrderService;
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

    private OrderService orderService;

    private CityService cityService;

    @Autowired
    public OrderController(OrderService orderService, CityService cityService) {

        this.orderService = orderService;
        this.cityService = cityService;
    }

    @GetMapping(value = "/list")
    public String showAllOrders(Model model) {
        List<Order> orders = orderService.listAll();
        model.addAttribute("orders", orders);
        return "order/list";
    }

//    @GetMapping("/{id}")
//    public String show(@PathVariable("id") int id, Model model) {
//        model.addAttribute("order", orderService.showOrder(id));
//        return "order/show";
//    }

    @GetMapping(value = "/create")
    public String createOrderForm(Model model) {
        model.addAttribute("order", new Order());
        return "/order/create";
    }

    @PostMapping(value = "/save")
    public String saveOrder(@ModelAttribute("order") @Valid Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "order/create";
        }
        orderService.saveOrder(order);
        return "redirect:/orders/list";
    }

    @GetMapping(value = "/edit")
    public String editOrderForm(@RequestParam("orderId") int id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        model.addAttribute("cities", cityService.listAll());
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
