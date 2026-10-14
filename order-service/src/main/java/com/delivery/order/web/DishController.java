package com.delivery.order.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.order.domain.Dish;
import com.delivery.order.error.NotFoundException;
import com.delivery.order.repository.DishRepository;

@RestController
public class DishController {

    private final DishRepository dishRepository;

    public DishController(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @GetMapping("/dishes")
    public List<Dish> list() {
        return dishRepository.findAll();
    }

    @GetMapping("/dishes/{id}")
    public Dish get(@PathVariable Long id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dish not found"));
    }
}
