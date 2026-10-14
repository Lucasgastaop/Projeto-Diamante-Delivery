package com.delivery.review.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "review_summaries")
public class ReviewSummary {

    @Id
    private Long dishId;

    private String dishName;

    private long ratingSum;

    private long count;

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public long getRatingSum() {
        return ratingSum;
    }

    public void setRatingSum(long ratingSum) {
        this.ratingSum = ratingSum;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
