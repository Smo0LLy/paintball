package com.paintball.project.controller;

import com.paintball.project.model.Order;
import com.paintball.project.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DataSource dataSource;

    @GetMapping("/order")
    public String orderForm(Model model) {
        model.addAttribute("order", new Order());
        return "order";
    }

    @PostMapping("/order")
    public String orderSubmit(@ModelAttribute Order order, Model model) {
        orderRepository.save(order);
        model.addAttribute("order", order);
        return "order-result";
    }

    @GetMapping("/orders")
    public String getAllOrders(Model model) {
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "orders";
    }

    @PostMapping("/order/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
        return "redirect:/orders";
    }

    @GetMapping("/test-db")
    public String testDatabaseConnection() {
        try (Connection connection = dataSource.getConnection()) {
            return "Database connection successful!";
        } catch (SQLException e) {
            return "Database connection failed: " + e.getMessage();
        }
    }
}