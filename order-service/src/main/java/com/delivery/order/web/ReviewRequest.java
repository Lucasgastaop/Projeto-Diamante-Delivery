package com.delivery.order.web;

public record ReviewRequest(Long dishId, Integer rating, String comment) {
}
