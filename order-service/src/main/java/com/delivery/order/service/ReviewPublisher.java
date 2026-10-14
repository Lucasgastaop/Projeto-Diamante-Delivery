package com.delivery.order.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.delivery.messaging.ReviewMessage;
import com.delivery.order.config.RabbitConfig;
import com.delivery.order.domain.Dish;
import com.delivery.order.error.BadRequestException;
import com.delivery.order.error.NotFoundException;
import com.delivery.order.repository.DishRepository;

@Service
public class ReviewPublisher {

    private final DishRepository dishRepository;
    private final RabbitTemplate rabbitTemplate;

    public ReviewPublisher(DishRepository dishRepository, RabbitTemplate rabbitTemplate) {
        this.dishRepository = dishRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(Long dishId, Integer rating, String comment) {
        if (dishId == null) {
            throw new BadRequestException("Dish id is required");
        }
        if (rating == null || rating < 1 || rating > 5) {
            throw new BadRequestException("Rating must be between 1 and 5");
        }
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new NotFoundException("Dish not found"));
        String safeComment = comment == null ? "" : comment;
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.ROUTING_KEY,
                new ReviewMessage(dish.getId(), dish.getName(), rating, safeComment));
    }
}
