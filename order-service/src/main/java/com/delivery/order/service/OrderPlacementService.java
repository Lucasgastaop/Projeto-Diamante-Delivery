package com.delivery.order.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.delivery.order.client.PaymentClient;
import com.delivery.order.domain.CustomerOrder;
import com.delivery.order.domain.Dish;
import com.delivery.order.domain.OrderStatus;
import com.delivery.order.error.BadRequestException;
import com.delivery.order.error.ConflictException;
import com.delivery.order.error.NotFoundException;
import com.delivery.order.repository.CustomerOrderRepository;
import com.delivery.order.repository.DishRepository;

@Service
public class OrderPlacementService {

    private final DishRepository dishRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private final PaymentClient paymentClient;

    public OrderPlacementService(
            DishRepository dishRepository,
            CustomerOrderRepository customerOrderRepository,
            PaymentClient paymentClient) {
        this.dishRepository = dishRepository;
        this.customerOrderRepository = customerOrderRepository;
        this.paymentClient = paymentClient;
    }

    @Transactional
    public CustomerOrder place(Long dishId, Integer quantity) {
        if (dishId == null) {
            throw new BadRequestException("Dish id is required");
        }
        if (quantity == null || quantity < 1) {
            throw new BadRequestException("Quantity must be at least 1");
        }

        Dish dish = dishRepository.findByIdForUpdate(dishId)
                .orElseThrow(() -> new NotFoundException("Dish not found"));
        if (dish.getStock() < quantity) {
            throw new ConflictException("Dish out of stock");
        }

        dish.setStock(dish.getStock() - quantity);
        BigDecimal totalPrice = dish.getPrice()
                .multiply(BigDecimal.valueOf(quantity))
                .setScale(2, RoundingMode.HALF_UP);

        paymentClient.charge(totalPrice);

        CustomerOrder order = new CustomerOrder();
        order.setDishId(dish.getId());
        order.setQuantity(quantity);
        order.setTotalPrice(totalPrice);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return customerOrderRepository.save(order);
    }
}
