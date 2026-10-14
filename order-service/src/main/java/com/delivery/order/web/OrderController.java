package com.delivery.order.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.order.domain.CustomerOrder;
import com.delivery.order.error.NotFoundException;
import com.delivery.order.repository.CustomerOrderRepository;
import com.delivery.order.service.OrderPlacementService;

@RestController
public class OrderController {

    private final OrderPlacementService orderPlacementService;
    private final CustomerOrderRepository customerOrderRepository;

    public OrderController(
            OrderPlacementService orderPlacementService,
            CustomerOrderRepository customerOrderRepository) {
        this.orderPlacementService = orderPlacementService;
        this.customerOrderRepository = customerOrderRepository;
    }

    @PostMapping("/orders")
    public ResponseEntity<CustomerOrder> create(@RequestBody OrderRequest request) {
        CustomerOrder order = orderPlacementService.place(request.dishId(), request.quantity());
        return ResponseEntity.status(201).body(order);
    }

    @GetMapping("/orders/{id}")
    public CustomerOrder get(@PathVariable Long id) {
        return customerOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));
    }
}
