package com.delivery.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delivery.review.domain.ReviewSummary;

public interface ReviewSummaryRepository extends JpaRepository<ReviewSummary, Long> {
}
