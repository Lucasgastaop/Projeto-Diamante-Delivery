package com.delivery.review.service;

class ReviewAccumulator {

    private String dishName;
    private long ratingSum;
    private long count;

    ReviewAccumulator(String dishName) {
        this.dishName = dishName;
    }

    void add(int rating, String dishName) {
        this.ratingSum += rating;
        this.count++;
        if (dishName != null && !dishName.isBlank()) {
            this.dishName = dishName;
        }
    }

    String getDishName() {
        return dishName;
    }

    long getRatingSum() {
        return ratingSum;
    }

    long getCount() {
        return count;
    }
}
