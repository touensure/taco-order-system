package com.order.manager.web;

import com.order.manager.config.OrderProps;
import com.order.manager.config.security.User;
import com.order.manager.config.security.UserRepository;
import com.order.manager.model.Order;
import com.order.manager.repository.OrderRepository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Slf4j
@Data
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
@ConfigurationProperties(prefix = "taco.orders")
public class OrderController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProps orderProps;

    @GetMapping("/current")
    public String orderForm(Model model,
          @AuthenticationPrincipal User user,
          @ModelAttribute Order order){

        if (order.getDeliveryName() == null) {
            order.setDeliveryName(user.getFullName());
        }
        if (order.getDeliveryStreet() == null) {
            order.setDeliveryStreet(user.getStreet());
        }
        if (order.getDeliveryCity() == null) {
            order.setDeliveryCity(user.getCity());
        }
        if (order.getDeliveryState() == null) {
            order.setDeliveryState(user.getState());
        }
        if (order.getDeliveryZip() == null) {
            order.setDeliveryZip(user.getZip());
        }

        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order,
                               Errors bindingErrors,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user){
        if(bindingErrors.hasErrors()){
            return "orderForm";
        }

        order.setUser(user);
        user.addOrder(order);

        userRepository.save(user);
        sessionStatus.setComplete();
        log.info("Order: " + order.getId() + " submitted");

        return "redirect:/";
    }

    @GetMapping
    public String ordersForUser(@AuthenticationPrincipal User user,
                                Model model) {

        Pageable pageable = PageRequest.of(0, orderProps.getPageSize());
        model.addAttribute("orders",
              orderRepository.findByUserOrderByPlacedAtDesc(user, pageable));

        return "orderList";
    }
}
