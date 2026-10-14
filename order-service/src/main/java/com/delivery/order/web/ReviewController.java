package com.delivery.order.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.order.service.ReviewPublisher;

@RestController
public class ReviewController {

    private final ReviewPublisher reviewPublisher;

    public ReviewController(ReviewPublisher reviewPublisher) {
        this.reviewPublisher = reviewPublisher;
    }

    @PostMapping("/reviews")
    public ResponseEntity<Void> create(@RequestBody ReviewRequest request) {
        reviewPublisher.publish(request.dishId(), request.rating(), request.comment());
        return ResponseEntity.accepted().build();
    }
}
