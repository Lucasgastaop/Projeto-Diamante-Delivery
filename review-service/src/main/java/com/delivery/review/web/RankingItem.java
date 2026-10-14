package com.delivery.review.web;

public record RankingItem(Long dishId, String dishName, double average, long count) {
}
