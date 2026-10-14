package com.delivery.order.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.delivery.order.domain.Dish;
import com.delivery.order.repository.DishRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadDishes(DishRepository dishRepository) {
        return args -> {
            if (dishRepository.count() > 0) {
                return;
            }
            dishRepository.save(dish(
                    "House Burger",
                    "Brioche bun, beef and cheese",
                    "39.90",
                    10));
            dishRepository.save(dish(
                    "Garden Salad",
                    "Vegetarian salad with greens and tomato",
                    "28.00",
                    30));
            dishRepository.save(dish(
                    "Veggie Wrap",
                    "Vegetarian wrap with grilled vegetables",
                    "32.00",
                    25));
            dishRepository.save(dish(
                    "Margherita Pizza",
                    "Tomato, mozzarella and basil",
                    "44.90",
                    20));
            dishRepository.save(dish(
                    "Grilled Salmon",
                    "Salmon fillet with herbs",
                    "59.90",
                    12));
            dishRepository.save(dish(
                    "Chocolate Brownie",
                    "Dark chocolate brownie",
                    "16.00",
                    40));
        };
    }

    private Dish dish(String name, String description, String price, int stock) {
        Dish dish = new Dish();
        dish.setName(name);
        dish.setDescription(description);
        dish.setPrice(new BigDecimal(price));
        dish.setStock(stock);
        return dish;
    }
}
