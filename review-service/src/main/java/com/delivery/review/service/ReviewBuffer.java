package com.delivery.review.service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.delivery.messaging.ReviewMessage;

@Component
public class ReviewBuffer {

    private final ConcurrentHashMap<Long, ReviewAccumulator> totals = new ConcurrentHashMap<>();

    public void add(ReviewMessage message) {
        totals.compute(message.dishId(), (id, current) -> {
            ReviewAccumulator accumulator = current == null
                    ? new ReviewAccumulator(message.dishName())
                    : current;
            accumulator.add(message.rating(), message.dishName());
            return accumulator;
        });
    }

    public List<PendingReview> drain() {
        List<Long> ids = List.copyOf(totals.keySet());
        return ids.stream()
                .map(id -> {
                    ReviewAccumulator removed = totals.remove(id);
                    if (removed == null) {
                        return null;
                    }
                    return new PendingReview(id, removed.getDishName(), removed.getRatingSum(), removed.getCount());
                })
                .filter(item -> item != null)
                .toList();
    }

    public record PendingReview(Long dishId, String dishName, long ratingSum, long count) {
    }
}
