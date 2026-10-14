package com.delivery.review.web;

import java.util.Comparator;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.review.domain.ReviewSummary;
import com.delivery.review.repository.ReviewSummaryRepository;

@RestController
public class RankingController {

    private final ReviewSummaryRepository reviewSummaryRepository;

    public RankingController(ReviewSummaryRepository reviewSummaryRepository) {
        this.reviewSummaryRepository = reviewSummaryRepository;
    }

    @GetMapping("/reviews/ranking")
    public List<RankingItem> ranking() {
        return reviewSummaryRepository.findAll().stream()
                .filter(summary -> summary.getCount() > 0)
                .map(this::toItem)
                .sorted(Comparator.comparingDouble(RankingItem::average).reversed())
                .toList();
    }

    private RankingItem toItem(ReviewSummary summary) {
        double average = Math.round(summary.getRatingSum() * 100.0 / summary.getCount()) / 100.0;
        return new RankingItem(summary.getDishId(), summary.getDishName(), average, summary.getCount());
    }
}
