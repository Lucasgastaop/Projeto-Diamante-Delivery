package com.delivery.review.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.delivery.messaging.ReviewMessage;
import com.delivery.review.config.RabbitConfig;
import com.delivery.review.service.ReviewBuffer;

@Component
public class ReviewListener {

    private final ReviewBuffer reviewBuffer;

    public ReviewListener(ReviewBuffer reviewBuffer) {
        this.reviewBuffer = reviewBuffer;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void onReview(ReviewMessage message) {
        reviewBuffer.add(message);
    }
}
