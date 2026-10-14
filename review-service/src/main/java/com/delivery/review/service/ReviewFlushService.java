package com.delivery.review.service;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.delivery.review.service.ReviewBuffer.PendingReview;

@Service
public class ReviewFlushService {

    private final ReviewBuffer reviewBuffer;
    private final ReviewSummaryWriter reviewSummaryWriter;

    public ReviewFlushService(ReviewBuffer reviewBuffer, ReviewSummaryWriter reviewSummaryWriter) {
        this.reviewBuffer = reviewBuffer;
        this.reviewSummaryWriter = reviewSummaryWriter;
    }

    @Scheduled(fixedRate = 5000)
    public void scheduledFlush() {
        List<PendingReview> pendingReviews = reviewBuffer.drain();
        if (!pendingReviews.isEmpty()) {
            reviewSummaryWriter.persist(pendingReviews);
        }
    }
}
