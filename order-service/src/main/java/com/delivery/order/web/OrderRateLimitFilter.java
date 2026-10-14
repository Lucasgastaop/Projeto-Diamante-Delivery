package com.delivery.order.web;

import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class OrderRateLimitFilter extends OncePerRequestFilter {

    private final OrderRateLimiter orderRateLimiter;

    public OrderRateLimitFilter(OrderRateLimiter orderRateLimiter) {
        this.orderRateLimiter = orderRateLimiter;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        if ("POST".equalsIgnoreCase(request.getMethod()) && "/orders".equals(request.getRequestURI())) {
            if (!orderRateLimiter.tryAcquire()) {
                response.setStatus(429);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Too many requests\"}");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
