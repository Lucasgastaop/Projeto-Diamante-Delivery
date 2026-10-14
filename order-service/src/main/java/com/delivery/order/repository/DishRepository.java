package com.delivery.order.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import com.delivery.order.domain.Dish;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;

public interface DishRepository extends JpaRepository<Dish, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(@QueryHint(name = "jakarta.persistence.lock.timeout", value = "300000"))
    @Query("select d from Dish d where d.id = :id")
    Optional<Dish> findByIdForUpdate(@Param("id") Long id);
}
