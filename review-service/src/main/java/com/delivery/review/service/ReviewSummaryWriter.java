package com.delivery.review.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.delivery.review.domain.ReviewSummary;
import com.delivery.review.repository.ReviewSummaryRepository;
import com.delivery.review.service.ReviewBuffer.PendingReview;

@Service
public class ReviewSummaryWriter {

    private final ReviewSummaryRepository reviewSummaryRepository;

    public ReviewSummaryWriter(ReviewSummaryRepository reviewSummaryRepository) {
        this.reviewSummaryRepository = reviewSummaryRepository;
    }

    @Transactional
    public void persist(List<PendingReview> pendingReviews) {
        for (PendingReview pending : pendingReviews) {
            ReviewSummary summary = reviewSummaryRepository.findById(pending.dishId()).orElseGet(() -> {
                ReviewSummary created = new ReviewSummary();
                created.setDishId(pending.dishId());
                created.setRatingSum(0);
                created.setCount(0);
                return created;
            });
            summary.setDishName(pending.dishName());
            summary.setRatingSum(summary.getRatingSum() + pending.ratingSum());
            summary.setCount(summary.getCount() + pending.count());
            reviewSummaryRepository.save(summary);
        }
    }
}
