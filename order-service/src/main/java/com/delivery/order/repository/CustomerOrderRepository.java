package com.delivery.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delivery.order.domain.CustomerOrder;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
}
