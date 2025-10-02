package com.bookstore.service;

import com.bookstore.model.Order;
import com.bookstore.repository.OrderRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService() {
        this.orderRepository = new OrderRepository();
    }

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order saveOrder(Order order) {
        validateOrder(order);
        calculateTotalSum(order);
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.delete(id);
    }

    private void validateOrder(Order order) {
        if (order.getBook() == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (order.getCustomer() == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (order.getSale() == null) {
            throw new IllegalArgumentException("Salesperson cannot be null");
        }
        if (order.getQuantity() == null || order.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (order.getDateInput() == null) {
            throw new IllegalArgumentException("Order date cannot be null");
        }
    }

    private void calculateTotalSum(Order order) {
        if (order.getBook() != null && order.getQuantity() != null) {
            BigDecimal price = order.getBook().getPrice();
            BigDecimal quantity = new BigDecimal(order.getQuantity());
            order.setTotalSum(price.multiply(quantity));
        }
    }
} 