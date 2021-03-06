package com.egovorushkin.logiweb.controllers;

import com.egovorushkin.logiweb.dto.OrderDto;
import com.egovorushkin.logiweb.dto.TruckDto;
import com.egovorushkin.logiweb.entities.enums.CargoStatus;
import com.egovorushkin.logiweb.entities.enums.OrderStatus;
import com.egovorushkin.logiweb.services.api.*;
import com.google.maps.errors.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final CityService cityService;
    private final CargoService cargoService;
    private final TruckService truckService;
    private final MapService mapService;

    private static final String ORDER = "order";
    private static final String STATUSES = "statuses";
    private static final String CITIES = "cities";
    private static final String CARGOES = "cargoes";
    private static final String TRUCKS = "trucks";
    private static final String REDIRECT_ORDERS_LIST = "redirect:/orders/list";

    @Autowired
    public OrderController(OrderService orderService,
                           CityService cityService,
                           CargoService cargoService,
                           TruckService truckService, MapService mapService) {
        this.orderService = orderService;
        this.cityService = cityService;
        this.cargoService = cargoService;
        this.truckService = truckService;
        this.mapService = mapService;
    }

    @GetMapping("/list/{pageId}")
    public String showOrdersByPage(@PathVariable("pageId") int pageId, Model model) {

        int recordsByPage = 6;
        int totalPages =
                (int) (orderService.totalCount() + recordsByPage - 1) / recordsByPage;

        if (pageId != 1) {
            pageId = (pageId - 1) * recordsByPage + 1;
        }

        model.addAttribute("orders",
                orderService.listAllByPage(pageId, recordsByPage));
        model.addAttribute("totalPages", totalPages);
        return "manager/order/list";
    }

    @GetMapping("/{id}")
    public String showOrder(@PathVariable("id") Long id, Model model) {
        OrderDto orderDto = orderService.getOrderById(id);
        TruckDto truckDto = orderDto.getTruck();
        model.addAttribute(ORDER, orderDto);
        if (truckDto != null) {
            model.addAttribute("currentDrivers", truckDto.getDrivers());
        }
        return "manager/order/show";
    }

    @GetMapping("/create")
    public String showCreateOrderForm(Model model) {
        model.addAttribute(ORDER, new OrderDto());
        model.addAttribute(CARGOES, cargoService.findAvailableCargoes());
        return "manager/order/create";
    }

    @PostMapping("/save")
    public String createOrder(@ModelAttribute("order") @Valid OrderDto orderDto,
                              BindingResult bindingResult, Model model)
            throws InterruptedException, ApiException, IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute(CARGOES, cargoService.findAvailableCargoes());
            return "manager/order/create";
        }

        orderDto.setDistance(mapService.computeDistance(orderDto.getFromCity(),
                orderDto.getToCity()));
        orderDto.setDuration(mapService.computeDuration(orderDto.getFromCity(),
                orderDto.getToCity()));

        orderService.createOrder(orderDto);

        return REDIRECT_ORDERS_LIST;
    }

    @GetMapping("/edit")
    public String showEditOrderFormForAdmin(@RequestParam("orderId") Long id,
                                            Model model) {
        model.addAttribute(ORDER, orderService.getOrderById(id));
        model.addAttribute(STATUSES, OrderStatus.values());
        model.addAttribute(CITIES, cityService.getAllCities());
        model.addAttribute(CARGOES, cargoService.findAvailableCargoes());
        model.addAttribute(TRUCKS, truckService.getAllTrucks());
        model.addAttribute("availableTrucks",
                orderService.findAvailableTrucks(orderService.getOrderById(id)));

        return "manager/order/edit";
    }

    @GetMapping("/edit-user-order")
    public String showEditOrderFormForUser(@RequestParam("orderId") Long id,
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
        return REDIRECT_ORDERS_LIST;
    }

    @GetMapping("/delete")
    public String deleteOrder(@RequestParam("orderId") Long id) {
        orderService.deleteOrder(id);
        return REDIRECT_ORDERS_LIST;
    }

    @GetMapping("/bind-truck")
    public String bindTruckForOrder(@RequestParam("truckId") Long truckId,
                                    @RequestParam("orderId") Long orderId,
                                    RedirectAttributes redirectAttributes) {
        TruckDto truck = truckService.getTruckById(truckId);
        OrderDto order = orderService.getOrderById(orderId);

        truck.setBusy(true);
        truckService.updateTruck(truck);

        order.setTruck(truck);
        orderService.updateOrder(order);

        redirectAttributes.addAttribute("orderId", orderId);
        return "redirect:{orderId}";
    }

    @GetMapping("/unbind-truck")
    public String unbindTruckForOrder(@RequestParam("orderId") Long orderId,
                                      RedirectAttributes redirectAttributes) {
        OrderDto order = orderService.getOrderById(orderId);
        if (order.getTruck() != null) {

            order.getTruck().setBusy(false);
            truckService.updateTruck(order.getTruck());

            order.setTruck(null);
            orderService.updateOrder(order);

        }

        redirectAttributes.addAttribute("orderId", orderId);
        return "redirect:{orderId}";
    }

}
